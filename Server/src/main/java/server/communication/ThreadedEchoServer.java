package server.communication;

import server.message.chaingOfResposibility.AbstractChainElement;
import server.message.chaingOfResposibility.EndElement;
import server.message.chaingOfResposibility.employees.*;
import server.message.chaingOfResposibility.institution.*;
import server.message.chaingOfResposibility.notification.*;
import server.message.command.*;
import server.message.command.employees.*;
import server.message.command.institution.*;
import server.message.command.notification.*;
import server.model.message.MessageType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main server class with server socket
 * Create and register all command for application
 */
public class ThreadedEchoServer  {

    private static ThreadedEchoServer instance = null;
    private final int port = 6789;
    private ServerSocket serverSocket;
    private AbstractChainElement chainElements;
    private Socket socket;

    private  ThreadedEchoServer(){
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        chainElements = getChainOfElements();
    }

    public static ThreadedEchoServer getInstance() {
        if (instance == null) {
            instance = new ThreadedEchoServer();
        }
        return instance;
    }

//    private void createCommandRegister(){
//
//



//        commandRegister.addCommand(MessageType.SAVE_NEW_EMPLOYEE,saveEmployeeCommand);
//        commandRegister.addCommand(MessageType.DELETE_EMPLOYEES,deleteEmployeeCommand);
//        commandRegister.addCommand(MessageType.EDIT_EMPLOYEE,editEmployeeCommand);
//        commandRegister.addCommand(MessageType.SEND_EMPLOYEE,oneEmployeeCommand);


//        commandRegister.addCommand(MessageType.ALL_INSTITUTION_LIST,allInstitutionListCommand);
//        commandRegister.addCommand(MessageType.DELETE_INSTITUTIONS,deleteInstitutionCommand);
//        commandRegister.addCommand(MessageType.SAVE_NEW_INSTITUTION,saveInstitutionCommand);
//        commandRegister.addCommand(MessageType.SEND_INSTITUTION,oneInstitutionCommand);
//        commandRegister.addCommand(MessageType.EDIT_INSTITUTION,editInstitutionCommand);

//        commandRegister.addCommand(MessageType.SEND_EMPLOYEE_DISTRIBUTOR,oneEmployeeCommand);
//        commandRegister.addCommand(MessageType.EDIT_EMPLOYEE_DISTRIBUTOR,editEmployeeFromDistributorCommand);
//        commandRegister.addCommand(MessageType.SEND_NOTIFICATION,notificationListForAllApplicationCommand);
//        commandRegister.addCommand(MessageType.SEND_FOR_LOCALIZATION_FOR_NOTIFICATION,localizationForNotificationCommand);
//        commandRegister.addCommand(MessageType.SEND_FOR_INSTITUTION_FOR_LOCALIZATION,institutionForNotificationCommand);
//        commandRegister.addCommand(MessageType.SAVE_NEW_FIRST_NOTIFICATION,saveFirstNotificationCommand);
//        commandRegister.addCommand(MessageType.SAVE_NEW_SECOND_NOTIFICATION,saveSecondNotificationCommand);
//    }

    private AbstractChainElement getChainOfElements(){

        AbstractChainElement loginElement = new LoginElement(MessageType.AUTHORIZATION);
        AbstractChainElement employeeListElement = new EmployeeListElement(MessageType.EMPLOYEE_LIST);
        AbstractChainElement editEmployeeElement = new EditEmployeeElement(MessageType.EDIT_EMPLOYEE);
        AbstractChainElement deleteEmployeeElement = new DeleteEmployeeElement(MessageType.DELETE_EMPLOYEES);
        AbstractChainElement oneEmployeeElement = new OneEmployeeElement(MessageType.SEND_EMPLOYEE);
        AbstractChainElement saveEmployeeElement = new SaveEmployeeElement(MessageType.SAVE_NEW_EMPLOYEE);

        AbstractChainElement allInstitutionElement = new AllInstitutionElement(MessageType.ALL_INSTITUTION_LIST);
        AbstractChainElement deleteInstitutionElement = new DeleteInstitutionElement(MessageType.DELETE_INSTITUTIONS);
        AbstractChainElement editInstitutionElement = new EditInstitutionElement(MessageType.EDIT_INSTITUTION);
        AbstractChainElement oneInstitutionElement = new OneInstitutionElement(MessageType.SEND_INSTITUTION);
        AbstractChainElement saveInstitutionElement = new SaveInstitutionElement(MessageType.SAVE_NEW_INSTITUTION);

        AbstractChainElement distributorDataElement = new DistributorDataElement(MessageType.SEND_EMPLOYEE_DISTRIBUTOR);
        AbstractChainElement editEmployeeForDistributorElement = new EditEmployeeForDistrbutorElement(MessageType.EDIT_EMPLOYEE_DISTRIBUTOR);
        AbstractChainElement institutionForNotificationElement = new InstitutionForNotificationElement(MessageType.SEND_FOR_INSTITUTION_FOR_LOCALIZATION);
        AbstractChainElement localizationForNotificationElement = new LocalizationForNotificationElement(MessageType.SEND_FOR_LOCALIZATION_FOR_NOTIFICATION);
        AbstractChainElement notificationForDistributorListElement = new NotificationForDistributorListElement(MessageType.SEND_NOTIFICATION);
        AbstractChainElement saveFirstNotificationElement = new SaveFirstNotificationElement(MessageType.SAVE_NEW_FIRST_NOTIFICATION);
        AbstractChainElement saveSecondNotificationElement = new SaveSecondNotificationElement(MessageType.SAVE_NEW_SECOND_NOTIFICATION);


        AbstractChainElement endElement = new EndElement();

        loginElement.setNextElement(employeeListElement);
        employeeListElement.setNextElement(deleteEmployeeElement);
        deleteEmployeeElement.setNextElement(oneEmployeeElement);
        oneEmployeeElement.setNextElement(editEmployeeElement);
        editEmployeeElement.setNextElement(saveEmployeeElement);

        saveEmployeeElement.setNextElement(allInstitutionElement);
        allInstitutionElement.setNextElement(deleteInstitutionElement);
        deleteInstitutionElement.setNextElement(saveInstitutionElement);
        saveInstitutionElement.setNextElement(oneInstitutionElement);
        oneInstitutionElement.setNextElement(editInstitutionElement);
        editInstitutionElement.setNextElement(distributorDataElement);

        distributorDataElement.setNextElement(editEmployeeForDistributorElement);
        editEmployeeForDistributorElement.setNextElement(notificationForDistributorListElement);
        notificationForDistributorListElement.setNextElement(localizationForNotificationElement);
        localizationForNotificationElement.setNextElement(institutionForNotificationElement);
        institutionForNotificationElement.setNextElement(saveFirstNotificationElement);
        saveFirstNotificationElement.setNextElement(saveSecondNotificationElement);
        saveSecondNotificationElement.setNextElement(endElement);


        return loginElement;
    }

    public void start() {
        while(true){

            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
                new EchoThread(socket,chainElements).start();
        }
    }
}
