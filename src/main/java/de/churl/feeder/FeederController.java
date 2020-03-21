package de.churl.feeder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.churl.feeder.domain.EventType;
import de.churl.feeder.gruppen2.EventBuilder;
import de.churl.feeder.gruppen2.event.CreateGroupEvent;
import de.churl.feeder.gruppen2.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
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

    private final TreeItem<Event> treeroot = new TreeItem<>();
    @FXML
    public TextField groups;

    @FXML
    public TextField groupId;

    @FXML
    public TreeView<Event> tree;

    private Stage primaryStage;
    private final List<Event> eventlist = new LinkedList<>();
    @FXML
    public TextField users;

    public void initialize() {
        eventtype.getItems().addAll(EventType.CREATESINGLE,
                                    EventType.CREATEMULTI,
                                    EventType.ADD,
                                    EventType.REMOVE,
                                    EventType.DESTROY);

        tree.setRoot(treeroot);
        tree.setShowRoot(false);
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
        List<Event> newEvents = new LinkedList<>();

        switch (eventtype.getValue()) {
            case CREATESINGLE:
                newEvents.addAll(EventBuilder.completeGroup(Integer.parseInt(users.getText())));
                break;
            case ADD:
                newEvents.addAll(EventBuilder.addUserEvents(Integer.parseInt(users.getText()), randomGroup()));
                break;
            case REMOVE:
                newEvents.addAll(EventBuilder.deleteUserEvents(Integer.parseInt(users.getText()), eventlist));
                break;
            case CREATEMULTI:
                newEvents.addAll(EventBuilder.completeGroups(Integer.parseInt(groups.getText()), Integer.parseInt(users.getText())));
                break;
        }

        updateTree(newEvents);
        eventlist.addAll(newEvents);
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

    private String getData() throws JsonProcessingException {
        StringBuilder builder = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();

        builder.append("INSERT INTO event VALUES\n");
        for (int i = 0; i < eventlist.size(); i++) {
            builder.append("(");
            builder.append(i + 1);
            builder.append(",");
            builder.append(eventlist.get(i).getGroupId());
            builder.append(",'");
            builder.append(eventlist.get(i).getUserId());
            builder.append("','");
            builder.append(mapper.writeValueAsString(eventlist.get(i)));
            builder.append("',");
            builder.append("TRUE");
            builder.append("),\n");
        }

        builder.replace(builder.lastIndexOf(","), builder.lastIndexOf(",") + 1, ";");

        return builder.toString();
    }

    private long randomGroup() {
        int index = (new Random()).nextInt(eventlist.size() - 1);

        return eventlist.get(index).getGroupId();
    }

    private void updateTree(List<Event> newEvents) {
        for (Event event : newEvents) {
            if (event instanceof CreateGroupEvent) {
                treeroot.getChildren().add(new TreeItem<>(event));
            }

            treeroot.getChildren().parallelStream()
                    .filter(item -> item.getValue().getGroupId().equals(event.getGroupId()))
                    .findFirst()
                    .get()
                    .getChildren()
                    .add(new TreeItem<>(event));
        }
    }
}
