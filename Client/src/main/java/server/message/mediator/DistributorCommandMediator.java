package server.message.mediator;

import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import server.gui.distributor.DistributorService;
import server.gui.distributor.employeeData.DistributorEmployeeEditService;
import server.gui.distributor.factory.NotificationForDistributorTables;
import server.gui.distributor.notificationPanel.DistributorNotificationService;
import server.gui.distributor.phone.DistributorPhoneService;
import server.gui.distributor.receivingPanel.CallerForTable;
import server.gui.distributor.receivingPanel.DistributorReceivingService;
import server.message.command.distributor.NotificationForAllApplicationListCommand;
//import server.message.command.distributor.NotificationForDistributorListCommand;
import server.message.command.institutions.InstitutionsForNotificationCommand;
import server.message.command.institutions.LocalizationForNotificationCommand;
import server.message.command.institutions.OneInstitutionCommand;
import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.institution.InstitutionType;
import server.model.localization.Locality;
import server.model.localization.Province;
import server.model.localization.Street;
import server.model.message.MessageWithNotification;
import server.model.message.SecondMessageWithNotification;
import server.model.notification.Notification;

import java.util.List;

public class DistributorCommandMediator {

    private DistributorService distributorService;
    private DistributorPhoneService distributorPhoneService;
    private DistributorEmployeeEditService distributorEmployeeEditService;
    private LocalizationForNotificationCommand localizationForNotificationCommand;
    private NotificationForAllApplicationListCommand notificationForApplicationListCommand;
    private InstitutionsForNotificationCommand institutionsForNotificationCommand;
    private OneInstitutionCommand oneInstitutionCommand;
    private DistributorReceivingService distributorReceivingService;
    private DistributorNotificationService distributorNotificationService;

    private static DistributorCommandMediator instance = null;


    private DistributorCommandMediator(){
    }

    public static DistributorCommandMediator getInstance(){
        if (instance == null)
            instance = new DistributorCommandMediator();
        return instance;
    }

    public void registerDistributorService(DistributorService distributorService){
        this.distributorService = distributorService;
    }

    public void registerDistributorEmployeeEditService(DistributorEmployeeEditService distributorEmployeeEditService){
        this.distributorEmployeeEditService = distributorEmployeeEditService;
    }

    public void registerDistributorPhoneService(DistributorPhoneService distributorPhoneService) {
        this.distributorPhoneService=distributorPhoneService;
    }

    public void setProgramPanelInformation(Employee employee){
        distributorService.setFirstName(employee.getFirstName());
        distributorService.setLastName(employee.getLastName());
        distributorService.setEmail(employee.getEmail());
        if(employee.getEmployeeImage()!= null)
            distributorService.setImage(employee.getEmployeeImage().getImage());
    }

    public String getUserFirstName(){
        return distributorService.getFirstName();
    }

    public String getUserLastName(){
        return distributorService.getLastName();
    }

    public String getUserEmail(){
        return distributorService.getEmail();
    }

    public String loadEmployeeFirstAndLastName(){
        return distributorService.loadEmployeeFirstAndLastName();
    }

    public byte[] loadEmployeeImageView()
    {
        return distributorService.loadEmployeeImageView();
    }

    public void executeLogout(){
        distributorService.logout();
    }


    public BorderPane createGoogleMap(WebEngine webEngine) {
        return distributorService.createGoogleMap(webEngine);
    }

    public Employee getEmployeeToEdit() {
        return distributorService.getEmployeeToEdit();
    }

    public void sendForEmployeeDataToEdit(Employee employee) {
        distributorService.sendForEmployeeDataToEdit(employee);
    }


    public void giveEmployeeData(Employee employee) {
        distributorEmployeeEditService.setEmployeeFromServer(employee);
    }

    public Employee giveEmployeeFromServer(){
        return distributorEmployeeEditService.giveEmployeeFromServer();
    }

    public void sendDataToEditEmployee(String firstName, String lastName, String email, String password, String login, ImageView imageView, Employee originalEmployee) {
        distributorEmployeeEditService.sendDataToEditEmployee(firstName,lastName,email,password,login,imageView,originalEmployee);
    }

    public void activeEmergencyAlarm() {
        distributorService.activeEmergencyAlarm();
    }

    public void activePhoneRinging() {
        distributorPhoneService.activePhoneRinging();
    }


    public Timeline give4SecondTimelineForNotificationInApplication() {
        return distributorService.give4SecondTimelineForNotificationInApplication();
    }

    public Timeline give4SecondTimelineForNotificationForUser() {
        return distributorService.give4SecondTimelineForNotificationForUser();
    }

    public Timeline giveTimelineForNotificationCaller(){
        return distributorReceivingService.giveTimelineForNotificationCaller();
    }

//    public void sendMessageFromUserNotification() {
//        distributorService.sendMessageFromUserNotification();
//    }
//
//    public void registerNotificationForDistributorListCommand(NotificationForDistributorListCommand notificationForDistributorListCommand) {
//        this.notificationForDistributorListCommand = notificationForDistributorListCommand;
//    }
//
//    public  void setNotificationListForDistributor(Object param) {
//        distributorService.setNotificationListForDistributor(param);
//    }
//
    public List<Notification> getNotificationForUserTable() {
        return distributorService.getNotificationListForDistributor();
    }

