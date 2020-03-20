package de.churl.feeder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FeederMain extends Application {

    private static final int WIDTH = 750;
    private static final int HEIGHT = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/index.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Gruppen2 Feeder");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }
}
