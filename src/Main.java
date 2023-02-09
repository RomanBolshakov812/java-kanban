import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import models.Epic;
import models.Subtask;
import models.Task;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        TaskManager taskManager = Managers.getDefault();
        System.out.println("---Создаем 4 задачи.");
        Task task1 = new Task("Задача 1.", "Задача 1.");
        taskManager.createTask(task1);
        Task task2 = new Task("Задача 2.", "Задача 2.");
        taskManager.createTask(task2);
        Task task3 = new Task("Задача 3.", "Задача 3.");
        taskManager.createTask(task3);
        Task task4 = new Task("Задача 4.", "Задача 4.");
        taskManager.createTask(task4);

        System.out.println("---Создаем 4 ЭПИКА.");
        Epic epic5 = new Epic("ЭПИК 1", "Эпик 1");
        taskManager.createEpic(epic5);
        Epic epic6 = new Epic("ЭПИК 2", "Эпик 2");
        taskManager.createEpic(epic6);
        Epic epic7 = new Epic("ЭПИК 3", "Эпик 3");
        taskManager.createEpic(epic7);
        Epic epic8 = new Epic("ЭПИК 4", "Эпик 4");
        taskManager.createEpic(epic8);

        System.out.println("---Добавляем подзадачи 1 и 2 к ЭПИКУ 1.");
        Subtask subtask9 = new Subtask("Подзадача 1 к ЭПИКУ 1.", "Подзадача 1 Эпик 1.", 5);
        taskManager.createSubtask(subtask9);
        Subtask subtask10 = new Subtask("Подзадача 2 к ЭПИКУ 1.", "Подзадача 2 Эпик 1.", 5);
        taskManager.createSubtask(subtask10);
        System.out.println("---Добавляем подзадачи 1-3 к ЭПИКУ 2.");
        Subtask subtask11 = new Subtask("Подзадача 1 к ЭПИКУ 2.", "Подзадача 1 Эпик 2.", 6);
        taskManager.createSubtask(subtask11);
        Subtask subtask12 = new Subtask("Подзадача 2 к ЭПИКУ 2.", "Подзадача 1 Эпик 2.", 6);
        taskManager.createSubtask(subtask12);
        Subtask subtask13 = new Subtask("Подзадача 3 к ЭПИКУ 2.", "Подзадача 1 Эпик 2.", 6);
        taskManager.createSubtask(subtask13);
        System.out.println("---Добавляем подзадачу 1 к ЭПИКУ 3.");
        Subtask subtask14 = new Subtask("Подзадача 1 к ЭПИКУ 3.", "Подзадача 1 Эпик 3.", 7);
        taskManager.createSubtask(subtask14);
        System.out.println();
        System.out.println("---ОБЩИЙ СПИСОК:");
        System.out.println("---Список задач:");
        System.out.println(taskManager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(taskManager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(taskManager.getListSubtasks());
        System.out.println();
        System.out.println("Вызываем более 10 задач разных типов и выводим историю просмотров:");
        System.out.println();
        taskManager.getSubtask(9);
        taskManager.getTask(1);
        taskManager.getTask(4);
        taskManager.getTask(3);
        taskManager.getEpic(7);
        taskManager.getSubtask(14);
        taskManager.getTask(2);
        taskManager.getTask(1);
        taskManager.getEpic(5);
        taskManager.getListSubtasksOfEpic(6);
        taskManager.getSubtask(9);
        taskManager.getEpic(8);
        taskManager.getEpic(6);
        taskManager.getSubtask(12);
        taskManager.getTask(4);
        System.out.println(taskManager.getHistory());
    }
}
