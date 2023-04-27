import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

import java.time.LocalDateTime;
import java.time.Month;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task(1,"Задача 1", Status.NEW, LocalDateTime.of(2023,Month.APRIL,22,22,1), 2,"Задача 1.");
        Task task2 = new Task(2,"Задача 2", Status.NEW, LocalDateTime.of(2023,Month.APRIL,22,22,4), 2,"Задача 2.");
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.deleteTask(1);
        System.out.println(inMemoryTaskManager.getListTasks().size());


/*
        System.out.println();
        TaskManager inMemoryTaskManager = Managers.getDefault();

        System.out.println("---Создаем 4 задачи.");
        Task task1 = new Task(1,"Задача 1", Status.NEW, LocalDateTime.of(2023,Month.APRIL,22,22,1), 2,"Задача 1.");
        inMemoryTaskManager.createTask(task1);
        Task task2 = new Task(2,"Задача 2", Status.NEW, LocalDateTime.of(2023,Month.APRIL,22,22,4), 2,"Задача 2.");
        inMemoryTaskManager.createTask(task2);
        Task task3 = new Task(3,"Задача 3", Status.NEW, LocalDateTime.of(2023,Month.APRIL,22,22,5), 8,"Задача 3.");
        inMemoryTaskManager.createTask(task3);
        Task task4 = new Task(4,"Задача 4", Status.NEW, LocalDateTime.of(2023,Month.APRIL,22,22,7), 2,"Задача 4.");
        inMemoryTaskManager.createTask(task4);

        System.out.println("---Создаем 2 ЭПИКА.");
        Epic epic5 = new Epic(5, "ЭПИК 1", Status.NEW,null,0,"Эпик 1");
        inMemoryTaskManager.createEpic(epic5);
        Epic epic6 = new Epic(6, "ЭПИК 2", Status.NEW,null,0,"Эпик 2");
        inMemoryTaskManager.createEpic(epic6);

        System.out.println("---Добавляем подзадачи 1-3 к ЭПИКУ 1.");
        Subtask subtask7 = new Subtask(7,"Подзадача 1 к ЭПИКУ 1", Status.NEW, LocalDateTime.of(2023,Month.APRIL,22,22,9), 2,"Подзадача 1 Эпик 1.", 5);
        inMemoryTaskManager.createSubtask(subtask7);
        Subtask subtask8 = new Subtask(8,"Подзадача 2 к ЭПИКУ 1", Status.NEW, LocalDateTime.of(2023,Month.APRIL,22,22,10), 9,"Подзадача 2 Эпик 1.", 5);
        inMemoryTaskManager.createSubtask(subtask8);
        Subtask subtask9 = new Subtask(9,"Подзадача 3 к ЭПИКУ 1", Status.NEW, LocalDateTime.of(2023,Month.APRIL,22,22,12), 10,"Подзадача 3 Эпик 1.", 5);
        inMemoryTaskManager.createSubtask(subtask9);

        System.out.println("---ОБЩИЙ СПИСОК:");
        System.out.println("---Список задач:");
        System.out.println(inMemoryTaskManager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(inMemoryTaskManager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(inMemoryTaskManager.getListSubtasks());
        System.out.println();

        System.out.println("Список задач/подзадач ПО ПРИОРИТЕТУ:");
        System.out.println(inMemoryTaskManager.getPrioritizedTasks());
*/


/*
        System.out.println("---Создаем 2 задачи.");
        Task task1 = new Task("Задача 1.", "Задача 1.");
        inMemoryTaskManager.createTask(task1);
        Task task2 = new Task("Задача 2.", "Задача 2.");
        inMemoryTaskManager.createTask(task2);

        System.out.println("---Создаем 2 ЭПИКА.");
        Epic epic3 = new Epic("ЭПИК 1", "Эпик 1");
        inMemoryTaskManager.createEpic(epic3);
        Epic epic4 = new Epic("ЭПИК 2", "Эпик 2");
        inMemoryTaskManager.createEpic(epic4);

        System.out.println("---Добавляем подзадачи 1-3 к ЭПИКУ 1.");
        Subtask subtask5 = new Subtask("Подзадача 1 к ЭПИКУ 1.", "Подзадача 1 Эпик 1.", 3);
        inMemoryTaskManager.createSubtask(subtask5);
        Subtask subtask6 = new Subtask("Подзадача 2 к ЭПИКУ 1.", "Подзадача 2 Эпик 1.", 3);
        inMemoryTaskManager.createSubtask(subtask6);
        Subtask subtask7 = new Subtask("Подзадача 3 к ЭПИКУ 1.", "Подзадача 2 Эпик 1.", 3);
        inMemoryTaskManager.createSubtask(subtask7);

        System.out.println("---ОБЩИЙ СПИСОК:");
        System.out.println("---Список задач:");
        System.out.println(inMemoryTaskManager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(inMemoryTaskManager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(inMemoryTaskManager.getListSubtasks());
        System.out.println();

        System.out.println("Вызываем задачи.");
        System.out.println();
        inMemoryTaskManager.getTask(1);
        inMemoryTaskManager.getTask(2);
        inMemoryTaskManager.getEpic(3);
        inMemoryTaskManager.getTask(1);
        inMemoryTaskManager.getEpic(4);
        inMemoryTaskManager.getSubtask(5);
        inMemoryTaskManager.getEpic(3);
        inMemoryTaskManager.getSubtask(6);
        inMemoryTaskManager.getSubtask(7);
        inMemoryTaskManager.getSubtask(5);

        System.out.println("Вызываем историю просмотров:");
        System.out.println();
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("Удаляем задачу 1.");
        inMemoryTaskManager.deleteTask(1);

        System.out.println("Вызываем историю просмотров:");
        System.out.println();
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("Удаляем подзадачу 1 ЭПИКА 1.");
        inMemoryTaskManager.deleteSubtask(5);

        System.out.println("Вызываем историю просмотров:");
        System.out.println();
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("Удаляем ЭПИК 1.");
        inMemoryTaskManager.deleteEpic(3);

        System.out.println("Вызываем историю просмотров:");
        System.out.println();
        System.out.println(inMemoryTaskManager.getHistory());*/
    }
}
