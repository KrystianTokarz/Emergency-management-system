package server.gui.administrator.institutionManagement.institutionState;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import server.communication.EchoThread;
import server.gui.administrator.institutionManagement.builder.InstitutionBuilder;
import server.gui.administrator.institutionManagement.builder.InstitutionBuilderImpl;
import server.gui.administrator.institutionManagement.builder.NewInstitutionBuildDirector;
import server.message.Message;
import server.model.institution.Institution;
import server.model.message.MessageType;

import java.util.HashMap;
import java.util.Map;

public class StateEditing implements InstitutionState {


    Button buttonToSet = new Button();

    Label labelToSet = new Label();

    @Override
    public void setView(Button buttonToSet, Label labelToSet){
        this.buttonToSet = buttonToSet;
        this.labelToSet = labelToSet;
        buttonToSet.setText("Edit");
        labelToSet.setText("Edit Institution");
    }

    @Override
    public void doAction(String institutionName, String institutionType, String provinceType, String localityType, String streetName, String numberName, ImageView imageView, boolean selectedValue,Institution conditionalValue) {

        InstitutionBuilder institutionBuilder = new InstitutionBuilderImpl();
        NewInstitutionBuildDirector newInstitutionBuildDirector = new NewInstitutionBuildDirector(institutionBuilder);
        Institution newInstitution = newInstitutionBuildDirector.construct(institutionName,
                institutionType,
                provinceType,
                localityType,
                streetName,
                numberName,
                imageView,
                selectedValue);
        Map<String , Institution> map = new HashMap<>();
        map.put("old",conditionalValue);
        map.put("new",newInstitution);
        Message message = new Message.MessageBuilder(MessageType.EDIT_INSTITUTION)
                .object(map)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }


}
