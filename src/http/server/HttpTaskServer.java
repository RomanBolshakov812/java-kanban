package http.server;

import com.sun.net.httpserver.HttpServer;
import exceptions.HttpException;
import http.server.handlers.*;
import manager.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer extends Managers {

    HttpServer httpServer;
    private static final int PORT = 8080;

    public void start(HttpTaskManager httpTaskManager) {
        try {
            httpServer = HttpServer.create();
        } catch (IOException exception) {
            throw new HttpException("Ошибка при запуске сервера!");
        }
        try {
            httpServer.bind(new InetSocketAddress(PORT), 0);
        } catch (IOException exception) {
            throw new HttpException("Ошибка при привязке к порту " + PORT + "!");
        }
        httpServer.createContext("/tasks/task", new TasksHandler(httpTaskManager));
        httpServer.createContext("/tasks/epic", new EpicsHandler(httpTaskManager));
        httpServer.createContext("/tasks/subtask", new SubtasksHandler(httpTaskManager));
        httpServer.createContext("/tasks", new PrioritizedHandler(httpTaskManager));
        httpServer.createContext("/tasks/history", new HistoryHandler(httpTaskManager));
        httpServer.createContext("/tasks/subtask/epic", new SubtaskOfEpicHandler(httpTaskManager));
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }
    public void stop() {
        httpServer.stop(0);
    }
}
