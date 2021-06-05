package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Represents Task parser
public class TaskParser {
    
    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {

        List<Task> taskList = new ArrayList<>();
        JSONArray taskArray = new JSONArray(input);

        for (Object object : taskArray) {
            JSONObject taskJson = (JSONObject) object;
            if (taskJson.has("description") && taskJson.has("tags") && taskJson.has("due-date")
                    && taskJson.has("status") && taskJson.has("priority")) {
                try {
                    String description = taskJson.getString("description");
                    Task myTask = new Task(description);
                    attemptUpdateTask(myTask, taskJson);
                    taskList.add(myTask);
                } catch (Exception e) { //if task description is empty
                    //do nothing with this array object, move on to next
                }
            }
        }
        return taskList;
    }


    private void attemptUpdateTask(Task myTask, JSONObject taskJson) {
        attemptPriorityUpdate(myTask, taskJson);
        attemptTagUpdate(myTask, taskJson);
        attemptDueDateUpdate(myTask, taskJson);
        attemptStatusUpdate(myTask, taskJson);
    }

    private void attemptPriorityUpdate(Task myTask, JSONObject taskJson) {
        JSONObject priorityObject = taskJson.getJSONObject("priority");
        if (priorityObject != null) {
            Priority myPriority = toPriority(priorityObject);
            myTask.setPriority(myPriority);
        }
    }

    private Priority toPriority(JSONObject priorityObj) {
        Boolean importance = priorityObj.getBoolean("important");
        Boolean urgency = priorityObj.getBoolean("urgent");
        Priority myPriority = new Priority(4);
        if (importance) {
            myPriority.setImportant(true);
        }
        if (urgency) {
            myPriority.setUrgent(true);
        }
        return myPriority;
    }

    private void attemptStatusUpdate(Task myTask, JSONObject taskJson) {
        if (taskJson.has("status")) {
            String statusObj = taskJson.getString("status");
            Status myStatus = toStatus(statusObj);
            myTask.setStatus(myStatus);
        }
    }


    private void attemptDueDateUpdate(Task myTask, JSONObject taskJson) {
        try {
            JSONObject dueDateObject = taskJson.getJSONObject("due-date");
            DueDate myDueDate = toDueDate(dueDateObject);
            myTask.setDueDate(myDueDate);
        } catch (JSONException e) {
            //if empty, use default
        }
    }





    private void attemptTagUpdate(Task myTask, JSONObject taskJson) {
        if (taskJson.has("tags")) {
            JSONArray tags = taskJson.getJSONArray("tags");
            List<Tag> myTags = toTagField(tags);
            for (Tag t : myTags) {
                myTask.addTag(t);
            }
        }
    }

    private List<Tag> toTagField(JSONArray tagsToParse) {
        List<Tag> myTags = new ArrayList<>();

        for (Object object : tagsToParse) {
            JSONObject taskJson = (JSONObject) object;
            String name = taskJson.getString("name");
            myTags.add(new Tag(name));
        }
        return myTags;

    }

    private DueDate toDueDate(JSONObject dateJson) {
        int myYear = dateJson.getInt("year");
        int myMonth = dateJson.getInt("month");
        int myDay = dateJson.getInt("day");
        int myHour = dateJson.getInt("hour");
        int myMins = dateJson.getInt("minute");
        Calendar myCal = Calendar.getInstance();
        myCal.set(Calendar.YEAR, myYear);
        myCal.set(Calendar.MONTH, myMonth);
        myCal.set(Calendar.DAY_OF_MONTH, myDay);
        myCal.set(Calendar.HOUR_OF_DAY, myHour);
        myCal.set(Calendar.MINUTE, myMins);
        DueDate myDueDate = new DueDate(myCal.getTime());
        return myDueDate;
    }





    private Status toStatus(String statusObj) {
        if (statusObj.equals("IN_PROGRESS")) {
            return Status.IN_PROGRESS;
        } else if (statusObj.equals("UP_NEXT")) {
            return Status.UP_NEXT;
        } else if (statusObj.equals("TODO")) {
            return Status.TODO;
        } else if (statusObj.equals("DONE")) {
            return Status.DONE;
        }
        return null;
    }




}
