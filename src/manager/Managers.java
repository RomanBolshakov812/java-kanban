package manager;

import http.server.HttpTaskManager;

import java.net.URI;

public class Managers {

    public static TaskManager getDefault() {
        return new HttpTaskManager(URI.create("http://localhost:8078"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
