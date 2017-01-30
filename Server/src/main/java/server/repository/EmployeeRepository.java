package server.repository;

import server.dao.HibernateSession;
import server.model.employee.Employee;
import server.model.employee.EmployeeAccount;
import server.model.employee.EmployeeImage;
import server.model.employee.EmployeeProfileType;
import server.model.notification.Notification;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;
import java.util.Map;

/**
 * Class EmployeeRepository for CRUD operation on Employee Entity (singleton)
 */
public class EmployeeRepository extends Repository {

    private EmployeeRepository() {
    }

    private static EmployeeRepository instance = null;

    public static EmployeeRepository getInstance() {
        if (instance == null) {
            instance = new EmployeeRepository();
        }
        return instance;
    }

    public Employee findById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeesInDatabase = criteriaQuery.from(Employee.class);
        criteriaQuery.select(employeesInDatabase).where(employeesInDatabase.get("id").in(id));
        Employee singleResult = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.clear();
        return  singleResult;
    }

    public List<Employee> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeesInDatabase = criteriaQuery.from(Employee.class);
        criteriaQuery.select(employeesInDatabase);
        List<Employee> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.clear();
        return  resultList;
    }

    public Employee findEmployeeByLoginAndPassword(String login, String password){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeAccount> criteriaQuery = criteriaBuilder.createQuery(EmployeeAccount.class);
        Root<EmployeeAccount> employeeInDatabase = criteriaQuery.from(EmployeeAccount.class);
        criteriaQuery.select(employeeInDatabase).where(employeeInDatabase.get("login").in(login),
                employeeInDatabase.get("password").in(password));
        Employee employee;
        try {
            EmployeeAccount singleResult = entityManager.createQuery(criteriaQuery).getSingleResult();
            Long id = singleResult.getId();
            employee = findById(id);
        }
        catch (NoResultException e){
            employee = null;
        }
        entityManager.clear();
        return  employee;
    }

    public void saveNewEmployee(Employee employee){

        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    private void deleteEmployeeByFirstAndLastNameAndMail(Employee employee){

        Employee employeeByFirstAndLastNameAndMail = findEmployeeByFirstAndLastNameAndMail(employee);
        NotificationRepository notificationRepository = NotificationRepository.getInstance();
        notificationRepository.updateNotificationForEmployee(employeeByFirstAndLastNameAndMail);
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(employeeByFirstAndLastNameAndMail) ? employeeByFirstAndLastNameAndMail : entityManager.merge(employeeByFirstAndLastNameAndMail));
        entityManager.getTransaction().commit();
        entityManager.clear();

    }

    public void deleteEmployee(List<Employee> employees) {
        for (Employee employee: employees) {
            deleteEmployeeByFirstAndLastNameAndMail(employee);
        }
    }

    public void updateEmployee(Map<String,Employee> employeeMap){

        boolean isNewImage = false;
        Employee oldEmployee = employeeMap.get("old");
        Employee newEmployee = employeeMap.get("new");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        Employee employeeByFirstAndLastNameAndMail = findEmployeeByFirstAndLastNameAndMail(oldEmployee);



        /*UPDATE EMPLOYEE_IMAGE ENTITY*/
        CriteriaUpdate<EmployeeImage> criteriaImageUpdate = null;
        if(newEmployee.getEmployeeImage() != null) {
            EmployeeImage employeeImage = employeeByFirstAndLastNameAndMail.getEmployeeImage();
            if (employeeImage != null) {
                criteriaImageUpdate = criteriaBuilder.createCriteriaUpdate(EmployeeImage.class);
                Root<EmployeeImage> employeeImageInDatabase = criteriaImageUpdate.from(EmployeeImage.class);
                criteriaImageUpdate.set("image", newEmployee.getEmployeeImage().getImage());
                criteriaImageUpdate.where(employeeImageInDatabase.get("id").in(employeeImage.getId()));
            }else
            {
                isNewImage=true;
                entityManager.getTransaction().begin();
                entityManager.persist(newEmployee.getEmployeeImage());
                entityManager.getTransaction().commit();
                entityManager.clear();
            }
        }
        /*UPDATE EMPLOYEE_ACCOUNT ENTITY*/
        EmployeeAccount employeeAccount = employeeByFirstAndLastNameAndMail.getEmployeeAccount();
        CriteriaUpdate<EmployeeAccount> criteriaAccountUpdate = criteriaBuilder.createCriteriaUpdate(EmployeeAccount.class);
        Root<EmployeeAccount> employeeAccountInDatabase = criteriaAccountUpdate.from(EmployeeAccount.class);
        criteriaAccountUpdate.set("login",newEmployee.getEmployeeAccount().getLogin());
        criteriaAccountUpdate.set("password",newEmployee.getEmployeeAccount().getPassword());
        criteriaAccountUpdate.where(employeeAccountInDatabase.get("id").in(employeeAccount.getId()));

        /*UPDATE EMPLOYEE ENTITY*/
        CriteriaUpdate<Employee> criteriaEmployeeUpdate = criteriaBuilder.createCriteriaUpdate(Employee.class);
        Root<Employee> employeeInDatabase = criteriaEmployeeUpdate.from(Employee.class);
        criteriaEmployeeUpdate.set("firstName",newEmployee.getFirstName());
        criteriaEmployeeUpdate.set("lastName",newEmployee.getLastName());
        criteriaEmployeeUpdate.set("email",newEmployee.getEmail());
        criteriaEmployeeUpdate.set("type",newEmployee.getType());
        if(isNewImage == true)
            criteriaEmployeeUpdate.set("employeeImage",newEmployee.getEmployeeImage());
        criteriaEmployeeUpdate.where(employeeInDatabase.get("firstName").in(oldEmployee.getFirstName())
                ,employeeInDatabase.get("lastName").in(oldEmployee.getLastName())
                ,employeeInDatabase.get("email").in(oldEmployee.getEmail()));

        entityManager.getTransaction().begin();
        if(criteriaImageUpdate != null)
            entityManager.createQuery(criteriaImageUpdate).executeUpdate();
        entityManager.createQuery(criteriaAccountUpdate).executeUpdate();
        entityManager.createQuery(criteriaEmployeeUpdate).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    public Employee findEmployeeByFirstAndLastNameAndMail(Employee employee) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeInDatabase = criteriaQuery.from(Employee.class);

        criteriaQuery.select(employeeInDatabase).where(employeeInDatabase.get("firstName").in(employee.getFirstName())
                ,employeeInDatabase.get("lastName").in(employee.getLastName())
                ,employeeInDatabase.get("email").in(employee.getEmail()));
        Employee foundedEmployee;
        try {
            foundedEmployee = entityManager.createQuery(criteriaQuery).getSingleResult();
        }
        catch (NoResultException e){
            foundedEmployee = null;
        }
        entityManager.clear();
        return  foundedEmployee;
    }
}
