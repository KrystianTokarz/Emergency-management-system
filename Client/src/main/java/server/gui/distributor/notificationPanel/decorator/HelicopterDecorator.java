package server.gui.distributor.notificationPanel.decorator;

/**
 * Class which decorating notification (report a helicopter into new notification)
 */
public class HelicopterDecorator extends  ActionDecorator {

    public HelicopterDecorator(AdditionAction decoratedAction) {
        super(decoratedAction);
    }

    public String report(){
        String report = super.report();
        return report + " HELICOPTER";
    }
}
