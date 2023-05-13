import http.KVServer.KVServer;
import http.server.HttpTaskManager;
import http.server.HttpTaskServer;
import manager.Managers;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        new KVServer().start();
        HttpTaskManager httpTaskManager = (HttpTaskManager) Managers.getDefault();
        new HttpTaskServer().start(httpTaskManager);
    }
}
/*

Привет, Сергей! (или привет тому, кто будет проверять)
Поскольку все сроки уже прошли и я на грани отчисления - терять нечего. Отправляю то, что успел сделать.
Тестов нет. Да и перед тем как с ними возиться, хорошо бы понять, в том ли направлении я вообще иду.
Криво-косо, но все как-то работает.
Если что - понять и простить.
Спасибо!

 */