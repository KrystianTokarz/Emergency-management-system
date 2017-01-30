package server.gui.administrator.institutionManagement.institutionState;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import server.model.institution.Institution;

/**
 * Interface for State pattern for operation on state about Institution view
 */
public interface InstitutionState {

    public void setView(Button button, Label label);

    void doAction(String institutionName,String institutionType, String provinceType, String localityType, String streetName, String numberName, ImageView imageView, boolean selectedValue,Institution conditionalValue);
}