    public void registerNotificationForApplicationListCommand(NotificationForAllApplicationListCommand notificationForApplicationListCommand) {
        this.notificationForApplicationListCommand = notificationForApplicationListCommand;
    }

    public void setNotificationForDistributor(Object param){
        distributorService.setNotificationListForApplication(param);
    }

    public List<Notification> getNotificationForAllApplicationTable() {
        return distributorService.getNotificationForAllApplicationTable();
    }

    public void sendMessageForAllNotification() {
        distributorService.sendMessageForAllNotification();
    }

    public void registerDistributorReceivingService(DistributorReceivingService distributorReceivingService) {
        this.distributorReceivingService=distributorReceivingService;
    }

    public void alarmAllObserver(boolean b) {
        this.distributorService.alarmAllObserver(b);
    }

    public void setObservableCallerForTableList(ObservableList<CallerForTable> observableCallerForTableList) {
        this.distributorService.setObservableCallerForTableList(observableCallerForTableList);
    }

    public void breakForUser(boolean b, TableView tableWithAllSystemNotifications, TableView tableWithUserNotifications, Pane googleMapPane) {
        this.distributorService.breakForUser(b,tableWithAllSystemNotifications,tableWithUserNotifications,googleMapPane);

    }

    public void registerDistributorNotificationService(DistributorNotificationService distributorNotificationService ) {
       this.distributorNotificationService = distributorNotificationService;
    }

    public CallerForTable getCallerData() {
        return this.distributorNotificationService.giveDataCaller();
    }

    public void sendMessageForLocalizationForNotification() {
        distributorReceivingService.sendMessageForLocalizationForNotification();
    }

    public void registerOneInstitutionCommand(OneInstitutionCommand oneInstitutionCommand) {
        this.oneInstitutionCommand = oneInstitutionCommand;
    }

    public void setLocalizationForServer(List<Province> localizationForServer) {
        this.distributorNotificationService.setLocalizationForServer(localizationForServer);
    }

    public List<Street> getStreetForSelectedLocality(String provinceName, String localityName){
        return this.distributorNotificationService.getStreetForSelectedLocality(provinceName,localityName);
    }

    public List<Locality> getLocalityForSelectedProvince(String provinceName){
        return this.distributorNotificationService.getLocalityForSelectedProvince(provinceName);
    }

    public List<String> returnAllProvince(){
        return this.distributorNotificationService.returnAllProvince();
    }

    public String getProvinceForLocality(String localityString) {
        return this.distributorNotificationService.getProvinceForLocality(localityString);
    }

    public void registerLocalizationForNotificationCommand(LocalizationForNotificationCommand localizationForNotificationCommand) {
        this.localizationForNotificationCommand = localizationForNotificationCommand;
    }

    public void registerInstitutionForNotificationCommand(InstitutionsForNotificationCommand institutionsForNotificationCommand) {
        this.institutionsForNotificationCommand = institutionsForNotificationCommand;
    }

    public void setInstitutionForServer(List<Institution> institutionForServer) {
       this.distributorNotificationService.setInstitutionForServer(institutionForServer);
    }

    public void sendForInstitutionDataToEdit(){
        this.distributorNotificationService.sendForInstitutionDataToEdit();
    }

    public List<Institution> getRightInstitutionListForProvinceAndLocality(InstitutionType type, String locality){
        return this.distributorNotificationService.getRightInstitutionListForProvinceAndLocality(type,locality);
    }

    public String getPoliceNameInstitution() {
        return this.distributorNotificationService.getPoliceNameInstitution();
    }

    public String getEmergencyNameInstitution() {
        return this.distributorNotificationService.getEmergencyNameInstitution();
    }

    public String getFireBrigadeNameInstitution() {
        return this.distributorNotificationService.getFireBrigadeNameInstitution();
    }

    public void setInstitutionChoiceBoxForService(ChoiceBox policeChoiceBox, ChoiceBox emergencyChoiceBox, ChoiceBox fireBrigadeChoiceBox) {
        this.distributorNotificationService.setInstitutionChoiceBoxForService(policeChoiceBox,emergencyChoiceBox,fireBrigadeChoiceBox);
    }



    public void saveFirstNotification(MessageWithNotification messageWithNotification) {
        this.distributorNotificationService.saveFirstNotification(messageWithNotification);
    }

    public void setNotificationId(Long notificationId) {
        this.distributorNotificationService.setNotificationId(notificationId);
    }

    public void setResultNotificationInServer(Boolean resultNotificationInServer) {
        this.distributorNotificationService.setResultNotificationInServer(resultNotificationInServer);
    }

    public void saveSecondNotification(SecondMessageWithNotification secondMessageWithNotification) {
        this.distributorNotificationService.saveSecondNotification(secondMessageWithNotification);
    }

    public boolean returnResultOfSaveAllNotificationInDatabase(){
        return distributorNotificationService.returnResultOfSaveAllNotificationInDatabase();
    }


    public void startThread() {
        this.distributorService.startThread();
    }

    public int getStatusOfController() {
        return distributorNotificationService.getStatusOfController();
    }
}
