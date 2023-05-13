import http.KVServer.KVServer;
import http.server.HttpTaskManager;
import http.server.HttpTaskServer;
import manager.Managers;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        new KVServer().start();
        HttpTaskManager httpTaskManager = (HttpTaskManager) Managers.getDefault();
        new HttpTaskServer().start(httpTaskManager);
    }
}
