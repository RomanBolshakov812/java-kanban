package http.server;

import com.google.gson.Gson;
import file.FileBackedTasksManager;
import http.KVServer.KVTaskClient;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class HttpTaskManager extends FileBackedTasksManager {

    Gson gson = new Gson();
    private final KVTaskClient kvTaskClient;

    public HttpTaskManager(URI url) throws IOException, InterruptedException {
        kvTaskClient = new KVTaskClient(url);
    }

    @Override
    public void save() {

        kvTaskClient.put("tasks", gson.toJson(tasks));
        kvTaskClient.put("epics", gson.toJson(epics));
        kvTaskClient.put("subtasks", gson.toJson(subtasks));
        kvTaskClient.put("history", gson.toJson(getHistory()));
    }

    public void load() {
        String tasksJson = kvTaskClient.load("tasks");
        tasks = gson.fromJson(tasksJson, HashMap.class);
        String epicsJson = kvTaskClient.load("tasks");
        epics = gson.fromJson(epicsJson, HashMap.class);
        String subtasksJson = kvTaskClient.load("tasks");
        subtasks = gson.fromJson(subtasksJson, HashMap.class);
        String historyJson = kvTaskClient.load("tasks");
        getHistory().addAll(gson.fromJson(historyJson, ArrayList.class));
    }
}
