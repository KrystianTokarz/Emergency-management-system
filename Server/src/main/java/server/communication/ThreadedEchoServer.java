package server.communication;

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
    private CommandRegister commandRegister;
    private Socket socket;

    private  ThreadedEchoServer(){
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        createCommandRegister();
    }

    public static ThreadedEchoServer getInstance() {
        if (instance == null) {
            instance = new ThreadedEchoServer();
        }
        return instance;
    }

    private void createCommandRegister(){
        Command loginCommand = new LoginCommand();
        Command employeeListCommand = new EmployeeListCommand();
        Command saveEmployeeCommand = new SaveEmployeeCommand();
        Command deleteEmployeeCommand = new DeleteEmployeeCommand();
        Command editEmployeeCommand = new EditEmployeeCommand();
        Command oneEmployeeCommand = new OneEmployeeCommand();
        Command deleteInstitutionCommand = new DeleteInstitutionCommand();
        Command saveInstitutionCommand = new SaveInstitutionCommand();
        Command oneInstitutionCommand = new OneInstitutionCommand();
        Command editInstitutionCommand = new EditInstitutionCommand();
        Command allInstitutionListCommand = new AllInstitutionListCommand();
        Command editEmployeeFromDistributorCommand = new EditEmployeeFromDistributor();
        Command notificationListForAllApplicationCommand = new NotificationForDistributorListCommand();
        Command localizationForNotificationCommand = new LocalizationForNotificationCommand();
        Command institutionForNotificationCommand = new InstitutionForNotificationCommand();
        Command saveFirstNotificationCommand = new SaveFirstNotificationCommand();

        commandRegister = new CommandRegister();
        commandRegister.addCommand(MessageType.AUTHORIZATION,loginCommand);
        commandRegister.addCommand(MessageType.EMPLOYEE_LIST, employeeListCommand);
        commandRegister.addCommand(MessageType.SAVE_NEW_EMPLOYEE,saveEmployeeCommand);
        commandRegister.addCommand(MessageType.DELETE_EMPLOYEES,deleteEmployeeCommand);
        commandRegister.addCommand(MessageType.EDIT_EMPLOYEE,editEmployeeCommand);
        commandRegister.addCommand(MessageType.SEND_EMPLOYEE,oneEmployeeCommand);
        commandRegister.addCommand(MessageType.ALL_INSTITUTION_LIST,allInstitutionListCommand);
        commandRegister.addCommand(MessageType.DELETE_INSTITUTIONS,deleteInstitutionCommand);
        commandRegister.addCommand(MessageType.SAVE_NEW_INSTITUTION,saveInstitutionCommand);
        commandRegister.addCommand(MessageType.SEND_INSTITUTION,oneInstitutionCommand);
        commandRegister.addCommand(MessageType.EDIT_INSTITUTION,editInstitutionCommand);
        commandRegister.addCommand(MessageType.SEND_EMPLOYEE_DISTRIBUTOR,oneEmployeeCommand);
        commandRegister.addCommand(MessageType.EDIT_EMPLOYEE_DISTRIBUTOR,editEmployeeFromDistributorCommand);
        commandRegister.addCommand(MessageType.SEND_NOTIFICATION,notificationListForAllApplicationCommand);
        commandRegister.addCommand(MessageType.SEND_FOR_LOCALIZATION_FOR_NOTIFICATION,localizationForNotificationCommand);
        commandRegister.addCommand(MessageType.SEND_FOR_INSTITUTION_FOR_LOCALIZATION,institutionForNotificationCommand);
        commandRegister.addCommand(MessageType.SAVE_NEW_FIRST_NOTIFICATION,saveFirstNotificationCommand);
    }

    public void start() {
        while(true){

            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
                new EchoThread(socket,commandRegister).start();
        }
    }
}
