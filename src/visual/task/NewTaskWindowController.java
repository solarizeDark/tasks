package visual.task;

import db.DBManager;
import entity.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NewTaskWindowController {

    @FXML
    private TextField dateField;
    @FXML
    private CheckBox statusField;
    @FXML
    private TextField taskTextField;
    @FXML
    private Button saveTaskButton;
    @FXML
    private Button cancelButton;
    private DBManager dbManager;

    public NewTaskWindowController(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @FXML
    public void initialize(){
        saveTaskButton.setOnAction(e -> saveTask());
        cancelButton.setOnAction(e -> cancel());
    }

    public void saveTask() {
        this.dbManager.insert(new Task(taskTextField.getText(), dateField.getText(),
                statusField.isSelected()));
    }

    public void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}