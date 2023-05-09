import file.FileBackedTasksManager;
import models.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static file.FileBackedTasksManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @BeforeEach
    public void BeforeEach() throws IOException {
        Files.createFile(Path.of("resources/taskManagerTest.csv"));
        File file = new File("resources/taskManagerTest.csv");
        taskManager = new FileBackedTasksManager(file);
        initTasks();

    }

    @AfterEach
    public void AfterEach() throws IOException {
        Path path = Path.of("resources/taskManagerTest.csv");
        Files.delete(path);
    }

    @Test
    public void shouldBeLoadFromEmptyFile() {
        FileBackedTasksManager taskManager2 = loadFromFile(new File("resources/taskManagerTest.csv"));
        assertEquals(taskManager.getListTasks(), taskManager2.getListTasks(), "Задачи не совпадают");
        assertEquals(taskManager.getListEpics(), taskManager2.getListEpics(), "Эпики не совпадают");
        assertEquals(taskManager.getListSubtasks(), taskManager2.getListSubtasks(), "Подзадачи не совпадают");
        assertEquals(taskManager.getHistory(), taskManager2.getHistory(), "Истории не совпадают");
    }

    @Test
    public void shouldBeLoadFromNotEmptyFile() {
        taskManager.createEpic(epic);
        taskManager.createTask(task1);
        taskManager.createSubtask(subtask1);
        taskManager.getTask(2);
        taskManager.getSubtask(3);
        taskManager.getEpic(1);
        FileBackedTasksManager taskManager2 = loadFromFile(new File("resources/taskManagerTest.csv"));
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
        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(new File("resources/taskManagerTest.csv"));
        assertTrue(fileBackedTasksManager2.getHistory().isEmpty(), "История не пустая");
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
        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(new File("resources/taskManagerTest.csv"));
        List<Task> loadHistory = fileBackedTasksManager2.getHistory();
        assertFalse(fileBackedTasksManager2.getHistory().isEmpty(), "История пустая");
        assertEquals(history.toString(), loadHistory.toString(), "Истории не совпадают");
    }
}
