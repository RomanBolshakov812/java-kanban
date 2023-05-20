package http.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import http.server.HttpTaskManager;
import models.Task;

import java.io.IOException;
import java.io.InputStream;

public class TasksHandler extends Handler {

    Gson gson = new Gson();

    public TasksHandler(HttpTaskManager httpTaskManager) {
        super(httpTaskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        switch (exchange.getRequestMethod()) {
            case "GET":
                if (!isId(exchange)) {
                    writeResponse(exchange, gson.toJson(httpTaskManager.getListTasks()), 200);
                } else {
                    writeResponse(exchange, gson.toJson(getTaskById(exchange)), 200);
                }
                break;
            case "POST":
                InputStream inputStream = exchange.getRequestBody();
                String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                Task task = gson.fromJson(body, Task.class);
                if (!isId(exchange)) {
                    httpTaskManager.createTask(task);
                    writeResponse(exchange, "Задача создана!", 200);
                } else {
                    httpTaskManager.updateTask(task);
                    writeResponse(exchange, "Задача с " + exchange.getRequestURI().getQuery()
                            + " обновлена!", 200);
                }
                break;
            case "DELETE":
                if (!isId(exchange)) {
                    httpTaskManager.deleteAllTasks();
                    writeResponse(exchange, "Все задачи удалены!", 200);
                } else {
                    httpTaskManager.deleteTask(getIdFromUri(exchange));
                    writeResponse(exchange, "Задача с " + exchange.getRequestURI().getQuery()
                            + " удалена!", 200);
                }
                break;
            default:
                writeResponse(exchange,"Такого метода не существует!", 400);
        }
    }
}
