package server.message.mediator;

import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import server.gui.administrator.AdministratorService;
import server.gui.administrator.employeeManagement.EmployeeForTable;
import server.gui.administrator.employeeManagement.AdministratorEmployeeManagementService;
import server.gui.administrator.employeeManagement.employeesAdding.AdministratorEmployeeAddService;
import server.gui.administrator.employeeManagement.employeesEditing.AdministratorEmployeeEditService;
import server.gui.administrator.institutionManagement.AdministratorInstitutionManagementService;
import server.gui.administrator.institutionManagement.InstitutionForTable;
import server.gui.administrator.institutionManagement.institutionState.AdministratorInstitutionOperationService;
import server.gui.login.LoginService;
import server.message.command.employees.AuthorizationCommand;
import server.message.command.employees.EmployeeListCommand;
import server.message.command.employees.OneEmployeeCommand;
import server.message.command.institutions.AllInstitutionListCommand;
import server.message.command.institutions.OneInstitutionCommand;
import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.localization.Locality;
import server.model.localization.ProvinceType;

import java.util.List;

public class CommandMediator implements  Mediator {
    private LoginService loginService;
    private AdministratorService administratorService;
    private AuthorizationCommand authorizationCommand;
    private EmployeeListCommand employeeListCommand;
    private OneEmployeeCommand oneEmployeeCommand;
    private AllInstitutionListCommand allInstitutionListCommand;
    private OneInstitutionCommand oneInstitutionCommand;
    private AdministratorEmployeeManagementService administratorEmployeeManagementService;
    private AdministratorEmployeeAddService administratorEmployeeAddService;
    private AdministratorEmployeeEditService administratorEmployeeEditService;
    private AdministratorInstitutionManagementService administratorInstitutionManagementService;
    private AdministratorInstitutionOperationService administratorInstitutionOperationService;

    private static CommandMediator instance = null;

    private CommandMediator(){
    }

    public static CommandMediator getInstance(){
        if (instance == null)
            instance = new CommandMediator();
        return instance;
    }


    public void registerAuthorizationCommand(AuthorizationCommand authorizationCommand){
        this.authorizationCommand = authorizationCommand;
    }

    public void registerOneEmployeeCommand(OneEmployeeCommand oneEmployeeCommand){
        this.oneEmployeeCommand = oneEmployeeCommand;
    }

    public void registerLoginService(LoginService loginService){
        this.loginService = loginService;
    }

    public void registerAdministratorService(AdministratorService administratorService){
        this.administratorService = administratorService;
    }

    public void registerAdministratorInstitutionManagementService(AdministratorInstitutionManagementService administratorInstitutionManagementService){
        this.administratorInstitutionManagementService = administratorInstitutionManagementService;
    }

    public void registerAdministratorEmployeeManagementService(AdministratorEmployeeManagementService administratorEmployeeManagementService){
        this.administratorEmployeeManagementService = administratorEmployeeManagementService;
    }

    public void registerAdministratorInstitutionAddService(AdministratorInstitutionOperationService administratorInstitutionOperationService){
        this.administratorInstitutionOperationService = administratorInstitutionOperationService;
    }



    public void executeLogin(Object param){
        loginService.loginIntoSystem(param);
    }

    public void executeLogout(){
        administratorService.logout();
    }

    public void sendPasswordAndLogin(Stage stage, String login, String password){
        loginService.sendMessageToServer(stage,login,password);
    }

    public void setProgramPanelInformation(Employee employee){
        administratorService.setFirstName(employee.getFirstName());
        administratorService.setLastName(employee.getLastName());
        if(employee.getEmployeeImage()!= null)
        administratorService.setImage(employee.getEmployeeImage().getImage());
    }
    public byte[] getUserImage(){
        return administratorService.getImage();
    }

    public String getUserFirstName(){
        return administratorService.getFirstName();
    }

    public String getUserLastName(){
        return administratorService.getLastName();
    }


    public String loadEmployeeFirstAndLastName(){
        return administratorService.loadEmployeeFirstAndLastName();
    }

    public byte[] loadEmployeeImageView(){
        return administratorService.loadEmployeeImageView();
    }


    public ObservableList<EmployeeForTable> loadEmployeeTable (){
        return administratorEmployeeManagementService.loadEmployeeTable();
    }

    public void registerEmployeeListCommand(EmployeeListCommand employeeListCommand) {
        this.employeeListCommand = employeeListCommand;
    }

    public void sendMessageForEmployeeList(){
        administratorService.sendMessageForEmployeeList();
    }


    public void setEmployeeList(Object param) {
        administratorEmployeeManagementService.setEmployeeList(param);
    }

    @Override
    public void deleteEmployeesFromTable(ObservableList<EmployeeForTable>  employeeForTable) {
        administratorEmployeeManagementService.deleteEmployees(employeeForTable);
    }


    public void deleteInstitutionFromTable(ObservableList<InstitutionForTable>  institutionForTable) {
        administratorInstitutionManagementService.deleteInstitutions(institutionForTable);
    }

