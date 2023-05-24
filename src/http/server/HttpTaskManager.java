package http.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import file.FileBackedTasksManager;
import http.KVServer.KVTaskClient;
import models.Epic;
import models.Subtask;
import models.Task;

import static util.ConversionsUtility.*;

import java.net.URI;
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
        kvTaskClient.putInKVServer("history", gson.toJson(historyToIdString(inMemoryHistoryManager)));
    }

    public void load(HttpTaskManager httpTaskManager) {
        HashMap<Integer, Task> loadTasks = gson.fromJson(kvTaskClient.loadFromKVServer("tasks"),
                new TypeToken<HashMap<Integer, Task>>() {}.getType());
        if (loadTasks != null) {
            tasks.putAll(loadTasks);
        }
        tasksByStartTime.addAll(tasks.values());
        HashMap<Integer, Epic> loadEpics = gson.fromJson(kvTaskClient.loadFromKVServer("epics"),
                new TypeToken<HashMap<Integer, Epic>>() {}.getType());
        if (loadEpics != null) {
            epics.putAll(loadEpics);
        }
        HashMap<Integer, Subtask> loadSubtasks = gson.fromJson(kvTaskClient.loadFromKVServer("subtasks"),
                new TypeToken<HashMap<Integer, Subtask>>() {}.getType());
        if (loadSubtasks != null) {
            subtasks.putAll(loadSubtasks);
        }
        tasksByStartTime.addAll(subtasks.values());
        String historyJson = gson.fromJson(kvTaskClient.loadFromKVServer("history"), String.class);
        if (historyJson != null) {
            loadHistory(historyJson, httpTaskManager);
        }
    }
}
