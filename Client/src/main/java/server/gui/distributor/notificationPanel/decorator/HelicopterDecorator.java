package server.gui.distributor.notificationPanel.decorator;

public class HelicopterDecorator extends  ActionDecorator {


    public HelicopterDecorator(AdditionAction decoratedAction) {
        super(decoratedAction);
    }

    public String report(){
        String report = super.report();
        return report + " HELICOPTER";
    }
}
