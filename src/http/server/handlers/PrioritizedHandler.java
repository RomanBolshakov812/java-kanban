package http.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import http.server.HttpTaskManager;

import java.io.IOException;

public class PrioritizedHandler extends Handler {

    public PrioritizedHandler(HttpTaskManager httpTaskManager) {
        super(httpTaskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        writeResponse(exchange, gson.toJson(httpTaskManager.getPrioritizedTasks()), 200);
    }
}
