package de.churl.feeder;

import de.churl.feeder.domain.EventType;
import de.churl.feeder.gruppen2.EventBuilder;
import de.churl.feeder.gruppen2.event.Event;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
    public TreeView<Event> tree;

    private Stage primaryStage;
    private final List<Event> eventlist = new LinkedList<>();

    public void initialize() {
        eventtype.getItems().addAll(EventType.CREATESINGLE,
                                    EventType.CREATEMULTI,
                                    EventType.ADD,
                                    EventType.REMOVE,
                                    EventType.DESTROY);
    }

    void setStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    public void eventtypeSelect(ActionEvent actionEvent) {
        switch (eventtype.getValue()) {
            case CREATESINGLE:
            case ADD:
            case REMOVE:
                groups.setDisable(true);
                users.setDisable(false);
                break;
            case CREATEMULTI:
                groups.setDisable(false);
                users.setDisable(false);
                break;
            case DESTROY:
                groups.setDisable(true);
                users.setDisable(true);
                break;
        }
    }

    @FXML
    public void addBtn(ActionEvent actionEvent) {
        switch (eventtype.getValue()) {
            case CREATESINGLE:
                eventlist.addAll(EventBuilder.completeGroup(users.getValue()));
                break;
            case ADD:
                eventlist.addAll(EventBuilder.addUserEvents(users.getValue(), randomGroup()));
                break;
            case REMOVE:
                eventlist.addAll(EventBuilder.deleteUserEvents(users.getValue(), eventlist));
                break;
            case CREATEMULTI:
                eventlist.addAll(EventBuilder.completeGroups(groups.getValue(), users.getValue()));
                break;
        }
    }

    private String randomUsers(long groupid) {
        return null;
    }

    @FXML
    public void removeBtn(ActionEvent actionEvent) {

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

    private long randomGroup() {
        int index = (new Random()).nextInt(eventlist.size() - 1);

        return eventlist.get(index).getGroupId();
    }
}
