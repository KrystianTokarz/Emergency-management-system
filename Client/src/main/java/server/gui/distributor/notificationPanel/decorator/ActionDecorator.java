package server.gui.distributor.notificationPanel.decorator;

/**
 * Class for adding new decorating elements
 */
public abstract  class ActionDecorator implements AdditionAction {

    protected AdditionAction decoratedAction;
    protected String decoratorValue;

    public ActionDecorator(AdditionAction decoratedAction){
        this.decoratedAction = decoratedAction;
    }

    public String report(){
        return decoratedAction.report();
    }
}
