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
 * Facade to support operation on Employees
 */
public class EmployeesFacade {

    EmployeeRepository employeeRepository = EmployeeRepository.getInstance();


    public Employee findEmployeeById(Long personId){
        return  employeeRepository.findById(personId);
    }

    public List<Employee> findAllEmployeesForAdministrator() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeByLoginAndPassword(EmployeeAccount employeeAccount){
        String login = employeeAccount.getLogin();
        String password = employeeAccount.getPassword();
        return employeeRepository.findEmployeeByLoginAndPassword(login,password);
    }

    public List<Employee> saveNewEmployee(Employee employee){
        employeeRepository.saveNewEmployee(employee);
        return employeeRepository.findAll();
    }

    public List<Employee>  deleteEmployee(List<Employee> employees){
        employeeRepository.deleteEmployee(employees);
        return employeeRepository.findAll();
    }

    public Employee findEmployeeByFirstAndLastNameAndMail(Employee employee){
        return employeeRepository.findEmployeeByFirstAndLastNameAndMail(employee);
    }

    public List<Employee>  updateEmployee(Map<String , Employee> employeeMap){
        employeeRepository.updateEmployee(employeeMap);
        return employeeRepository.findAll();
    }

    public Employee updateEmployeeFromDistributor(Map<String , Employee> employeeMap){
        employeeRepository.updateEmployee(employeeMap);
        return employeeRepository.findEmployeeByFirstAndLastNameAndMail(employeeMap.get("new"));
    }
}
