package http.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import file.FileBackedTasksManager;
import http.KVServer.KVTaskClient;
import manager.Managers;
import models.Epic;
import models.Subtask;
import models.Task;

import static util.ConversionsUtility.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class HttpTaskManager extends FileBackedTasksManager {

    Gson gson = new Gson();
    private final KVTaskClient kvTaskClient;

    public HttpTaskManager(URI url) {
        kvTaskClient = new KVTaskClient(url);
    }

    @Override
    public void save() {
        kvTaskClient.putInKVServer("tasks", gson.toJson(tasks));
        kvTaskClient.putInKVServer("epics", gson.toJson(epics));
        kvTaskClient.putInKVServer("subtasks", gson.toJson(subtasks));
        kvTaskClient.putInKVServer("history", gson.toJson(historyToFileString(inMemoryHistoryManager)));
    }

    public void load(HttpTaskManager httpTaskManager) {
/*
        if (!kvTaskClient.loadFromKVServer("tasks").isEmpty()) {
            tasks = gson.fromJson(kvTaskClient.loadFromKVServer("tasks"),
                    new TypeToken<HashMap<Integer, Task>>() {}.getType());
        }*/

        tasks = gson.fromJson(kvTaskClient.loadFromKVServer("tasks"),
                new TypeToken<HashMap<Integer, Task>>() {}.getType());

        tasksByStartTime.addAll(tasks.values());
        epics = gson.fromJson(kvTaskClient.loadFromKVServer("epics"),
                new TypeToken<HashMap<Integer, Epic>>() {}.getType());
        subtasks = gson.fromJson(kvTaskClient.loadFromKVServer("subtasks"),
                new TypeToken<HashMap<Integer, Subtask>>() {}.getType());
        tasksByStartTime.addAll(subtasks.values());
        String historyJson = kvTaskClient.loadFromKVServer("history");
        loadHistory(historyJson, httpTaskManager);
    }
}
