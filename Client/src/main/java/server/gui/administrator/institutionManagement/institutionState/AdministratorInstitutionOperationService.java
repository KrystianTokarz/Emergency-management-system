package server.gui.administrator.institutionManagement.institutionState;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import server.gui.administrator.employeeManagement.flyweight.factory.ImageFlyweightFactory;
import server.gui.administrator.institutionManagement.InstitutionForTable;
import server.gui.administrator.institutionManagement.institutionState.InstitutionState;
import server.gui.administrator.institutionManagement.institutionState.StateContext;
import server.message.mediator.CommandMediator;
import server.model.employee.Employee;
import server.model.institution.Institution;

public class AdministratorInstitutionOperationService {

    private CommandMediator commandMediator;
    private StateContext stateContext;
    private int valueSelectedView;
    private Institution institutionFromServer;
    private TableView<InstitutionForTable> table;
    private Button button;
    private Label label;

    public AdministratorInstitutionOperationService(CommandMediator commandMediator, StateContext stateContext, int valueSelectedView,TableView<InstitutionForTable> table) {
        this.commandMediator = commandMediator;
        this.stateContext = stateContext;
        this.valueSelectedView = valueSelectedView;
        this.table = table;
    }

    public void setButtonAndLabelToView(Button button, Label label) {
        this.button = button;
        this.label = label;

    }

    public int getValueSelectedView(){
        return this.valueSelectedView;
    }

    public void setInstitutionFromServer(Institution institutionFromServer) {
        this.institutionFromServer = institutionFromServer;

    }
    public Institution giveInstitutionFromServer(){
            return institutionFromServer;
    }

    public void setView(){
        this.stateContext.getState().setView(button,label);
    }


    public void sendDataForSelectedViewInstitution(String institutionName, String institutionType, String provinceType, String localityType, String streetName, String numberName, ImageView imageView, boolean selectedValue,Institution conditionalValue) {
        this.stateContext.getState().doAction(institutionName, institutionType, provinceType, localityType, streetName, numberName,imageView,selectedValue,conditionalValue);
    }
}
