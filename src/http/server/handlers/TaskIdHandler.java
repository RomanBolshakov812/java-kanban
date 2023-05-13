package http.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import http.server.HttpTaskManager;
import models.Task;

import java.io.IOException;
import java.io.InputStream;

public class TaskIdHandler extends Handler{

    public TaskIdHandler(HttpTaskManager httpTaskManager) {
        super(httpTaskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        switch (exchange.getRequestMethod()) {
            case "GET":
                writeResponse(exchange, gson.toJson(getTaskById(exchange)), 200);
                break;
            case "POST":
                InputStream inputStream = exchange.getRequestBody();
                String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                Task task = gson.fromJson(body, Task.class);
                httpTaskManager.updateTask(task);
                writeResponse(exchange, "Задача с " + exchange.getRequestURI().getQuery()
                        + " обновлена!", 200);
                break;
            case "DELETE":
                httpTaskManager.deleteTask(getIdFromUri(exchange));
                writeResponse(exchange, "Задача с " + exchange.getRequestURI().getQuery()
                        + " удалена!", 200);
                break;
            default:
                writeResponse(exchange,"Такого метода не существует!", 400);
        }
    }
}
