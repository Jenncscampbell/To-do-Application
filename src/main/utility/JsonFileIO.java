package utility;

import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");

    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        TaskParser tp = new TaskParser();
        try {
            FileReader fr = new FileReader(jsonDataFile);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            /// just move on
        }
        try {
            String stringToParse = stringBuilder.toString();
            List<Task> tasks = tp.parse(stringToParse);
            return tasks;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    
    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        JSONArray jsonArray = Jsonifier.taskListToJson(tasks);
        try {
            FileWriter fw = new FileWriter(jsonDataFile);
            fw.write(jsonArray.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
