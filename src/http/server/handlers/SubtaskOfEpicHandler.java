package http.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import http.server.HttpTaskManager;

import java.io.IOException;

public class SubtaskOfEpicHandler extends Handler {

    public SubtaskOfEpicHandler(HttpTaskManager httpTaskManager) {
        super(httpTaskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        writeResponse(exchange, gson.toJson(httpTaskManager.getListSubtasksOfEpic(getIdFromUri(exchange))), 200);
    }
}
