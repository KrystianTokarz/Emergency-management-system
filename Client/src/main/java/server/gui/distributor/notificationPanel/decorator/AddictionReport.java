package server.gui.distributor.notificationPanel.decorator;

import server.model.localization.Locality;
        import server.model.localization.Province;
        import server.model.localization.Street;
        import server.model.notification.AccidentType;

/**
 * Basic class which is decorating
 */
public class AddictionReport implements AdditionAction{

    private String province;
    private String locality;
    private String street;
    private AccidentType accidentType;

    public AddictionReport(String province, String locality, String street, AccidentType accidentType) {
        this.province = province;
        this.locality = locality;
        this.street = street;
        this.accidentType = accidentType;

    }

    @Override
    public String report() {
        return "Into accident system report special notification for send";
    }
}
