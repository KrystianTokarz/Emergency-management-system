package server.repository;



import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.institution.InstitutionImage;
import server.model.localization.Locality;
import server.model.localization.Province;
import server.model.localization.ProvinceType;
import server.model.localization.Street;
import server.model.notification.Notification;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class InstitutionRepository for CRUD operation on Institution Entity (singleton)
 */
public class InstitutionRepository extends Repository {


    private InstitutionRepository() {
    }

    private static InstitutionRepository instance = null;

    public static InstitutionRepository getInstance() {
        if (instance == null) {
            instance = new InstitutionRepository();
        }
        return instance;
    }

    public Institution findById(Long id) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Institution> criteriaQuery = criteriaBuilder.createQuery(Institution.class);
        Root<Institution> institution = criteriaQuery.from(Institution.class);
        criteriaQuery.select(institution).where(institution.get("id").in(id));
        Institution singleResult= entityManager.createQuery(criteriaQuery).getSingleResult();

        return  singleResult;
    }

    public List<Institution> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Institution> criteriaQuery = criteriaBuilder.createQuery(Institution.class);
        Root<Institution> institutionsInDatabase = criteriaQuery.from(Institution.class);
        criteriaQuery.select(institutionsInDatabase);
        List<Institution> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.clear();
        return  resultList;
    }

    public Institution findInstitutionByName(Institution institution){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Institution> criteriaQuery = criteriaBuilder.createQuery(Institution.class);
        Root<Institution> institutionInDatabase = criteriaQuery.from(Institution.class);

        criteriaQuery.select(institutionInDatabase)
                .where(institutionInDatabase.get("name").in(institution.getName()));
        Institution foundedInstitution;
            try {
                foundedInstitution = entityManager.createQuery(criteriaQuery).getSingleResult();
            }
            catch (NoResultException e){
                foundedInstitution = null;
            }
            entityManager.clear();
        return  foundedInstitution;
    }

    public Institution findInstitutionByName(String institutionName){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Institution> criteriaQuery = criteriaBuilder.createQuery(Institution.class);
        Root<Institution> institutionInDatabase = criteriaQuery.from(Institution.class);

        criteriaQuery.select(institutionInDatabase)
                .where(institutionInDatabase.get("name").in(institutionName));
        Institution foundedInstitution;
        try {
            foundedInstitution = entityManager.createQuery(criteriaQuery).getSingleResult();
        }
        catch (NoResultException e){
            foundedInstitution = null;
        }
        entityManager.clear();
        return  foundedInstitution;
    }

    public Locality findLocalityInBase(Institution institution){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Locality> criteriaQuery = criteriaBuilder.createQuery(Locality.class);
        Root<Locality> localityInDatabase = criteriaQuery.from(Locality.class);

        criteriaQuery.select(localityInDatabase)
                .where(localityInDatabase.get("locality").in(institution.getLocality().getLocality()));
        Locality foundedLocality = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.clear();
        return foundedLocality;
    }

    public Province findProvinceInBase(Institution institution){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Province> criteriaQuery = criteriaBuilder.createQuery(Province.class);
        Root<Province> provinceInDatabase = criteriaQuery.from(Province.class);
        int valueOfProvinceType = -1;
        if(institution.getProvince().getProvinceType() == ProvinceType.SWIETOKRZYSKIE)
            valueOfProvinceType = 0;
        else if(institution.getProvince().getProvinceType() == ProvinceType.LODZKIE)
            valueOfProvinceType = 1;
        else if(institution.getProvince().getProvinceType() == ProvinceType.MASLOVIA)
            valueOfProvinceType = 2;
        criteriaQuery.select(provinceInDatabase)
                .where(provinceInDatabase.get("provinceType").in(valueOfProvinceType));
        Province foundedProvince = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.clear();;
        return foundedProvince;
    }

    private void deleteInstitutionByName(Institution institution){
        Institution institutionByName = findInstitutionByName(institution);
        NotificationRepository notificationRepository = NotificationRepository.getInstance();

        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(institutionByName) ? institutionByName : entityManager.merge(institutionByName));
        entityManager.getTransaction().commit();
        entityManager.clear();
    }
    public void deleteEmployee(List<Institution> institutions) {
        for(Institution institution:institutions){
            deleteInstitutionByName(institution);
        }
    }

    public void saveNewInstitution(Institution institution) {
        Province provinceInBase = findProvinceInBase(institution);
        for (Locality localityInBase: provinceInBase.getLocalityList()) {
            if(localityInBase.getLocality().equals(institution.getLocality().getLocality())){
                institution.setLocality(localityInBase);
            }
        }
        institution.setProvince(provinceInBase);
        entityManager.getTransaction().begin();
        entityManager.merge(institution);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    public Street findStreetInBase(Institution institution){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Street> criteriaQuery = criteriaBuilder.createQuery(Street.class);
        Root<Street> streetInDatabase = criteriaQuery.from(Street.class);
        criteriaQuery.select(streetInDatabase)
                .where(streetInDatabase.get("street").in(institution.getStreet().getStreet()),
                        streetInDatabase.get("specialNumber").in(institution.getStreet().getSpecialNumber()));
        Street foundedStreet = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.clear();
        return foundedStreet;
    }

    public void updateStreet(Street oldStreet,Street newStreet){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Street> criteriaStreetUpdate = criteriaBuilder.createCriteriaUpdate(Street.class);
        Root<Street> localityInDatabase = criteriaStreetUpdate.from(Street.class);
        criteriaStreetUpdate.set("street",newStreet.getStreet());
        criteriaStreetUpdate.set("specialNumber",newStreet.getSpecialNumber());
        criteriaStreetUpdate.where(localityInDatabase.get("street").in(oldStreet.getStreet()),
                localityInDatabase.get("specialNumber").in(oldStreet.getSpecialNumber()) );

        entityManager.getTransaction().begin();
        entityManager.createQuery(criteriaStreetUpdate).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    public void updateNotificationInstitution(String institutionName, Notification notification){

        Institution institutionByName = findInstitutionByName(institutionName);
        institutionByName.getNotification().add(notification);

        entityManager.getTransaction().begin();
        entityManager.merge(institutionByName);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }



    public void updateInstitution(Map<String, Institution> institutionMap) {
        boolean isNewImage = false;
        Institution oldInstitution = institutionMap.get("old");
        Institution newInstitution = institutionMap.get("new");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        Institution institutionByName = findInstitutionByName(oldInstitution);

        /*UPDATE EMPLOYEE_IMAGE ENTITY*/
        CriteriaUpdate<InstitutionImage> criteriaImageUpdate = null;
        if(newInstitution.getInstitutionImage() != null) {
            InstitutionImage institutionImage = institutionByName.getInstitutionImage();
            if (institutionImage != null) {
                criteriaImageUpdate = criteriaBuilder.createCriteriaUpdate(InstitutionImage.class);
                Root<InstitutionImage> institutionImageInDatabase = criteriaImageUpdate.from(InstitutionImage.class);
                criteriaImageUpdate.set("image", newInstitution.getInstitutionImage().getImage());
                criteriaImageUpdate.where(institutionImageInDatabase.get("id").in(institutionImage.getId()));
            }else
            {
                isNewImage=true;
                entityManager.getTransaction().begin();
                entityManager.persist(newInstitution.getInstitutionImage());
                entityManager.getTransaction().commit();
                entityManager.clear();
            }
        }

        Province newProvinceInBase = findProvinceInBase(newInstitution);
        Locality newLocalityInBase = findLocalityInBase(newInstitution);
        Street oldStreetInBase = findStreetInBase(oldInstitution);
        Street newStreet = newInstitution.getStreet();
        updateStreet(oldStreetInBase,newStreet);

        CriteriaUpdate<Institution> criteriaInstitutionUpdate = criteriaBuilder.createCriteriaUpdate(Institution.class);
        Root<Institution> institutionInDatabase = criteriaInstitutionUpdate.from(Institution.class);
        criteriaInstitutionUpdate.set("institutionType",newInstitution.getInstitutionType());
        criteriaInstitutionUpdate.set("name",newInstitution.getName());
        criteriaInstitutionUpdate.set("availability",newInstitution.getAvailability());
        if(isNewImage == true)
            criteriaInstitutionUpdate.set("institutionImage",newInstitution.getInstitutionImage());
        criteriaInstitutionUpdate.set("province",newProvinceInBase);
        criteriaInstitutionUpdate.set("locality",newLocalityInBase);
        criteriaInstitutionUpdate.where(institutionInDatabase.get("name").in(oldInstitution.getName()));

        entityManager.getTransaction().begin();
        if(criteriaImageUpdate != null)
            entityManager.createQuery(criteriaImageUpdate).executeUpdate();
        entityManager.createQuery(criteriaInstitutionUpdate).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }
}
