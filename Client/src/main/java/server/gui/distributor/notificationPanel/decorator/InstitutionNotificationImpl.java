package server.gui.distributor.notificationPanel.decorator;

import java.util.ArrayList;
import java.util.List;

public class InstitutionNotificationImpl implements InstitutionNotification {
    private List<String> institution = new ArrayList<>();

    @Override
    public void setInstitutionName(String name) {
        institution.add(name);
    }

    @Override
    public List<String> getInstitution() {
        return institution;
    }
}
