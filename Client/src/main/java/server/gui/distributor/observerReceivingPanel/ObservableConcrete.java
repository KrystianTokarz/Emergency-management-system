package server.gui.distributor.observerReceivingPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class which implements Observable interface for all operations on observable
 */
public class ObservableConcrete implements Observable {

    private List<Observer> observers = new ArrayList<>();
    private boolean state;


    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void disconnect(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);

    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer: observers) {
            observer.update();
        }
    }

    @Override
    public void setState(boolean state) {
        this.state=state;
        notifyAllObservers();
    }
}
