package server.communication;

import server.message.chaingOfResponsibility.AbstractChainElement;
import server.message.chaingOfResponsibility.EndElement;
import server.message.chaingOfResponsibility.employees.*;
import server.message.chaingOfResponsibility.institution.*;
import server.message.chaingOfResponsibility.notification.*;
import server.model.message.MessageType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main server class with server socket
 * Create and register all command for application
 */
public class ThreadedEchoServer  {

    private final int port = 6789;
    private ServerSocket serverSocket;
    private AbstractChainElement chainElements;
    private Socket socket;

    public  ThreadedEchoServer(){
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        chainElements = getChainOfElements();
    }



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
        AbstractChainElement editEmployeeForDistributorElement = new EditEmployeeForDistributorElement(MessageType.EDIT_EMPLOYEE_DISTRIBUTOR);
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
