package server.gui.distributor.MEMENTOO_JESZCZE_NIE_PODPIETE;

public class Originator {
    private String[] userTableColumnName;
    private String[] systemTableColumnName;
    private int numberSatelliteToggleButton;

    public void setState(String[] userTableColumnName, String[] systemTableColumnName, int numberSatelliteToggleButton){
        this.userTableColumnName = userTableColumnName;
        this.systemTableColumnName = systemTableColumnName;
        this.numberSatelliteToggleButton = numberSatelliteToggleButton;
    }

    public Originator getState(){
        return this;
    }

    public Memento saveStateInmMomento(){
        return new Memento(userTableColumnName,systemTableColumnName,numberSatelliteToggleButton);
    }

    public void getStateFromMemento(Memento memento){
        userTableColumnName = memento.getUserTableColumnName();
        systemTableColumnName = memento.getSystemTableColumnName();
        numberSatelliteToggleButton = memento.getNumberSatelliteToggleButton();
    }
}
