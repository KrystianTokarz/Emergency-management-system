package server;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import server.communication.ThreadedEchoClient;

import java.io.IOException;
import java.util.Optional;

public class Main  extends Application{

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/loginPanelView.fxml"));
        primaryStage.setTitle("Application ");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        ThreadedEchoClient x = new ThreadedEchoClient();
        x.start();

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog  ");
            alert.setHeaderText(null);
            alert.setContentText("You are sure ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                x.interrupt();
                try {
                    x.getSocket().shutdownInput();
                    x.getSocket().shutdownOutput();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                primaryStage.close();
            }
        });

    }

}



