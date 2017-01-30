package server.repository;

import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.localization.Locality;
import server.model.localization.Province;
import server.model.localization.Street;
import server.model.message.FirstMessageWithNotification;
import server.model.message.SecondMessageWithNotification;
import server.model.notification.Notification;
import server.repository.confirmation.ThreadConfirmationFromAnotherSystem;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;

/**
 * Class NotificationRepository for CRUD operation on Notification Entity (singleton)
 */
public class NotificationRepository extends Repository {

    private NotificationRepository() {
    }

    private static NotificationRepository instance = null;

    public static NotificationRepository getInstance() {
        if (instance == null) {
            instance = new NotificationRepository();
        }
        return instance;
    }


    public Notification findById(Long id) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
        Root<Notification> notification = criteriaQuery.from(Notification.class);
        criteriaQuery.select(notification).where(notification.get("id").in(id));
        Notification singleResult= entityManager.createQuery(criteriaQuery).getSingleResult();
        return  singleResult;
    }

    public List<Notification> findNotificationForDistributor(Employee employee) {
        EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
        Employee employeeInDatabase = employeeRepository.findEmployeeByFirstAndLastNameAndMail(employee);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
        Root<Notification> notificationInDatabase = criteriaQuery.from(Notification.class);
        criteriaQuery.select(notificationInDatabase).where(notificationInDatabase.get("employee").in(employeeInDatabase));
        List<Notification> notificationListInDatabase;
        try {
            List<Notification> resultList = entityManager.createQuery(criteriaQuery).getResultList();

            notificationListInDatabase = resultList;
        }
        catch (NoResultException e){
            notificationListInDatabase = null;
        }
        entityManager.clear();
        return  notificationListInDatabase;
    }

    public Map<String,List<Notification>> findAllNotification(Employee employee) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
        Root<Notification> notificationInDatabase = criteriaQuery.from(Notification.class);
        criteriaQuery.select(notificationInDatabase);
        List<Notification> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.clear();
        Map<String, List<Notification>> mapsForClient = new HashMap<>();
        mapsForClient.put("forUser", findNotificationForDistributor(employee));
        mapsForClient.put("forAll", resultList);

        return mapsForClient;

    }

    public Long saveFirstNotification(FirstMessageWithNotification messageWithNotification) {
        EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
        Employee employeeInDatabase = employeeRepository.findEmployeeByFirstAndLastNameAndMail(messageWithNotification.getEmployee());
        LocalizationRepository localizationRepository = LocalizationRepository.getInstance();
        Locality localityInDatabase = localizationRepository.findLocality(messageWithNotification.getLocality());
        Street streetInDatabase = localizationRepository.findStreet(messageWithNotification.getStreetName(),messageWithNotification.getStreetNumber());
        Province provinceInDatabase = localizationRepository.findProvince(messageWithNotification.getProvince());

        InstitutionRepository institutionRepository = InstitutionRepository.getInstance();
        List<String> institutions = messageWithNotification.getInstitutionNotification();

        Notification notification = new Notification();

        for (String institutionName:institutions) {
            Institution institution = institutionRepository.findInstitutionByName(institutionName);
            notification.getInstitutions().add(institution);
        }
        notification.setStatus(1);
        notification.setCallerFirstName(messageWithNotification.getCallerFirstNameTextField());
        notification.setCallerLastName(messageWithNotification.getCallerLastNameTextField());
        notification.setCallerPhoneNumber(messageWithNotification.getCallerNumber());
        notification.setEmployee(employeeInDatabase);
        notification.setStreet(streetInDatabase);
        notification.setLocality(localityInDatabase);
        notification.setProvince(provinceInDatabase);

        entityManager.getTransaction().begin();
        Notification notificationInBase = entityManager.merge(notification);
        entityManager.getTransaction().commit();
        entityManager.clear();

        for (String institutionName:institutions) {
            institutionRepository.updateNotificationInstitution(institutionName, notificationInBase);
        }
        return notificationInBase.getId();
    }

    public void updateNotificationForEmployee(Employee employee) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
        Root<Notification> notificationInDatabase = criteriaQuery.from(Notification.class);

        criteriaQuery.select(notificationInDatabase)
                .where(notificationInDatabase.get("employee").in(employee));
        List<Notification> resultList;
        try {
            entityManager.getTransaction().begin();
            resultList = entityManager.createQuery(criteriaQuery).getResultList();
            entityManager.getTransaction().commit();
            entityManager.clear();
        } catch (NoResultException e) {
            resultList = null;
            entityManager.getTransaction().commit();
            entityManager.clear();
        }

        CriteriaUpdate<Notification> criteriaUpdate = null;
        for (Notification notification : resultList) {
            criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Notification.class);
            Root<Notification> notificationInBase = criteriaUpdate.from(Notification.class);
            criteriaUpdate.set("employee", null);
            criteriaUpdate.where(notificationInBase.get("id").in(notification.getId()));
            entityManager.getTransaction().begin();
            entityManager.createQuery(criteriaUpdate).executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.clear();
        }
    }

    public void updateNotificationForInstitutions(Institution institution) {

        Query nativeQuery = entityManager.createNativeQuery("SELECT COUNT(*) FROM NOTIFICATION");
        BigDecimal numberOfNotifications = (BigDecimal) nativeQuery.getSingleResult();

        for(long i=1;i<=numberOfNotifications.intValue();i++){
            Notification notification = entityManager.find(Notification.class, i);
            for (Institution institutionToEdit: notification.getInstitutions()) {
                if(institutionToEdit.getId() == institution.getId()){
                    institutionToEdit = null;
                }
            }
            entityManager.getTransaction().begin();
            entityManager.merge(notification);
            entityManager.getTransaction().commit();
        }
    }



    public Boolean saveSecondNotification(SecondMessageWithNotification secondMessageWithNotification) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Notification> criteriaNotificationUpdate = criteriaBuilder.createCriteriaUpdate(Notification.class);
        Root<Notification> notificationRoot = criteriaNotificationUpdate.from(Notification.class);
        if(secondMessageWithNotification.getNotations()!=null)
             criteriaNotificationUpdate.set("notation",secondMessageWithNotification.getNotations());
        criteriaNotificationUpdate.set("numberOfVictims",secondMessageWithNotification.getNumberOfVictims());
        criteriaNotificationUpdate.set("accidentType",secondMessageWithNotification.getAccidentType());
        criteriaNotificationUpdate.set("reportBoat",secondMessageWithNotification.getReportBoat());
        criteriaNotificationUpdate.set("reportHelicopter",secondMessageWithNotification.getReportHelicopter());
        criteriaNotificationUpdate.where(notificationRoot.get("id").in(secondMessageWithNotification.getIdFirstMessage()));

        entityManager.getTransaction().begin();
        entityManager.createQuery(criteriaNotificationUpdate).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();

        Thread confirmationThread = new ThreadConfirmationFromAnotherSystem(secondMessageWithNotification);
        confirmationThread.start();
        return true;

    }

    public void changeNotificationStatus(SecondMessageWithNotification secondMessageWithNotification){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Notification> criteriaNotificationUpdate = criteriaBuilder.createCriteriaUpdate(Notification.class);
        Root<Notification> notificationRoot = criteriaNotificationUpdate.from(Notification.class);
        if(secondMessageWithNotification.getIdFirstMessage() == 5)
            criteriaNotificationUpdate.set("status",3L);
        else
            criteriaNotificationUpdate.set("status",2L);
        criteriaNotificationUpdate.where(notificationRoot.get("id").in(secondMessageWithNotification.getIdFirstMessage()));

        entityManager.getTransaction().begin();
        entityManager.createQuery(criteriaNotificationUpdate).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }
}
