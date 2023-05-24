import manager.TaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public abstract class TaskManagerTest<T extends TaskManager> {

    T taskManager;
    Task task1;
    Task task2;
    Task task3;
    Epic epic;
    Subtask subtask1;
    Subtask subtask2;

    public void initTasks() {

        task1 = new Task(1,"Задача 1", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,2), 1,"Задача 1.");
        task2 = new Task(2,"Задача 2", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,6), 1,"Задача 2.");
        task3 = new Task(6,"Задача 3", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,8), 1,"Задача 3.");
        epic = new Epic(3, "ЭПИК", Status.NEW,null,0,"Эпик NEW", null);
        subtask1 = new Subtask(4,"Подзадача 1 к ЭПИКУ", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,4),
                1,"Подзадача 1 Эпика.", 1);
        subtask2 = new Subtask(5,"Подзадача 2 к ЭПИКУ", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,0),
                1,"Подзадача 2 Эпика.", 1);
    }

    @Test
    public void shouldCreateTaskInEmptyList() {
        assertEquals(0, taskManager.getListTasks().size(), "Размер списка задач не нулевой");
        taskManager.createTask(task1);
        Task createdTask = taskManager.getTask(1);
        assertNotNull(createdTask, "Задача не найдена");
        assertEquals(task1, createdTask, "Задачи не совпадают");
        List<Task> savedTasks = taskManager.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        assertEquals(task1, savedTasks.get(0), "Задачи не совпадают");
    }

    @Test
    public void shouldCreateTaskInNonEmptyList() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        List<Task> savedTasks = taskManager.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(2, savedTasks.size(), "Неверное количество задач");
        assertEquals(task2, savedTasks.get(1), "Задачи не совпадают");
        Task createdTask2 = taskManager.getTask(2);
        assertNotNull(createdTask2, "Задача не найдена");
        assertEquals(task2, createdTask2, "Задачи не совпадают");
    }

    @Test
    public void shouldUpdateTask() {
        taskManager.createTask(task1);
        Task taskUpdated = new Task(1,"Задача 1 обновленная", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,2), 1,"Задача 1 обновленная.");
        taskManager.updateTask(taskUpdated);
        List<Task> savedTasks = taskManager.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        Task savedTask = savedTasks.get(0);
        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(taskUpdated, savedTask, "Задачи не совпадают");
    }

    @Test
    public void shouldNotUpdateTask() {
        taskManager.updateTask(task1);
        List<Task> savedTasks = taskManager.getListTasks();
        assertEquals(0, savedTasks.size(), "Список задач не нулевой");
    }

    @Test
    public void shouldNotUpdate() {
        taskManager.createTask(task1);
        Task taskUpdated = new Task(2,"Задача с несуществующим id", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 1,"Задача с несуществующим id");
        taskManager.updateTask(taskUpdated);
        Task postUpdateTask = taskManager.getTask(2);
        assertNull(postUpdateTask, "Задача с несуществующим id есть в списке задач");
    }

    @Test
    public void shouldReturnTask() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        List<Task> savedTasks = taskManager.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(2, savedTasks.size(), "Неверное количество задач");
        Task returnedTask = savedTasks.get(1);
        assertEquals(task2, returnedTask, "Задачи не совпадают");
    }

    @Test
    public void shouldTrowsIndexOutOfBoundsExceptionBecauseEmptyList() {
        List<Task> savedTasks = taskManager.getListTasks();
        assertEquals(0, savedTasks.size(), "Неверное количество задач");
        assertThrows(IndexOutOfBoundsException.class,
                () -> savedTasks.get(0));
    }

    @Test
    public void shouldTrowsIndexOutOfBoundsExceptionBecauseIncorrectId() {
        taskManager.createTask(task1);
        List<Task> savedTasks = taskManager.getListTasks();
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        assertThrows(IndexOutOfBoundsException.class,
                () -> savedTasks.get(1));
    }

    @Test
    public void shouldReturnListTask() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        List<Task> savedTasks = taskManager.getListTasks();
        assertNotNull(savedTasks, "Задачи не возвращаются");
        assertEquals(2, savedTasks.size(), "Неверное количество задач");
        Task returnedTask1 = savedTasks.get(0);
        Task returnedTask2 = savedTasks.get(1);
        assertEquals(task1, returnedTask1, "Задачи не совпадают");
        assertEquals(task2, returnedTask2, "Задачи не совпадают");
    }

    @Test
    public void shouldReturnEmptyListTask() {
        List<Task> savedTasks = taskManager.getListTasks();
        assertNotNull(savedTasks, "Список есть null");
        assertEquals(0, savedTasks.size(), "Неверное количество задач");
    }

    @Test
    public void shouldDeleteTask() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.deleteTask(1);
        List<Task> savedTasks = taskManager.getListTasks();
        assertEquals(1, savedTasks.size(), "Неверное количество задач");
        assertEquals(task2, savedTasks.get(0),"Задачи не совпадают");
    }

    @Test
    public void shouldDeleteAllTask() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.deleteAllTasks();
        List<Task> tasks = taskManager.getListTasks();
        assertEquals(0, tasks.size(), "Неверное количество задач");
    }

    @Test
    public void shouldDeleteSingleTask() {
        taskManager.createTask(task1);
        taskManager.deleteAllTasks();
        List<Task> tasks = taskManager.getListTasks();
        assertEquals(0, tasks.size(), "Неверное количество задач");
    }

    @Test
    public void shouldBeNewBecauseNoSubtasks() {
        taskManager.createEpic(epic);
        taskManager.deleteAllSubtasks();
        Enum<Status> status = taskManager.getEpic(1).getStatus();
        assertEquals(Status.NEW, status, "Статус эпика без подзадач не NEW");
    }

    @Test
    public void shouldBeNewBecauseAllSubtasksNew() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        Enum<Status> status = taskManager.getEpic(1).getStatus();
        assertEquals(Status.NEW, status, "Статус эпика не NEW");
    }

    @Test
    public void shouldBeDoneBecauseAllSubtasksDone() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        Enum<Status> status = taskManager.getEpic(1).getStatus();
        assertEquals(Status.DONE, status, "Статус эпика не DONE");
    }

    @Test
    public void shouldBeInProgressBecauseAllSubtasksNewAndDone() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        subtask1.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask1);
        Enum<Status> status = taskManager.getEpic(1).getStatus();
        assertEquals(Status.IN_PROGRESS, status, "Статус эпика не DONE");
    }

    @Test
    public void shouldBeInProgressBecauseAllSubtasksInProgress() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        Enum<Status> status = taskManager.getEpic(1).getStatus();
        assertEquals(Status.IN_PROGRESS, status, "Статус эпика не DONE");
    }

    @Test
    public void shouldBeSingleSubtaskStartTime() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        LocalDateTime epicStartTime = epic.getStartTime();
        assertEquals(LocalDateTime
                        .of(2023, Month.APRIL, 22, 22, 4),
                epicStartTime, "Время не соответствует");
    }

    @Test
    public void shouldBeSingleSubtaskEndTime() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        LocalDateTime epicEndTime = epic.getEndTime();
        assertEquals(LocalDateTime
                        .of(2023, Month.APRIL, 22, 22, 5),
                epicEndTime, "Время не соответствует");
    }

    @Test
    public void shouldBeFirstSubtaskStartTime() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        LocalDateTime epicStartTime = epic.getStartTime();
        assertEquals(LocalDateTime
                .of(2023, Month.APRIL, 22, 22, 0),
                epicStartTime, "Время не соответствует");
    }

    @Test
    public void shouldBeLastSubtaskEndTime() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        LocalDateTime epicEndTime = epic.getEndTime();
        assertEquals(LocalDateTime
                        .of(2023, Month.APRIL, 22, 22, 5),
                epicEndTime, "Время не соответствует");
    }

    @Test
    public void shouldBeDuration1() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        long epicDuration = epic.getDuration();
        assertEquals(1, epicDuration, "Неверная длительность");
    }

    @Test
    public void shouldBeDuration2() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        long epicDuration = epic.getDuration();
        assertEquals(2, epicDuration, "Неверная длительность");
    }

    @Test
    public void shouldReturnPrioritizedTask() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        ArrayList<Task> prioritizedTask = new ArrayList<>(taskManager.getPrioritizedTasks());
        assertEquals(subtask2, prioritizedTask.get(0), "Задачи не совпадают");
        assertEquals(task1, prioritizedTask.get(1), "Задачи не совпадают");
        assertEquals(subtask1, prioritizedTask.get(2), "Задачи не совпадают");
        assertEquals(task2, prioritizedTask.get(3), "Задачи не совпадают");
        assertEquals(task3, prioritizedTask.get(4), "Задачи не совпадают");
    }

    @Test
    public void shouldNotBeIncludedTask3InPrioritizedTasks() {
        task3 = new Task(3,"Задача 3", Status.NEW, LocalDateTime
                .of(2023, Month.APRIL,22,22,1), 2,"Задача 3.");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        assertFalse(taskManager.getPrioritizedTasks().contains(task3), "Задача 3 создана и есть в списке");
    }
}
