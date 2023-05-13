package http.server;

import com.sun.net.httpserver.HttpServer;
import http.server.handlers.*;
import manager.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer extends Managers {

    private static final int PORT = 8080;

    public void start(HttpTaskManager httpTaskManager) throws IOException {
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);

        httpServer.createContext("/tasks/task", new TasksHandler(httpTaskManager));
        httpServer.createContext("/epics/epic", new EpicsHandler(httpTaskManager));
        httpServer.createContext("/subtasks/subtask", new SubtasksHandler(httpTaskManager));
        httpServer.createContext("/tasks/taskId", new TaskIdHandler(httpTaskManager));
        httpServer.createContext("/epics/epicId", new EpicIdHandler(httpTaskManager));
        httpServer.createContext("/subtasks/subtaskId", new SubtaskIdHandler(httpTaskManager));
        httpServer.createContext("/tasks", new PrioritizedHandler(httpTaskManager));
        httpServer.createContext("/tasks/history", new HistoryHandler(httpTaskManager));
        httpServer.createContext("/subtasks/epic", new SubtaskOfEpicHandler(httpTaskManager));

        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }
}
