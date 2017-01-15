package server.gui.distributor.notificationPanel.decorator;

import server.message.mediator.DistributorCommandMediator;

import java.io.Serializable;
import java.util.List;

public class PoliceInstitutionDecorator extends InstitutionNotifciationDecorator implements Serializable {

    public PoliceInstitutionDecorator(InstitutionNotification messageDecorator){
        super(messageDecorator);
    }

    public List<String> getInstitution(){
        DistributorCommandMediator commandMediator = DistributorCommandMediator.getInstance();
        String policeNameInstitution = commandMediator.getPoliceNameInstitution();
        if(policeNameInstitution != "No selected")
            super.setInstitutionName(policeNameInstitution);
        return super.getInstitution();
    }


}
