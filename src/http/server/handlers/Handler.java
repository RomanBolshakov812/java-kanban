package http.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http.server.HttpTaskManager;
import models.Epic;
import models.Subtask;
import models.Task;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public abstract class Handler implements HttpHandler {

    HttpTaskManager httpTaskManager;
    Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public Handler(HttpTaskManager httpTaskManager) {
        this.httpTaskManager = httpTaskManager;
    }

    protected boolean isId(HttpExchange exchange) {
        return exchange.getRequestURI().getQuery() != null;
    }

    Task getTaskById(HttpExchange exchange) {
        return httpTaskManager.getTask(getIdFromUri(exchange));
    }

    Epic getEpicById(HttpExchange exchange) {
        return httpTaskManager.getEpic(getIdFromUri(exchange));
    }

    Subtask getSubtaskById(HttpExchange exchange) {
        return httpTaskManager.getSubtask(getIdFromUri(exchange));
    }

    int getIdFromUri(HttpExchange exchange) {
        String[] parts = exchange.getRequestURI().getQuery().split("=");
        return Integer.parseInt(parts[1]);
    }

    void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws IOException {
        if(responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }
}


