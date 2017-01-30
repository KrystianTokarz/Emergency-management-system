package server.message.facade;

import server.message.mediator.CommandMediator;
import server.message.mediator.DistributorCommandMediator;
import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.localization.Province;

import java.util.List;

/**
 * Facade pattern for support receiving data from server (proxy-end)
 */
public class ClientFacadeProxy {

    private ClientFacadeProxy() {
    }

    private static ClientFacadeProxy instance = null;

    public static ClientFacadeProxy getInstance() {
        if (instance == null) {
            instance = new ClientFacadeProxy();
        }
        return instance;
    }

    private Object objectForMediator;
    private DistributorCommandMediator distributorCommandMediator = DistributorCommandMediator.getInstance();
    private CommandMediator commandMediator = CommandMediator.getInstance();


    public void setObjectForMediator(Object objectForMediator){
        this.objectForMediator = objectForMediator;
    }


    public void setProgramPanelInformation(){
       distributorCommandMediator.setProgramPanelInformation((Employee) objectForMediator);
    }

   public void setNotificationForDistributor() {
       distributorCommandMediator.setNotificationForDistributor(objectForMediator);
    }

    public void setNotificationId() {
        distributorCommandMediator.setNotificationId((Long) objectForMediator);
    }

    public void setResultNotificationInServer(){
        distributorCommandMediator.setResultNotificationInServer((Boolean) objectForMediator);
    }

    public void executeLogin(){
        commandMediator.executeLogin(objectForMediator);
    }

    public void setEmployeeList(){
        commandMediator.setEmployeeList(objectForMediator);
    }

    public void giveEmployeeDataForAdministrator(){
        commandMediator.giveEmployeeData((Employee) objectForMediator);
    }

    public void giveEmployeeDataForDistributor(){
        distributorCommandMediator.giveEmployeeData((Employee) objectForMediator);
    }

    public void setAllInstitutionList(){
        commandMediator.setAllInstitutionList(objectForMediator);
    }

    public void setInstitutionForServer(){
        distributorCommandMediator.setInstitutionForServer((List<Institution>) objectForMediator);
    }

    public void setLocalizationForServer(){
        distributorCommandMediator.setLocalizationForServer((List<Province>) objectForMediator);
    }

    public void setInstitutionFromServer(){
        commandMediator.setInstitutionFromServer((Institution) objectForMediator);
    }
}
