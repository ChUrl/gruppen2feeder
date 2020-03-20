package de.churl.feeder;

import de.churl.feeder.domain.EventType;
import de.churl.feeder.domain.TreeElement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FeederController {

    @FXML
    public Pane pane;

    @FXML
    public ComboBox<EventType> eventtype;

    @FXML
    public Spinner<Integer> groups;

    @FXML
    public Spinner<Integer> users;

    @FXML
    public TextField groupId;

    @FXML
    public TreeView<TreeElement> tree;

    private Stage primaryStage;

    void setStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    public void removeBtn(ActionEvent actionEvent) {

    }

    @FXML
    public void addBtn(ActionEvent actionEvent) {

    }

    @FXML
    public void saveBtn(ActionEvent actionEvent) throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Open Folder containing data.sql");
        Path path = Paths.get(chooser.showDialog(primaryStage).getPath(), "data.sql");

        Files.write(path, getData().getBytes(StandardCharsets.UTF_8));

        System.out.println(path);
    }

    private String getData() {
        return "Test";
    }
}
