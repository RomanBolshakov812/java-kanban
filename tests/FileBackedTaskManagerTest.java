import file.FileBackedTasksManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import static file.FileBackedTasksManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @BeforeEach
    public void BeforeEach() throws IOException {
        Files.createFile(Path.of("resources/taskManagerTest.csv"));
        File file = new File("resources/taskManagerTest.csv");

        T = new FileBackedTasksManager(file);
        task1 = new Task(1,"Задача 1", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 1,"Задача 1.");
        task2 = new Task(2,"Задача 2", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,3), 1,"Задача 2.");
        task3 = new Task(6,"Задача 3", Status.NEW, null, 1,"Задача 3.");
        epic = new Epic(3, "ЭПИК", Status.NEW,null,0,"Эпик NEW");
        subtask1 = new Subtask(4,"Подзадача 1 к ЭПИКУ", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,2),
                1,"Подзадача 1 Эпика.", 1);
        subtask2 = new Subtask(5,"Подзадача 2 к ЭПИКУ", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,0),
                1,"Подзадача 2 Эпика.", 1);
    }

    @AfterEach
    public void AfterEach() throws IOException {
        Path path = Path.of("resources/taskManagerTest.csv");
        Files.delete(path);
    }

    @Test
    public void shouldBeLoadFromEmptyFile() {
        assertTrue(T.getListTasks().isEmpty(), "Список задач не пустой");
        assertTrue(T.getListEpics().isEmpty(), "Список эпиков не пустой");
    }

    @Test
    public void shouldBeLoadFromNotEmptyFile() {
        T.createEpic(epic);
        T.createTask(task1);
        T.createSubtask(subtask1);
        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(new File("resources/taskManagerTest.csv"));
        assertEquals(task1.toString(), fileBackedTasksManager2.getTask(2).toString(), "Задачи не совпадают");
        assertEquals(epic.toString(), fileBackedTasksManager2.getEpic(1).toString(), "Эпики не совпадают");
        assertEquals(subtask1.toString(), fileBackedTasksManager2
                .getSubtask(3).toString(), "Подзадачи не совпадают");
    }

    @Test
    public void shouldBeEmptyHistory() {
        T.createEpic(epic);
        T.createTask(task1);
        T.createSubtask(subtask1);
        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(new File("resources/taskManagerTest.csv"));
        assertTrue(fileBackedTasksManager2.getHistory().isEmpty(), "История не пустая");
    }

    @Test
    public void shouldBeNotEmptyHistory() {
        T.createEpic(epic);
        T.createTask(task1);
        T.createSubtask(subtask1);
        T.getEpic(1);
        T.getTask(2);
        T.getSubtask(3);
        List<Task> history = T.getHistory();
        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(new File("resources/taskManagerTest.csv"));
        List<Task> loadHistory = fileBackedTasksManager2.getHistory();
        assertFalse(fileBackedTasksManager2.getHistory().isEmpty(), "История пустая");
        assertEquals(history.toString(), loadHistory.toString(), "Истории не совпадают");
    }
}
