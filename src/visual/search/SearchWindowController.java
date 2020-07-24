package visual.search;

import db.DBManager;
import entity.Task;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import java.util.Date;


public class SearchWindowController {

    private DBManager dbManager;
    @FXML
    private TableView<Task> tableTasks;
    @FXML
    private TableColumn<Task, String> dateColumn;
    @FXML
    private TableColumn<Task, String> textColumn;
    @FXML
    private TableColumn<Task, Boolean> statusColumn;
    @FXML
    private TextField dateFromButton;
    @FXML
    private TextField dateToButton;
    @FXML
    private CheckBox statusCheckBox;
    @FXML
    private Button searchButton;
    @FXML
    private TableView resultTable;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;
    public SearchWindowController(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @FXML
    public void initialize() {

        searchButton.setOnAction(e -> search());
        cancelButton.setOnAction(e -> cancel());
        deleteButton.setOnAction(e -> delete());

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        textColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setOnEditCommit(
                t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setDate(t.getNewValue()));
        textColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        textColumn.setOnEditCommit(
                t -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setText(t.getNewValue()));

        statusColumn.setCellValueFactory(param -> {
            Task task = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(task.isStatus());
            booleanProp.addListener((observable, oldValue, newValue) -> {
                    task.setStatus(newValue);
                    dbManager.update(task);
            });
            return booleanProp;
        });

        statusColumn.setCellFactory(p -> {
            CheckBoxTableCell<Task, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

    }

    public void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void delete() {
        Task temp = tableTasks.getSelectionModel().getSelectedItem();
        dbManager.delete(temp);
        tableTasks.getItems().remove(temp);
    }

    public void search() {
        ObservableList<Task> tasks = FXCollections.observableArrayList(
                dbManager.findAllWithCondition(dateFromButton.getText(), dateToButton.getText(), statusCheckBox.isSelected()));
        tableTasks.setItems(tasks);
    }

}
