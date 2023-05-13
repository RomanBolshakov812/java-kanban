package http.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import http.server.HttpTaskManager;
import models.Epic;

import java.io.IOException;
import java.io.InputStream;

public class EpicIdHandler extends Handler {

    public EpicIdHandler(HttpTaskManager httpTaskManager) {
        super(httpTaskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        switch (exchange.getRequestMethod()) {
            case "GET":
                writeResponse(exchange, gson.toJson(getEpicById(exchange)), 200);
                break;
            case "POST":
                InputStream inputStream = exchange.getRequestBody();
                String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                Epic epic = gson.fromJson(body, Epic.class);
                httpTaskManager.updateEpic(epic);
                writeResponse(exchange, "Эпик с " + exchange.getRequestURI().getQuery()
                        + " обновлен!", 200);
                break;
            case "DELETE":
                httpTaskManager.deleteEpic(getIdFromUri(exchange));
                writeResponse(exchange, "Эпик с " + exchange.getRequestURI().getQuery()
                        + " удалён!", 200);
                break;
            default:
                writeResponse(exchange,"Такого метода не существует!", 400);
        }
    }
}
