package de.churl.feeder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FeederMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/index.fxml"));
        Parent root = loader.load();

        FeederController controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("Gruppen2 Feeder");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }
}
