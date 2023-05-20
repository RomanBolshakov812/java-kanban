package http.KVServer;

import exceptions.FileException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private static final HttpClient kvTaskClient = HttpClient.newHttpClient();
    private final String API_TOKEN;

    public KVTaskClient(URI url) {
        API_TOKEN = registration(url);
    }

    public static String registration(URI url) {
        URI urlRegister = URI.create(url + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(urlRegister)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
        return kvTaskClient.send(request, handler).body();
        } catch (IOException | InterruptedException e) {
            throw new FileException("Регистрация не осуществлена!", e);
        }        
    }

    public void putInKVServer(String key, String json) {
        URI url = URI.create("http://localhost:8078/save/" + key + "?API_TOKEN=" + API_TOKEN);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        System.out.println(url);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(body)
                .uri(url)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            kvTaskClient.send(request, handler);
        } catch (IOException | InterruptedException e) {
            throw new FileException("Данные на сервер не загружены!", e);
        }
    }

    public String loadFromKVServer(String key) {

        URI url = URI.create("http://localhost:8078/load/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = kvTaskClient.send(request, handler);
            return String.valueOf(response);
        } catch (IOException | InterruptedException e) {
            throw new FileException("Данные с сервера не загружены!", e);
        }
    }
}
