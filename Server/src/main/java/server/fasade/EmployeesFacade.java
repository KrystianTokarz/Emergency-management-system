package server.fasade;

import server.model.employee.Employee;
import server.model.employee.EmployeeAccount;
import server.repository.EmployeeRepository;
import server.repository.InstitutionRepository;
import server.repository.LocalizationRepository;
import server.repository.NotificationRepository;

import java.util.List;
import java.util.Map;

/**
 * Facade to support operation on Employees (proxy-end)
 */
public class EmployeesFacade  {

    private EmployeesFacade() {
    }

    private static EmployeesFacade instance = null;

    public static EmployeesFacade getInstance() {
        if (instance == null) {
            instance = new EmployeesFacade();
        }
        return instance;
    }

    private EmployeeRepository employeeRepository = EmployeeRepository.getInstance();


    public Employee findEmployeeById(Long personId){
        return  employeeRepository.findById(personId);
    }

    public List<Employee> findAllEmployeesForAdministrator() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeByLoginAndPassword(Object employeeAccountObject){
        EmployeeAccount employeeAccount = (EmployeeAccount) employeeAccountObject;
        String login = employeeAccount.getLogin();
        String password = employeeAccount.getPassword();
        return employeeRepository.findEmployeeByLoginAndPassword(login,password);
    }

    public List<Employee> saveNewEmployee(Object employeeObject){
        Employee employee = (Employee) employeeObject;
        employeeRepository.saveNewEmployee(employee);
        return employeeRepository.findAll();
    }

    public List<Employee>  deleteEmployee(Object employeesObject){
        List<Employee> employees = (List<Employee>) employeesObject;
        employeeRepository.deleteEmployee(employees);
        return employeeRepository.findAll();
    }

    public Employee findEmployeeByFirstAndLastNameAndMail(Object employeeObject){
        Employee employee = (Employee) employeeObject;
        return employeeRepository.findEmployeeByFirstAndLastNameAndMail(employee);
    }

    public List<Employee>  updateEmployee(Object employeeMapObject){
        Map<String , Employee> employeeMap = (Map<String , Employee> ) employeeMapObject;
        employeeRepository.updateEmployee(employeeMap);
        return employeeRepository.findAll();
    }

    public Employee updateEmployeeFromDistributor(Object employeeMapObject){
        Map<String , Employee> employeeMap = (Map<String , Employee> ) employeeMapObject;
        employeeRepository.updateEmployee(employeeMap);
        return employeeRepository.findEmployeeByFirstAndLastNameAndMail(employeeMap.get("new"));
    }
}
