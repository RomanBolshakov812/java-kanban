import manager.Managers;
import manager.TaskManager;
import models.Epic;
import models.Subtask;
import models.Task;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        TaskManager inMemoryTaskManager = Managers.getDefault();
        System.out.println("---Создаем 4 задачи.");
        Task task1 = new Task("Задача 1.", "Задача 1.");
        inMemoryTaskManager.createTask(task1);
        Task task2 = new Task("Задача 2.", "Задача 2.");
        inMemoryTaskManager.createTask(task2);
        Task task3 = new Task("Задача 3.", "Задача 3.");
        inMemoryTaskManager.createTask(task3);
        Task task4 = new Task("Задача 4.", "Задача 4.");
        inMemoryTaskManager.createTask(task4);

        System.out.println("---Создаем 4 ЭПИКА.");
        Epic epic5 = new Epic("ЭПИК 1", "Эпик 1");
        inMemoryTaskManager.createEpic(epic5);
        Epic epic6 = new Epic("ЭПИК 2", "Эпик 2");
        inMemoryTaskManager.createEpic(epic6);
        Epic epic7 = new Epic("ЭПИК 3", "Эпик 3");
        inMemoryTaskManager.createEpic(epic7);
        Epic epic8 = new Epic("ЭПИК 4", "Эпик 4");
        inMemoryTaskManager.createEpic(epic8);

        System.out.println("---Добавляем подзадачи 1 и 2 к ЭПИКУ 1.");
        Subtask subtask9 = new Subtask("Подзадача 1 к ЭПИКУ 1.", "Подзадача 1 Эпик 1.", 5);
        inMemoryTaskManager.createSubtask(subtask9);
        Subtask subtask10 = new Subtask("Подзадача 2 к ЭПИКУ 1.", "Подзадача 2 Эпик 1.", 5);
        inMemoryTaskManager.createSubtask(subtask10);
        System.out.println("---Добавляем подзадачи 1-3 к ЭПИКУ 2.");
        Subtask subtask11 = new Subtask("Подзадача 1 к ЭПИКУ 2.", "Подзадача 1 Эпик 2.", 6);
        inMemoryTaskManager.createSubtask(subtask11);
        Subtask subtask12 = new Subtask("Подзадача 2 к ЭПИКУ 2.", "Подзадача 1 Эпик 2.", 6);
        inMemoryTaskManager.createSubtask(subtask12);
        Subtask subtask13 = new Subtask("Подзадача 3 к ЭПИКУ 2.", "Подзадача 1 Эпик 2.", 6);
        inMemoryTaskManager.createSubtask(subtask13);
        System.out.println("---Добавляем подзадачу 1 к ЭПИКУ 3.");
        Subtask subtask14 = new Subtask("Подзадача 1 к ЭПИКУ 3.", "Подзадача 1 Эпик 3.", 7);
        inMemoryTaskManager.createSubtask(subtask14);
        System.out.println();
        System.out.println("---ОБЩИЙ СПИСОК:");
        System.out.println("---Список задач:");
        System.out.println(inMemoryTaskManager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(inMemoryTaskManager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(inMemoryTaskManager.getListSubtasks());
        System.out.println();
        System.out.println("Вызываем более 10 задач разных типов и выводим историю просмотров:");
        System.out.println();
        inMemoryTaskManager.getSubtask(9);
        inMemoryTaskManager.getTask(1);
        inMemoryTaskManager.getTask(4);
        inMemoryTaskManager.getTask(3);
        inMemoryTaskManager.getEpic(7);
        inMemoryTaskManager.getSubtask(14);
        inMemoryTaskManager.getTask(2);
        inMemoryTaskManager.getTask(1);
        inMemoryTaskManager.getEpic(5);
        inMemoryTaskManager.getListSubtasksOfEpic(6);
        inMemoryTaskManager.getSubtask(9);
        inMemoryTaskManager.getEpic(8);
        inMemoryTaskManager.getEpic(6);
        inMemoryTaskManager.getSubtask(12);
        inMemoryTaskManager.getTask(4);
        System.out.println(inMemoryTaskManager.getHistoryList().getHistory());
    }
}
