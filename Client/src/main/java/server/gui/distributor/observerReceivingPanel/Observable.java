package server.gui.distributor.observerReceivingPanel;

public interface Observable {

    boolean getState();

    void disconnect(Observer observer);

    void attach(Observer observer);

    void notifyAllObservers();

    void setState(boolean state);

}
