import exceptions.HttpException;
import http.KVServer.KVServer;
import http.server.HttpTaskManager;
import http.server.HttpTaskServer;
import manager.Managers;
import models.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {

    KVServer kvServer;
    HttpTaskServer httpTaskServer;

    @BeforeEach
    public void BeforeEach() {
        try {
            kvServer = new KVServer();
        } catch (IOException exception) {
            throw new HttpException("Ошибка при создании KVServer!");
        }
        kvServer.start();
        taskManager = (HttpTaskManager) Managers.getDefault();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start(taskManager);
        initTasks();
    }

    @AfterEach
    public void AfterEach() {
        kvServer.stop();
        httpTaskServer.stop();
    }

    private HttpTaskManager startNewHttpTaskManager()  {
        HttpTaskManager taskManager2 = (HttpTaskManager) Managers.getDefault();
        taskManager2.load(taskManager2);
        httpTaskServer.stop();
        httpTaskServer.start(taskManager2);
        return taskManager2;
    }

    @Test
    public void shouldBeLoadFromEmptyKVServer() {
        HttpTaskManager taskManager2 = startNewHttpTaskManager();
        assertEquals(taskManager.getListTasks(), taskManager2.getListTasks(), "Задачи не совпадают");
        assertEquals(taskManager.getListEpics(), taskManager2.getListEpics(), "Эпики не совпадают");
        assertEquals(taskManager.getListSubtasks(), taskManager2.getListSubtasks(), "Подзадачи не совпадают");
        assertEquals(taskManager.getHistory(), taskManager2.getHistory(), "Истории не совпадают");
    }

    @Test
    public void shouldBeLoadFromNotEmptyKVServer() {
        taskManager.createEpic(epic);
        taskManager.createTask(task1);
        taskManager.createSubtask(subtask1);
        taskManager.getTask(2);
        taskManager.getSubtask(3);
        taskManager.getEpic(1);
        HttpTaskManager taskManager2 = startNewHttpTaskManager();
        assertEquals(taskManager.getListTasks(), taskManager2.getListTasks(), "Задачи не совпадают");
        assertEquals(taskManager.getListEpics(), taskManager2.getListEpics(), "Эпики не совпадают");
        assertEquals(taskManager.getListSubtasks(), taskManager2.getListSubtasks(), "Подзадачи не совпадают");
        assertEquals(taskManager.getHistory(), taskManager2.getHistory(), "Истории не совпадают");
    }

    @Test
    public void shouldBeEmptyHistory() {
        taskManager.createEpic(epic);
        taskManager.createTask(task1);
        taskManager.createSubtask(subtask1);
        HttpTaskManager taskManager2 = startNewHttpTaskManager();
        assertTrue(taskManager2.getHistory().isEmpty(), "История не пустая");
    }

    @Test
    public void shouldBeNotEmptyHistory() {
        taskManager.createEpic(epic);
        taskManager.createTask(task1);
        taskManager.createSubtask(subtask1);
        taskManager.getEpic(1);
        taskManager.getTask(2);
        taskManager.getSubtask(3);
        List<Task> history = taskManager.getHistory();
        HttpTaskManager taskManager2 = startNewHttpTaskManager();
        List<Task> loadHistory = taskManager2.getHistory();
        assertFalse(taskManager2.getHistory().isEmpty(), "История пустая");
        assertEquals(history.toString(), loadHistory.toString(), "Истории не совпадают");
    }
}
