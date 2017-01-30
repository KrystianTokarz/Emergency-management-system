package server.gui.distributor.notificationPanel.decorator;


/**
 * Class which decorating notification (report a boat into new notification)
 */
public class BoatDecorator extends  ActionDecorator {

    public BoatDecorator(AdditionAction decoratedAction) {
        super(decoratedAction);
    }

    public String report( ){

        String report = super.report();
        return report + " BOAT";
    }
}
