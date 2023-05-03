import manager.InMemoryTaskManager;
import manager.TaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public abstract class TaskManagerTest<T extends TaskManager> {

    TaskManager T;
    Task task1;
    Task task2;
    Task task3;
    Epic epic;
    Subtask subtask1;
    Subtask subtask2;


    @BeforeEach
    public void BeforeEach() throws IOException {

        T = new InMemoryTaskManager();
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

    @Test
    public void shouldCreateTaskInEmptyList() {
        assertEquals(0, T.getListTasks().size(), "Размер списка задач не нулевой");
        T.createTask(task1);
        Task createdTask = T.getTask(1);
        assertNotNull(createdTask, "Задача не найдена");
        assertEquals(task1, createdTask, "Задачи не совпадают");
        List<Task> savedTasks = T.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        assertEquals(task1, savedTasks.get(0), "Задачи не совпадают");
    }

    @Test
    public void shouldCreateTaskInNonEmptyList() {
        T.createTask(task1);
        T.createTask(task2);
        List<Task> savedTasks = T.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(2, savedTasks.size(), "Неверное количество задач");
        assertEquals(task2, savedTasks.get(1), "Задачи не совпадают");
        Task createdTask2 = T.getTask(2);
        assertNotNull(createdTask2, "Задача не найдена");
        assertEquals(task2, createdTask2, "Задачи не совпадают");
    }

    @Test
    public void shouldUpdateTask() {
        T.createTask(task1);
        Task taskUpdated = new Task(1,"Задача 1 обновленная", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 1,"Задача 1 обновленная.");
        T.updateTask(taskUpdated);
        List<Task> savedTasks = T.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        Task savedTask = savedTasks.get(0);
        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(taskUpdated, savedTask, "Задачи не совпадают");
    }

    @Test
    public void shouldNotUpdateTask() {
        T.updateTask(task1);
        List<Task> savedTasks = T.getListTasks();
        assertEquals(0, savedTasks.size(), "Список задач не нулевой");
    }

    @Test
    public void shouldNotUpdate() {
        T.createTask(task1);
        Task taskUpdated = new Task(2,"Задача с несуществующим id", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 1,"Задача с несуществующим id");
        T.updateTask(taskUpdated);
        Task postUpdateTask = T.getTask(2);
        assertNull(postUpdateTask);
    }

    @Test
    public void shouldReturnTask() {
        T.createTask(task1);
        T.createTask(task2);
        List<Task> savedTasks = T.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(2, savedTasks.size(), "Неверное количество задач");
        Task returnedTask = savedTasks.get(1);
        assertEquals(task2, returnedTask, "Задачи не совпадают");
    }

    @Test
    public void shouldTrowsIndexOutOfBoundsExceptionBecauseEmptyList() {
        List<Task> savedTasks = T.getListTasks();
        assertEquals(0, savedTasks.size(), "Неверное количество задач");
        assertThrows(IndexOutOfBoundsException.class,
                () -> savedTasks.get(0));
    }

    @Test
    public void shouldTrowsIndexOutOfBoundsExceptionBecauseIncorrectId() {
        T.createTask(task1);
        List<Task> savedTasks = T.getListTasks();
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        assertThrows(IndexOutOfBoundsException.class,
                () -> savedTasks.get(1));
    }

    @Test
    public void shouldReturnListTask() {
        T.createTask(task1);
        T.createTask(task2);
        List<Task> savedTasks = T.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(2, savedTasks.size(), "Неверное количество задач");
        Task returnedTask1 = savedTasks.get(0);
        Task returnedTask2 = savedTasks.get(1);
        assertEquals(task1, returnedTask1, "Задачи не совпадают");
        assertEquals(task2, returnedTask2, "Задачи не совпадают");
    }

    @Test
    public void shouldReturnEmptyListTask() {
        List<Task> savedTasks = T.getListTasks();
        assertNotNull(savedTasks, "Список есть null");
        assertEquals(0, savedTasks.size(), "Неверное количество задач");
    }

    @Test
    public void shouldDeleteTask() {
        T.createTask(task1);
        T.createTask(task2);
        T.deleteTask(1);
        List<Task> savedTasks = T.getListTasks();
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        assertEquals(task2, savedTasks.get(0),"Задачи не совпадают");
    }

    @Test
    public void shouldDeleteAllTask() {
        T.createTask(task1);
        T.createTask(task2);
        T.deleteAllTasks();
        List<Task> tasks = T.getListTasks();
        assertEquals(0, tasks.size(), "Неверное количество задач");
    }

    @Test
    public void shouldDeleteSingleTask() {
        T.createTask(task1);
        T.deleteAllTasks();
        List<Task> tasks = T.getListTasks();
        assertEquals(0, tasks.size(), "Неверное количество задач");
    }

    @Test
    public void shouldBeNewBecauseNoSubtasks() {
        T.createEpic(epic);
        T.deleteAllSubtasks();
        Enum<Status> status = T.getEpic(1).getStatus();
        assertEquals(Status.NEW, status, "Статус эпика без подзадач не NEW");
    }

    @Test
    public void shouldBeNewBecauseAllSubtasksNew() {
        T.createEpic(epic);
        T.createSubtask(subtask1);
        T.createSubtask(subtask2);
        Enum<Status> status = T.getEpic(1).getStatus();
        assertEquals(Status.NEW, status, "Статус эпика не NEW");
    }

    @Test
    public void shouldBeDoneBecauseAllSubtasksDone() {
        T.createEpic(epic);
        T.createSubtask(subtask1);
        T.createSubtask(subtask2);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        T.updateSubtask(subtask1);
        T.updateSubtask(subtask2);
        Enum<Status> status = T.getEpic(1).getStatus();
        assertEquals(Status.DONE, status, "Статус эпика не DONE");
    }

    @Test
    public void shouldBeInProgressBecauseAllSubtasksNewAndDone() {
        T.createEpic(epic);
        T.createSubtask(subtask1);
        T.createSubtask(subtask2);
        subtask1.setStatus(Status.DONE);
        T.updateSubtask(subtask1);
        Enum<Status> status = T.getEpic(1).getStatus();
        assertEquals(Status.IN_PROGRESS, status, "Статус эпика не DONE");
    }

    @Test
    public void shouldBeInProgressBecauseAllSubtasksInProgress() {
        T.createEpic(epic);
        T.createSubtask(subtask1);
        T.createSubtask(subtask2);
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        T.updateSubtask(subtask1);
        T.updateSubtask(subtask2);
        Enum<Status> status = T.getEpic(1).getStatus();
        assertEquals(Status.IN_PROGRESS, status, "Статус эпика не DONE");
    }

    @Test
    public void shouldBeSingleSubtaskStartTime() {
        T.createEpic(epic);
        T.createSubtask(subtask1);
        LocalDateTime epicStartTime = epic.getStartTime();
        assertEquals(LocalDateTime
                        .of(2023, Month.APRIL, 22, 22, 2),
                epicStartTime, "Время не соответствует");
    }

    @Test
    public void shouldBeSingleSubtaskEndTime() {
        T.createEpic(epic);
        T.createSubtask(subtask1);
        LocalDateTime epicEndTime = epic.getEndTime();
        assertEquals(LocalDateTime
                        .of(2023, Month.APRIL, 22, 22, 3),
                epicEndTime, "Время не соответствует");
    }

    @Test
    public void shouldBeFirstSubtaskStartTime() {
        T.createEpic(epic);
        T.createSubtask(subtask1);
        T.createSubtask(subtask2);
        LocalDateTime epicStartTime = epic.getStartTime();
        assertEquals(LocalDateTime
                .of(2023, Month.APRIL, 22, 22, 0),
                epicStartTime, "Время не соответствует");
    }

    @Test
    public void shouldBeLastSubtaskEndTime() {
        T.createEpic(epic);
        T.createSubtask(subtask1);
        T.createSubtask(subtask2);
        LocalDateTime epicEndTime = epic.getEndTime();
        assertEquals(LocalDateTime
                        .of(2023, Month.APRIL, 22, 22, 3),
                epicEndTime, "Время не соответствует");
    }

    @Test
    public void shouldBeDuration1() {
        T.createEpic(epic);
        T.createSubtask(subtask1);
        long epicDuration = epic.getDuration();
        assertEquals(1, epicDuration, "Неверная длительность");
    }

    @Test
    public void shouldBeDuration2() {
        T.createEpic(epic);
        T.createSubtask(subtask1);
        T.createSubtask(subtask2);
        long epicDuration = epic.getDuration();
        assertEquals(2, epicDuration, "Неверная длительность");
    }

    @Test
    public void shouldReturnPrioritizedTask() {
        T.createEpic(epic);
        T.createSubtask(subtask1);
        T.createSubtask(subtask2);
        T.createTask(task1);
        T.createTask(task2);
        T.createTask(task3);
        ArrayList<Task> prioritizedTask = new ArrayList<>(T.getPrioritizedTasks());
        assertEquals(subtask2, prioritizedTask.get(0), "Задачи не совпадают");
        assertEquals(task1, prioritizedTask.get(1), "Задачи не совпадают");
        assertEquals(subtask1, prioritizedTask.get(2), "Задачи не совпадают");
        assertEquals(task2, prioritizedTask.get(3), "Задачи не совпадают");
        assertEquals(task3, prioritizedTask.get(4), "Задачи не совпадают");
    }

    @Test
    public void shouldNotOverlapAndNotCreateTask3() {
        task3 = new Task(3,"Задача 3", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 2,"Задача 3.");
        T.createTask(task1);
        T.createTask(task2);
        T.createTask(task3);
        assertNull(T.getTask(task3.getId()), "Задача 3 создана и есть в списке");
    }
}
