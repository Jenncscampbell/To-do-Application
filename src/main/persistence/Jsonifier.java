package persistence;


import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

// Converts model elements to JSON objects
public class Jsonifier {
    
    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();
        tagJson.put("name", tag.getName());

        return tagJson;
    }
    
    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {

        JSONObject priorityJson = new JSONObject();
        priorityJson.put("important", priority.isImportant());
        priorityJson.put("urgent", priority.isUrgent());

        return priorityJson;
    }
    
    // EFFECTS: returns JSON respresentation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        if (dueDate == null) {
            return null;
        }
        JSONObject dueDateJson = new JSONObject();
        Date myDate = dueDate.getDate();
        Calendar myCal = Calendar.getInstance();
        myCal.setTime(myDate);
        dueDateJson.put("year", myCal.get(Calendar.YEAR));
        dueDateJson.put("month", myCal.get(Calendar.MONTH));
        dueDateJson.put("day", myCal.get(Calendar.DAY_OF_MONTH));
        dueDateJson.put("hour", myCal.get(Calendar.HOUR_OF_DAY));
        dueDateJson.put("minute", myCal.get(Calendar.MINUTE));
        return dueDateJson;
    }
    
    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {

        JSONObject taskJson = new JSONObject();
        taskJson.put("description", task.getDescription());
        taskJson.put("tags", getJsonTagSet(task));
        if (getJsonDate(task) == null) {
            taskJson.put("due-date", JSONObject.NULL);
        } else {
            taskJson.put("due-date", getJsonDate(task));
        }
        taskJson.put("priority", getJsonPriority(task));
        taskJson.put("status", getStatus(task));

        return taskJson;
    }
    
    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray taskArray = new JSONArray();

        for (Task t : tasks) {
            taskArray.put(taskToJson(t));
        }

        return taskArray;
    }



    //EFFECTS: returns a set of the JSON Tag set
    private static ArrayList<JSONObject> getJsonTagSet(Task task) {
        ArrayList<JSONObject> myArray = new ArrayList<>();
        Set<Tag> myTags = task.getTags();
        for (Tag t : myTags) {
            JSONObject myOb = tagToJson(t);
            myArray.add(myOb);
        }
        return myArray;
    }

    //EFFECTS: returns the object for the task date
    private static JSONObject getJsonDate(Task task) {
        DueDate myDueDate = task.getDueDate();
        return dueDateToJson(myDueDate);
    }

    //EFFECTS: returns the object for the task date
    private static JSONObject getJsonPriority(Task task) {
        Priority myPriority = task.getPriority();
        return priorityToJson(myPriority);
    }

    //EFFECTS:prints the statuses without spaces
    private static String getStatus(Task task) {
        Status myStatus = task.getStatus();
        if (myStatus == Status.UP_NEXT) {
            return "UP_NEXT";
        } else if (myStatus == Status.IN_PROGRESS) {
            return "IN_PROGRESS";
        } else {
            return myStatus.toString();
        }
    }
}
