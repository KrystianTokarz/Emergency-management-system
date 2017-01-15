package server.gui.distributor.notificationPanel.decorator;

import java.util.List;

public abstract  class InstitutionNotifciationDecorator implements InstitutionNotification {

    protected InstitutionNotification decoratedMessage;

    public InstitutionNotifciationDecorator(InstitutionNotification message){
        this.decoratedMessage = message;

    }

    public List<String> getInstitution(){
        return decoratedMessage.getInstitution();
    }


    public void setInstitutionName(String name){
        decoratedMessage.setInstitutionName(name);
    }

}
