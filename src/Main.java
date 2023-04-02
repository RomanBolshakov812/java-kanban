import manager.Managers;
import manager.TaskManager;
import models.Epic;
import models.Subtask;
import models.Task;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        TaskManager inMemoryTaskManager = Managers.getDefault();

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
        System.out.println(inMemoryTaskManager.getHistory());
    }
}
