import manager.InMemoryHistoryManager;
import models.Status;
import models.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {

    private static InMemoryHistoryManager inMemoryHistoryManager;
    private static Task task1;
    private static Task task1Double;
    private static Task task2;
    private static Task task3;

    @BeforeEach
    public  void beforeEach() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        task1 = new Task(1,"Задача 1", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 2,"Задача 1.");
        task1Double = new Task(1,"Задача 1 Дубликат", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 2,"Задача 1 Дубликат.");
        task2 = new Task(2,"Задача 2", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 2,"Задача 2.");
        task3 = new Task(3,"Задача 3", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 2,"Задача 3.");
        inMemoryHistoryManager.addHistory(task1);
    }

    @Test
    public void shouldReturnEmptyList() {
        inMemoryHistoryManager.remove(task1.getId());
        assertEquals(0, inMemoryHistoryManager.getHistory().size(), "Размер списка истории не нулевой");
    }

    @Test
    public void shouldAddToEmptyHistory() {
        assertEquals(1, inMemoryHistoryManager.getHistory().size(), "Размер списка истории не равен 1");
        assertEquals(task1, inMemoryHistoryManager.getHistory().get(0), "Задачи не совпадают");
    }

    @Test
    public void shouldReplaceDuplicatesInHistory() {
        inMemoryHistoryManager.addHistory(task1Double);
        assertEquals(1, inMemoryHistoryManager.getHistory().size(), "Размер списка истории не равен 1");
        assertEquals(task1Double, inMemoryHistoryManager.getHistory().get(0), "Задачи не совпадают");
    }

    @Test
    public void shouldAddToEndOfNonemptyHistory() {
        inMemoryHistoryManager.addHistory(task2);
        assertEquals(2, inMemoryHistoryManager.getHistory().size(), "Размер списка истории не равен 2");
        assertEquals(task2, inMemoryHistoryManager.getHistory().get(1), "Задачи не совпадают");
    }

    @Test
    public void shouldDeleteFromMiddleOfHistory() {
        inMemoryHistoryManager.addHistory(task2);
        inMemoryHistoryManager.addHistory(task3);
        inMemoryHistoryManager.remove(2);
        assertEquals(2, inMemoryHistoryManager.getHistory().size(), "Размер списка истории не равен 2");
        assertEquals(task3, inMemoryHistoryManager.getHistory().get(1), "Задачи не совпадают");
    }

    @Test
    public void shouldDeleteFromBeginningOfHistory() {
        inMemoryHistoryManager.addHistory(task2);
        inMemoryHistoryManager.addHistory(task3);
        inMemoryHistoryManager.remove(1);
        assertEquals(2, inMemoryHistoryManager.getHistory().size(), "Размер списка истории не равен 2");
        assertEquals(task2, inMemoryHistoryManager.getHistory().get(0), "Задачи не совпадают");
    }

    @Test
    public void shouldDeleteFromEndOfHistory() {
        inMemoryHistoryManager.addHistory(task2);
        inMemoryHistoryManager.addHistory(task3);
        inMemoryHistoryManager.remove(3);
        assertEquals(2, inMemoryHistoryManager.getHistory().size(), "Размер списка истории не равен 2");
        assertEquals(task2, inMemoryHistoryManager.getHistory().get(1), "Задачи не совпадают");
    }
}
