package server.gui.distributor.notificationPanel.decorator;

import server.message.mediator.DistributorCommandMediator;

import java.io.Serializable;
import java.util.List;

public class EmergencyInstitutionDecorator  extends InstitutionNotifciationDecorator implements Serializable {



    public EmergencyInstitutionDecorator(InstitutionNotification messageDecorator){
        super(messageDecorator);
    }

    public List<String> getInstitution(){
        DistributorCommandMediator commandMediator = DistributorCommandMediator.getInstance();
        String emergencyNameInstitution = commandMediator.getEmergencyNameInstitution();
        if(emergencyNameInstitution != "No selected")
             super.setInstitutionName(emergencyNameInstitution);
        return super.getInstitution();
    }

}
