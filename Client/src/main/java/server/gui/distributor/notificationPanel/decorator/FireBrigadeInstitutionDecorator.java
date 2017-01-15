package server.gui.distributor.notificationPanel.decorator;

import server.message.mediator.DistributorCommandMediator;

import java.util.List;

public class FireBrigadeInstitutionDecorator  extends InstitutionNotifciationDecorator {



    public FireBrigadeInstitutionDecorator(InstitutionNotification messageDecorator){
        super(messageDecorator);
    }

    public List<String> getInstitution(){
        DistributorCommandMediator commandMediator = DistributorCommandMediator.getInstance();
        String fireBrigadeInstitution = commandMediator.getFireBrigadeNameInstitution();
        if(fireBrigadeInstitution != "No selected")
            super.setInstitutionName(fireBrigadeInstitution);
        return super.getInstitution();
    }
}
