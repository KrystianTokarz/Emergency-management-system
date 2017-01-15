package server.gui.distributor.receivingPanel;

import javafx.beans.property.SimpleStringProperty;

public class CallerForTable {

    private SimpleStringProperty callerPhoneNumber = new SimpleStringProperty("");
    private SimpleStringProperty callerLocalization  = new SimpleStringProperty("");


    public CallerForTable(String callerPhoneNumber, String callerLocalization) {
        this.callerPhoneNumber.set(callerPhoneNumber);
        this.callerLocalization.set(callerLocalization);
    }

    public String getCallerPhoneNumber() {
        return callerPhoneNumber.get();
    }

    public SimpleStringProperty callerPhoneNumberProperty() {
        return callerPhoneNumber;
    }

    public void setCallerPhoneNumber(String callerPhoneNumber) {
        this.callerPhoneNumber.set(callerPhoneNumber);
    }

    public String getCallerLocalization() {
        return callerLocalization.get();
    }

    public SimpleStringProperty callerLocalizationProperty() {
        return callerLocalization;
    }

    public void setCallerLocalization(String callerLocalization) {
        this.callerLocalization.set(callerLocalization);
    }

    //public static class CallerFactory{

//        public CallerForTable;
//    }

}
