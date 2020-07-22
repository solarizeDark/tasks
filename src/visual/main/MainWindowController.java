package visual.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import visual.search.SearchWindow;
import visual.task.NewTaskWindow;

public class MainWindowController {

    @FXML
    private Button searchButton;
    @FXML
    private Button createTaskButton;

    @FXML
    public void initialize(){

        createTaskButton.setOnAction(e -> {
            try {
                createTaskWindowOpen();
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }
        });

        searchButton.setOnAction(e -> {
            try {
                searchWindowOpen();
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }
        });

    }

    public void createTaskWindowOpen() throws Exception {
        (new NewTaskWindow()).start(new Stage());
    }

    public void searchWindowOpen() throws Exception {
        (new SearchWindow()).start(new Stage());
    }

}