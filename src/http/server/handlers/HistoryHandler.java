package http.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import http.server.HttpTaskManager;

import java.io.IOException;

public class HistoryHandler extends Handler {

    public HistoryHandler(HttpTaskManager httpTaskManager) {
        super(httpTaskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        writeResponse(exchange, gson.toJson(httpTaskManager.getHistory()), 200);
    }
}
