package server.gui.distributor.MEMENTOO_JESZCZE_NIE_PODPIETE;

public class CareTaker {
    private Memento memento;

    public void add(Memento state){
        memento = state;
    }

    public Memento get(int index){
        return memento;
    }
}
