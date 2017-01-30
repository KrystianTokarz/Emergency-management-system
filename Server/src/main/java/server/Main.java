package server;

import server.communication.ThreadedEchoServer;
import server.dao.HibernateSession;
import server.model.employee.Employee;
import server.model.employee.EmployeeAccount;
import server.model.employee.EmployeeProfileType;
import server.model.institution.Institution;
import server.model.institution.InstitutionType;
import server.model.localization.Locality;
import server.model.localization.Province;
import server.model.localization.ProvinceType;
import server.model.localization.Street;
import server.model.notification.AccidentType;
import server.model.notification.Notification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class with mock data for database
 */
public class Main {

    public static void main(String[] args) {

        HibernateSession instance = HibernateSession.getInstance();
        EntityManager entityManager = instance.getEntityManager();

        EmployeeAccount employeeAccount = new EmployeeAccount();
        employeeAccount.setPassword("p");
        employeeAccount.setLogin("u");

        Employee employee = new Employee();
        employee.setFirstName("Krystian");
        employee.setLastName("Tokarz");
        employee.setEmail("krycha@wp.pl");
        employee.setType(EmployeeProfileType.DISTRIBUTOR);
        employee.setEmployeeAccount(employeeAccount);

        Province province  = new Province();
        province.setProvinceType(ProvinceType.SWIETOKRZYSKIE);
        Locality locality= new Locality();
        locality.setLocality("Kielce");
        Street street = new Street();
        street.setStreet("Warszawska");
        street.setSpecialNumber("22/23");
        locality.add(street);
        province.add(locality);
        Institution institution = new Institution();
        institution.setName("Wojskowy Szpital Wewnętrzny");
        institution.setAvailability(true);
        institution.setInstitutionImage(null);
        institution.setInstitutionType(InstitutionType.EMERGENCY);
        institution.setProvince(province);
        institution.setLocality(locality);
        institution.setStreet(street);

        Locality locality3= new Locality();
        locality3.setLocality("Sandomierz");
        Street street3 = new Street();
        street3.setStreet("Krakowska");
        street3.setSpecialNumber("22/23");
        locality3.add(street3);
        province.add(locality3);
        Institution institution3 = new Institution();
        institution3.setName("Szpital z SOREM");
        institution3.setAvailability(true);
        institution3.setInstitutionImage(null);
        institution3.setInstitutionType(InstitutionType.EMERGENCY);
        institution3.setProvince(province);
        institution3.setLocality(locality3);
        institution3.setStreet(street3);

        Province province2  = new Province();
        province2.setProvinceType(ProvinceType.LODZKIE);
        Locality locality2 = new Locality();
        locality2.setLocality("Lodz");
        Street street2 = new Street();
        street2.setStreet("Lodzka");
        street2.setSpecialNumber("21/22");
        locality2.add(street2);
        province2.add(locality2);
        Institution institution2 = new Institution();
        institution2.setName("Ochotnicza Straz Pozarna nr.2 ");
        institution2.setAvailability(true);
        institution2.setInstitutionImage(null);
        institution2.setInstitutionType(InstitutionType.FIRE_BRIGADE);
        institution2.setProvince(province2);
        institution2.setLocality(locality2);
        institution2.setStreet(street2);


        EmployeeAccount employeeAccount2 = new EmployeeAccount();
        employeeAccount2.setPassword("1");
        employeeAccount2.setLogin("2");

        Employee employee2 = new Employee();
        employee2.setFirstName("Jan");
        employee2.setLastName("Kowalski");
        employee2.setEmail("janek@onet.pl");
        employee2.setType(EmployeeProfileType.ADMINISTRATOR);
        employee2.setEmployeeAccount(employeeAccount2);

        Notification notification = new Notification();
        notification.setCallerLastName("Basia");
        notification.setCallerFirstName("Asia");
        notification.setCallerPhoneNumber("333111333");

        notification.getInstitutions().add(institution);
        notification.setProvince(province);
        notification.setLocality(locality);
        notification.setStreet(street);
        notification.setNumberOfVictims(0);
        notification.setAccidentType(AccidentType.POŻAR);
        notification.setEmployee(employee);
        notification.setStatus(2);


        Notification notification2 = new Notification();
        notification2.setCallerLastName("Oalek");
        notification2.setCallerFirstName("Barek");
        notification2.setCallerPhoneNumber("222111333");

        notification2.getInstitutions().add(institution2);
        notification2.setProvince(province2);
        notification2.setLocality(locality2);
        notification2.setStreet(street2);
        notification2.setNumberOfVictims(3);
        notification2.setAccidentType(AccidentType.WYBUCH);
        notification2.setEmployee(employee);
        notification2.setStatus(2);

        institution2.getNotification().add(notification2);
        institution.getNotification().add(notification);

            entityManager.getTransaction().begin();
            entityManager.persist(institution);
            entityManager.persist(institution2);
            entityManager.persist(institution3);
            entityManager.persist(employee);
            entityManager.persist(employee2);
            entityManager.persist(notification);
            entityManager.persist(notification2);
            entityManager.getTransaction().commit();
            entityManager.clear();

        ThreadedEchoServer threadedEchoServer = new ThreadedEchoServer();
        threadedEchoServer.start();
    }
}
