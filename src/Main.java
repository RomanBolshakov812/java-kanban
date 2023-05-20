import http.KVServer.KVServer;
import http.server.HttpTaskManager;
import http.server.HttpTaskServer;
import manager.Managers;
import models.Status;
import models.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        new KVServer().start();
        HttpTaskManager httpTaskManager = (HttpTaskManager) Managers.getDefault();
        new HttpTaskServer().start(httpTaskManager);

        Task task1 = new Task(1,"Задача 1", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,6), 1,"Задача 1.");
        Task task2 = new Task(2,"Задача 2", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,2), 1,"Задача 2.");
        Task task3 = new Task(3,"Задача 3", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,8), 1,"Задача 3.");
        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        httpTaskManager.createTask(task3);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Epic epic1 = new Epic(4, "ЭПИК 1", Status.NEW,null,0,"Эпик 1", null);
        Epic epic2 = new Epic(5, "ЭПИК 2", Status.NEW,null,0,"Эпик 2", null);
        Epic epic3 = new Epic(6, "ЭПИК 3", Status.NEW,null,0,"Эпик 3", null);
        httpTaskManager.createEpic(epic1);
        httpTaskManager.createEpic(epic2);
        httpTaskManager.createEpic(epic3);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Subtask subtask1 = new Subtask(7,"Подзадача 1 к ЭПИКУ 1", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,16),
                1,"Подзадача 1 Эпик 1.", 4);
        Subtask subtask2 = new Subtask(8,"Подзадача 2 к ЭПИКУ 1", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,0),
                1,"Подзадача 2 Эпик 1.", 4);
        Subtask subtask3 = new Subtask(9,"Подзадача 1 к ЭПИКУ 3", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,4),
                1,"Подзадача 1 Эпик 3.", 6);
        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        httpTaskManager.createSubtask(subtask3);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Task task4 = new Task(10,"Задача 4", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,12), 1,"Задача 4.");
        Task task5 = new Task(11,"Задача 5", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,14), 1,"Задача 5.");
        httpTaskManager.createTask(task4);
        httpTaskManager.createTask(task5);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Subtask subtask4 = new Subtask(12,"Подзадача 2 к ЭПИКУ 3", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,10),
                1,"Подзадача 2 Эпик 3.", 6);
        Subtask subtask5 = new Subtask(13,"Подзадача 1 к ЭПИКУ 2", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,18),
                1,"Подзадача 1 Эпик 2.", 5);
        httpTaskManager.createSubtask(subtask4);
        httpTaskManager.createSubtask(subtask5);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        httpTaskManager.getTask(3);
        httpTaskManager.getEpic(4);
        httpTaskManager.getSubtask(7);
        httpTaskManager.getTask(11);
        httpTaskManager.getTask(10);
        httpTaskManager.getEpic(5);
        httpTaskManager.getSubtask(9);
        httpTaskManager.getTask(2);
        httpTaskManager.getSubtask(12);
        httpTaskManager.getSubtask(8);
        httpTaskManager.getTask(11);
        httpTaskManager.getTask(2);
        httpTaskManager.getEpic(6);
        httpTaskManager.getSubtask(13);
        httpTaskManager.getEpic(5);
        httpTaskManager.getTask(3);
        httpTaskManager.getSubtask(7);
        httpTaskManager.getEpic(4);

        System.out.println(httpTaskManager.getPrioritizedTasks());


/*
        HttpTaskManager httpTaskManager2 = (HttpTaskManager) Managers.getDefault();
        httpTaskManager2.load(httpTaskManager2);
        new HttpTaskServer().start(httpTaskManager2);
*/
    }
}
