package http.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import http.server.HttpTaskManager;
import models.Epic;
import models.Task;

import java.io.IOException;
import java.io.InputStream;

public class EpicsHandler extends Handler {

    public EpicsHandler(HttpTaskManager httpTaskManager) {
        super(httpTaskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        switch (exchange.getRequestMethod()) {
            case "GET":
                if (!isId(exchange)) {
                    writeResponse(exchange, gson.toJson(httpTaskManager.getListEpics()), 200);
                } else {
                    writeResponse(exchange, gson.toJson(getEpicById(exchange)), 200);
                }
                break;
            case "POST":
                InputStream inputStream = exchange.getRequestBody();
                String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                Epic epic = gson.fromJson(body, Epic.class);
                if (!isId(exchange)) {
                    httpTaskManager.createEpic(epic);
                    writeResponse(exchange, "Эпик создан!", 200);
                } else {
                    httpTaskManager.updateEpic(epic);
                    writeResponse(exchange, "Эпик с " + exchange.getRequestURI().getQuery()
                            + " обновлён!", 200);
                }
                break;
            case "DELETE":
                if (!isId(exchange)) {
                    httpTaskManager.deleteAllEpics();
                    writeResponse(exchange, "Все эпики удалены!", 200);
                } else {
                    httpTaskManager.deleteEpic(getIdFromUri(exchange));
                    writeResponse(exchange, "Эпик с " + exchange.getRequestURI().getQuery()
                            + " удалён!", 200);
                }
                break;
            default:
                writeResponse(exchange,"Такого метода не существует!", 400);
        }
    }
}
