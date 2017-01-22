package server.gui.distributor.notificationPanel.decorator;

public class BoatDecorator extends  ActionDecorator {


    public BoatDecorator(AdditionAction decoratedAction) {
        super(decoratedAction);
    }

    public String report( ){

        String report = super.report();
        return report + " BOAT";
    }
}