    @Override
    public void registerAdministratorEmployeeAddService(AdministratorEmployeeAddService administratorEmployeeAddService) {
        this.administratorEmployeeAddService = administratorEmployeeAddService;
    }

    @Override
    public void registerAdministratorEmployeeEditService(AdministratorEmployeeEditService administratorEmployeeEditService) {
        this.administratorEmployeeEditService = administratorEmployeeEditService;
    }


    public void sendDataForEmployee(String firstName, String lastName, String email, String type, String password, String login,ImageView image) {
        administratorEmployeeAddService.saveNewEmployee(firstName,lastName,email,type,password,login,image);
    }

    public void sendDataToEditEmployee(String firstName, String lastName, String email,String type,String password, String login,ImageView image, Employee oryginalEmployee){
        administratorEmployeeEditService.editEmployee(firstName,lastName,email,type,password,login,image, oryginalEmployee);
    }

    public void sendForEmployeeDataToEdit(Employee employee){
        administratorEmployeeManagementService.sendForEmployeeDataToEdit(employee);
    }

    public void giveEmployeeData(Employee param){
        administratorEmployeeEditService.setEmployeeFromServer(param);
    }

    public Employee giveEmployeeFromServer(){
       return administratorEmployeeEditService.giveEmployeeFromServer();
    }



    public Timeline give5SecondTimelineForEmployee(){
        return administratorEmployeeManagementService.give5SecondsTimeline();
    }

    //INSTITUTION METHODS

    public Timeline give10SecondTimelineForInstitution() {
        return administratorInstitutionManagementService.give10SecondTimelineForInstitution();
    }
    @Override
    public void sendMessageForInstitutionList() {
        administratorService.sendMessageForInstitutionList();
    }

    @Override
    public void setAllInstitutionList(Object param) {
        administratorInstitutionManagementService.setAllInstitutionList(param);
    }

    @Override
    public void registerOneInstitutionCommand(OneInstitutionCommand oneInstitutionCommand) {
        this.oneInstitutionCommand = oneInstitutionCommand;
    }

    public void registerAllInstitutionListCommand(AllInstitutionListCommand allInstitutionListCommand) {
        this.allInstitutionListCommand = allInstitutionListCommand;
    }

    public ObservableList<InstitutionForTable> loadInstitutionTable (){
        return administratorInstitutionManagementService.loadInstitutionTable();
    }


    public List<Institution> getRightInstitutionList(ProvinceType type){
        return administratorInstitutionManagementService.getRightInstitutionListForProvince(type);
    }
    public ObservableList<InstitutionForTable> convertInstitutionListIntoIntitutionForTableList(List<Institution> allInstitutionList){
        return administratorInstitutionManagementService.convertInstitutionListIntoInstitutionForTableList(allInstitutionList);
    }

    public List<Locality> getLocalityList(Institution institution){
        return administratorInstitutionManagementService.getLocalityList(institution);
    }


    public ObservableList<InstitutionForTable> sortWithInstitutionType(ObservableList<InstitutionForTable> convertedInstitutionForTables,String type) {
        return administratorInstitutionManagementService.sortWithInstitutionType(convertedInstitutionForTables,type);
    }

    public List<Institution> getRightInstitutionListForType(String type,String provinceComboBoxValue,String localityComboBoxValue){
        return  administratorInstitutionManagementService.getRightInstitutionListForType(type,provinceComboBoxValue,localityComboBoxValue);
    }

    public List<Institution> getRightInstitutionListForLocality(ObservableList<InstitutionForTable> convertedInstitutionForTables,String locality){
        return administratorInstitutionManagementService.getRightInstitutionListForLocality(convertedInstitutionForTables, locality);
    }

    public void setAllFXMLElements(Button button, Label label){
        administratorInstitutionOperationService.setButtonAndLabelToView(button, label);
    }

    public void setViewForSelectedView(){
        administratorInstitutionOperationService.setView();
    }

    public void sendDataForSelectedViewInstitution(String institutionName, String institutionType, String provinceType, String localityType, String streetName, String numberName, ImageView imageView, boolean selectedValue,Institution conditionalValue) {
        administratorInstitutionOperationService.sendDataForSelectedViewInstitution(institutionName, institutionType, provinceType, localityType, streetName, numberName,imageView,selectedValue,conditionalValue);
    }

    public int getValueSelectedView(){
        return administratorInstitutionOperationService.getValueSelectedView();
    }

    public Institution giveInstitutionFromServer() {
       return administratorInstitutionOperationService.giveInstitutionFromServer();
    }

    public void setInstitutionFromServer(Institution institution){
        administratorInstitutionOperationService.setInstitutionFromServer(institution);
    }

    public void sendForInstitutionDataToEdit(Institution institution) {
        administratorInstitutionManagementService.sendForInstitutionDataToEdit(institution);
    }

//    public TableView<InstitutionForTable> getTableWithInstitution(){
//        return administratorInstitutionOperationService.getTableWithInstitution();
//    }



//    public void registerAdministratorInstitutionOperationController(AdministratorInstitutionOperationController administratorInstitutionOperationController) {
//        this.administratorInstitutionOperationM
//    }
}
