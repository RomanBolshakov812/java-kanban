import manager.InMemoryTaskManager;
import manager.TaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TaskManagerTest {

    InMemoryTaskManager inMemoryTaskManager;
    Task task1;
    Task task2;

    @BeforeEach
    public void BeforeEach() {
        inMemoryTaskManager = new InMemoryTaskManager();
        task1 = new Task(1,"Задача 1", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 1,"Задача 1.");
        task2 = new Task(2,"Задача 2", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,2), 1,"Задача 2.");
    }

    @Test
    public void shouldCreateTaskInEmptyList() {// Создание ЗАДАЧИ в пустой список
        assertEquals(0, inMemoryTaskManager.getListTasks().size(), "Размер списка задач не нулевой");
        inMemoryTaskManager.createTask(task1);
        Task createdTask = inMemoryTaskManager.getTask(1);
        assertNotNull(createdTask, "Задача не найдена");
        assertEquals(task1, createdTask, "Задачи не совпадают");
        List<Task> savedTasks = inMemoryTaskManager.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        assertEquals(task1, savedTasks.get(0), "Задачи не совпадают");
    }

    @Test
    public void shouldCreateTaskInNonEmptyList() {// Создание ЗАДАЧИ в непустой список
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        List<Task> savedTasks = inMemoryTaskManager.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(2, savedTasks.size(), "Неверное количество задач");
        assertEquals(task2, savedTasks.get(1), "Задачи не совпадают");
        Task createdTask2 = inMemoryTaskManager.getTask(2);
        assertNotNull(createdTask2, "Задача не найдена");
        assertEquals(task2, createdTask2, "Задачи не совпадают");
    }

    @Test
    public void shouldUpdateTask() {// Обновление ЗАДАЧИ
        inMemoryTaskManager.createTask(task1);
        Task taskUpdated = new Task(1,"Задача 1 обновленная", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 1,"Задача 1 обновленная.");
        inMemoryTaskManager.updateTask(taskUpdated);
        List<Task> savedTasks = inMemoryTaskManager.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        Task savedTask = savedTasks.get(0);
        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(taskUpdated, savedTask, "Задачи не совпадают");
    }

    @Test
    public void shouldNotUpdateTask() {// Обновление ЗАДАЧИ в пустой список
        inMemoryTaskManager.updateTask(task1);
        List<Task> savedTasks = inMemoryTaskManager.getListTasks();
        assertEquals(0, savedTasks.size(), "Список задач не нулевой");
    }

    @Test
    public void shouldTrowsNullPointerException() {// Обновление ЗАДАЧИ c неверным id
        inMemoryTaskManager.createTask(task1);
        Task taskUpdated = new Task(2,"Задача с несуществующим id", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 1,"Задача с несуществующим id");
        assertThrows(NullPointerException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        inMemoryTaskManager.updateTask(taskUpdated);
                    }
                });
    }

    @Test
    public void shouldReturnTask() {// Вызов ЗАДАЧИ
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        List<Task> savedTasks = inMemoryTaskManager.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(2, savedTasks.size(), "Неверное количество задач");
        Task returnedTask = savedTasks.get(1);
        assertEquals(task2, returnedTask, "Задачи не совпадают");
    }

    @Test
    public void shouldTrowsIndexOutOfBoundsExceptionBecauseEmptyList() {// Вызов ЗАДАЧИ из пустого списка
        List<Task> savedTasks = inMemoryTaskManager.getListTasks();
        assertEquals(0, savedTasks.size(), "Неверное количество задач");
        assertThrows(IndexOutOfBoundsException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        savedTasks.get(0);
                    }
                });
    }

    @Test
    public void shouldTrowsIndexOutOfBoundsExceptionBecauseIncorrectId() {// Вызов ЗАДАЧИ c неверным id
        inMemoryTaskManager.createTask(task1);
        List<Task> savedTasks = inMemoryTaskManager.getListTasks();
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        assertThrows(IndexOutOfBoundsException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        savedTasks.get(1);
                    }
                });
    }

    @Test
    public void shouldReturnListTask() {// Вызов списка ЗАДАЧ
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        List<Task> savedTasks = inMemoryTaskManager.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(2, savedTasks.size(), "Неверное количество задач");
        Task returnedTask1 = savedTasks.get(0);
        Task returnedTask2 = savedTasks.get(1);
        assertEquals(task1, returnedTask1, "Задачи не совпадают");
        assertEquals(task2, returnedTask2, "Задачи не совпадают");
    }

    @Test
    public void shouldReturnEmptyListTask() {// Вызов пустого списка ЗАДАЧ
        List<Task> savedTasks = inMemoryTaskManager.getListTasks();
        assertNotNull(savedTasks, "Список есть null");
        assertEquals(0, savedTasks.size(), "Неверное количество задач");
    }

    @Test
    public void shouldDeleteTask() {// Удаление ЗАДАЧИ
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.deleteTask(1);
        List<Task> savedTasks = inMemoryTaskManager.getListTasks();
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        assertEquals(task2, savedTasks.get(0),"Задачи не совпадают");
    }

    @Test
    public void shouldTrowsIndexOutOfBoundsExceptionBecauseIncorrectId1() {// Удаление ЗАДАЧИ из пустого списка
        assertThrows(IndexOutOfBoundsException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        inMemoryTaskManager.deleteTask(1);
                    }
                });
    }





}
/*
    void createTask(Task task);/////////////////////////////////
    void createEpic(Epic epic);
    void createSubtask(Subtask subtask);

    void updateTask(Task task);/////////////////////////////////
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);

    Task getTask(int id);///////////////////////////////////////
    Epic getEpic(int id);
    Subtask getSubtask(int id);

    ArrayList<Task> getListTasks();/////////////////////////////
    ArrayList<Epic> getListEpics();
    ArrayList<Subtask> getListSubtasks();
    List<Subtask> getListSubtasksOfEpic(int epicId);

    void deleteTask(int id);
    void deleteEpic(int id);
    void deleteSubtask(int id);

    List<Task> getHistory();
    public Set<Task> getPrioritizedTasks();
    boolean isTimeIntervalCheck(Task task);

    void changeEpicStatus(Epic epic);
    void setEpicStartTime(Epic epic);
    void setEpicDuration(Epic epic);

    void deleteAllTasks();
    void deleteAllEpics();
    void deleteAllSubtasks();

 */







