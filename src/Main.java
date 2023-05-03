import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task(1,"Задача 1", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,1), 1,"Задача 1.");
        Task task2 = new Task(2,"Задача 2", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,3), 1,"Задача 2.");
        Task task3 = new Task(3,"Задача 3", Status.NEW, null, 1,"Задача 3.");
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.createTask(task3);
        Epic epic = new Epic(4, "ЭПИК 1", Status.NEW,null,0,"Эпик 1");
        inMemoryTaskManager.createEpic(epic);
        Subtask subtask1 = new Subtask(4,"Подзадача 1 к ЭПИКУ 1", Status.NEW, null,
                1,"Подзадача 1 Эпик 1.", 4);
        Subtask subtask2 = new Subtask(5,"Подзадача 2 к ЭПИКУ 1", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,0), 1,"Подзадача 2 Эпик 1.", 4);
        inMemoryTaskManager.createSubtask(subtask1);
        inMemoryTaskManager.createSubtask(subtask2);
        System.out.println(inMemoryTaskManager.getPrioritizedTasks());
    }
}
