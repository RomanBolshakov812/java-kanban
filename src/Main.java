import manager.InMemoryTaskManager;
import models.Epic;
import models.Subtask;
import models.Task;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        System.out.println("---Создаем 2 задачи.");
        Task task1 = new Task("Задача 1.", "Задача 1.");
        inMemoryTaskManager.createTask(task1);
        Task task2 = new Task("Задача 2.", "Задача 2.");
        inMemoryTaskManager.createTask(task2);
        System.out.println("---Создаем 2 ЭПИКА.");
        Epic epic1 = new Epic("ЭПИК 1", "Эпик 1");
        inMemoryTaskManager.createEpic(epic1);
        Epic epic2 = new Epic("ЭПИК 2", "Эпик 2");
        inMemoryTaskManager.createEpic(epic2);
        System.out.println("---Добавляем подзадачи 1 и 2 к ЭПИКУ 1.");
        Subtask subtask5 = new Subtask("Подзадача 1 к ЭПИКУ 1.", "Подзадача 1 Эпик 1.", 3);
        inMemoryTaskManager.createSubtask(subtask5);
        Subtask subtask6 = new Subtask("Подзадача 2 к ЭПИКУ 1.", "Подзадача 2 Эпик 1.", 3);
        inMemoryTaskManager.createSubtask(subtask6);
        System.out.println("---Добавляем подзадачу 1 к ЭПИКУ 2.");
        Subtask subtask7 = new Subtask("Подзадача 1 к ЭПИКУ 2.", "Подзадача 1 Эпик 2.", 4);
        inMemoryTaskManager.createSubtask(subtask7);
        System.out.println();

        System.out.println("---ОБЩИЙ СПИСОК:");
        System.out.println("---Список задач:");
        System.out.println(inMemoryTaskManager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(inMemoryTaskManager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(inMemoryTaskManager.getListSubtasks());
        System.out.println();

        System.out.println("---Изменяем статус подзадачи 1 (id 5) в ЭПИКЕ 1 на IN_PROGRESS");
        subtask5.setStatus(inMemoryTaskManager.IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(subtask5);
        System.out.println("---Изменяем статус подзадачи 1 (id 7) в ЭПИКЕ 2 на DONE");
        subtask7.setStatus(inMemoryTaskManager.DONE);
        inMemoryTaskManager.updateSubtask(subtask7);

        System.out.println("---ОБЩИЙ СПИСОК:");
        System.out.println("---Список задач:");
        System.out.println(inMemoryTaskManager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(inMemoryTaskManager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(inMemoryTaskManager.getListSubtasks());
        System.out.println();

        System.out.println("---Удаляем Задачу 2 и ЭПИК 1.");
        inMemoryTaskManager.deleteTask(2);
        inMemoryTaskManager.deleteEpic(3);
        System.out.println("---ОБЩИЙ СПИСОК:");
        System.out.println("---Список задач:");
        System.out.println(inMemoryTaskManager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(inMemoryTaskManager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(inMemoryTaskManager.getListSubtasks());
    }
}
