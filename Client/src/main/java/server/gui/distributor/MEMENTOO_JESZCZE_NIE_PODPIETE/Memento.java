package server.gui.distributor.MEMENTOO_JESZCZE_NIE_PODPIETE;

public class Memento {
    private String[] userTableColumnName;
    private String[] systemTableColumnName;
    private int numberSatelliteToggleButton;

    public Memento(String[] userTableColumnName, String[] systemTableColumnName, int numberSatelliteToggleButton){
        this.userTableColumnName = userTableColumnName;
        this.systemTableColumnName = systemTableColumnName;
        this.numberSatelliteToggleButton = numberSatelliteToggleButton;
    }

    public  String[] getUserTableColumnName(){
        return this.userTableColumnName;
    }
    public  String[] getSystemTableColumnName(){
        return this.systemTableColumnName;
    }
    public  int  getNumberSatelliteToggleButton(){
        return this.numberSatelliteToggleButton;
    }
}
