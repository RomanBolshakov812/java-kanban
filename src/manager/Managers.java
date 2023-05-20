package manager;

import http.server.HttpTaskManager;

import java.io.IOException;
import java.net.URI;

public class Managers {

    public static TaskManager getDefault() throws IOException, InterruptedException {

        HttpTaskManager httpTaskManager = new HttpTaskManager(URI.create("http://localhost:8078"));
        //httpTaskManager.load(httpTaskManager);
        return httpTaskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
