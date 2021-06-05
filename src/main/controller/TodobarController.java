package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Project;
import model.Task;
import ui.EditTask;
import ui.EditTaskDemo;
import ui.ListView;
import ui.PomoTodoApp;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoOptionsPopUpFxmlFile = new File(todoOptionsPopUpFXML);
    private File todoActionsPopUpFxmlFile = new File(todoActionsPopUpFXML);
    private static Stage primaryStage;

    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private Task task;
    private JFXPopup todoOptionsPopUp;
    private JFXPopup todoActionsPopUp;

    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodoOptionsPopUp();
        loadTodoOptionsPopUpActionListener();
        loadTodoActionsPopUp();
        loadTodoActionsPopUpActionListener();
    }

    // EFFECTS: load todooptions pop up (pritorties)
    private void loadTodoOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoOptionsPopUpController());
            todoOptionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: load todoactions pop up (edit, delete)
    private void loadTodoActionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoActionsPopUpController());
            todoActionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: show view selector pop up when its icon is clicked
    private void loadTodoOptionsPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoOptionsPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        12,
                        15);
            }
        });
    }

    // EFFECTS: show options pop up when its icon is clicked
    private void loadTodoActionsPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoActionsPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        -12,
                        15);
            }
        });
    }



    //Inner class: option pop up controller
    class TodoOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectIndex) {
                case 0:
                    Logger.log("TodobarOptionsPopUpController", "Editing " + task.getDescription());
                    editTask(task);
                    break;
                case 1:
                    Logger.log("TodobarOptionsPopUpController", "Deleted" + task.getDescription());
                    removeTask(task);
                    PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
                    break;
                default:
                    Logger.log("TodobarOptionsPopUpController", "No action is implemented for "
                            + "the selected option");
            }
            todoOptionsPopUp.hide();
        }


        // EFFECTS: calls the edit task scene
        private void editTask(Task task) {
            Logger.log("Editing", "Opening Editor.");
            PomoTodoApp.setScene(new EditTask(task));
        }

        // EFFECTS: removes this task from our list
        private void removeTask(Task task) {
            List<Task> myTasks = PomoTodoApp.getTasks();
            myTasks.remove(task);
        }

    }


    //Inner class: action popup controller
    class TodoActionsPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {

            int selectIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            switchStatement(selectIndex);
            todoActionsPopUp.hide();
        }

        //EFFECTS: selects the appropriate menu item based on button selection
        private void switchStatement(Integer selectIndex) {
            switch (selectIndex) {
                case 0: Logger.log("TodoActionsPopUpController", "Todo filter is not yet supported.");
                    break;
                case 1: Logger.log("TodoActionsPopUpController", "UpNext filter is not yet supported.");
                    break;
                case 2: Logger.log("TodoActionsPopUpController", "InProgress filter is not supported.");
                    break;
                case 3: Logger.log("TodoActionsPopUpController", "Done filter is not yet supported.");
                    break;
                case 4: Logger.log("TodoActionsPopUpController", "Pomodoro! filter is not supported.");
                    break;
                default: Logger.log("TodobarActionsPopUpController", "No action is implemented for "
                            + "the selected option");
            }
        }
    }
}

