package http.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import http.server.HttpTaskManager;
import models.Subtask;
import models.Task;

import java.io.IOException;
import java.io.InputStream;

public class SubtasksHandler extends Handler {

    public SubtasksHandler(HttpTaskManager httpTaskManager) {
        super(httpTaskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        switch (exchange.getRequestMethod()) {
            case "GET":
                if (!isId(exchange)) {
                    writeResponse(exchange, gson.toJson(httpTaskManager.getListSubtasks()), 200);
                } else {
                    writeResponse(exchange, gson.toJson(getSubtaskById(exchange)), 200);
                }
                break;
            case "POST":
                InputStream inputStream = exchange.getRequestBody();
                String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                Subtask subtask = gson.fromJson(body, Subtask.class);
                if (!isId(exchange)) {
                    httpTaskManager.createSubtask(subtask);
                    writeResponse(exchange, "Подзадача создана!", 200);
                } else {
                    httpTaskManager.updateSubtask(subtask);
                    writeResponse(exchange, "Подзадача с " + exchange.getRequestURI().getQuery()
                            + " обновлена!", 200);
                }
                break;
            case "DELETE":
                if (!isId(exchange)) {
                    httpTaskManager.deleteAllSubtasks();
                    writeResponse(exchange, "Все подзадачи удалены!", 200);
                } else {
                    httpTaskManager.deleteSubtask(getIdFromUri(exchange));
                    writeResponse(exchange, "Подзадача с " + exchange.getRequestURI().getQuery()
                            + " удалена!", 200);
                }
                break;
            default:
                writeResponse(exchange,"Такого метода не существует!", 400);
        }
    }
}
