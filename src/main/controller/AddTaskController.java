package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import model.Project;
import model.Task;
import ui.ListView;
import ui.PomoTodoApp;
import utility.Logger;

import java.util.List;

// Controller class for AddTask UI
public class AddTaskController {
    @FXML
    private JFXTextArea description;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    private Task task;
    
    // EFFECTS: try to create a new task from the given description
    //          add the new task to the list of tasks in PomoTodoApp
    //          return to the list view UI
    @FXML
    public void saveTask() {
        Logger.log("AddTaskController", "Add new Task with description " + description.getText());
        try {
            Task task = new Task(description.getText());
            attemptToAddTask(task);
            //PomoTodoApp.getTasks().add(task);
        } catch (RuntimeException e) {
            Logger.log("AddTaskController", "Failed to create a new task from description " + description.getText());
        } finally {
            returnToListView();
        }
    }

    // EFFECTS: Add new tasks that are not already on the list
    private void attemptToAddTask(Task task) {
        Project myProj = new Project("currentList");
        List<Task> myTasks = PomoTodoApp.getTasks();
        for (Task t : myTasks) {
            myProj.add(t);
        }
        if (!myProj.contains(task)) {
            PomoTodoApp.getTasks().add(task);
            Logger.log("AddNewTask", "Added new task.");
        } else {
            Logger.log("AddNewTask", "Cannot create two identical tasks in the same project!");
        }
    }
    
    // EFFECTS: return to the list view UI
    @FXML
    public void cancelNewTask() {
        Logger.log("AddTaskController", "Edit Task cancelled.");
        returnToListView();
    }
    
    // EFFECTS: return to the list view UI
    private void returnToListView() {
        Logger.log("AddTaskController", "Return to the list view UI.");
        PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
    }
}
