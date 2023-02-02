public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();
        System.out.println("---Создаем 4 задачи.");
        manager.createTask("Задача 1.", "Задача 1.", manager.NEW);
        manager.createTask("Задача 2.", "Задача 2.", manager.NEW);
        manager.createTask("Задача 3.", "Задача 3.", manager.NEW);
        manager.createTask("Задача 4.", "Задача 4.", manager.NEW);

        System.out.println("---Добавляем подзадачи 1 и 2 к задаче 3 (Задача 3 становится ЭПИКОМ 1):");
        manager.addSubtask("Подзадача 1 к ЭПИКУ 1.", "Подзадача 1 ЭПИК 1 (задача 3 стала ЭПИКОМ 1).",
                manager.NEW, 3);
        manager.addSubtask("Подзадача 2 к ЭПИКУ 1.", "Подзадача 2 ЭПИК 1" +
                "(добавлена к уже существующему ЭПИКУ 1).", manager.NEW, 3);

        System.out.println("---Добавляем подзадачу 1 к задаче 4 (Задача 4 становится ЭПИКОМ 2):");
        manager.addSubtask("Подзадача 1 к ЭПИКУ 2.", "Подзадача 1 ЭПИК 2(задача 4 стала ЭПИКОМ 2).",
                manager.NEW, 4);

        System.out.println("---Изменим названия задач 3 и 4 на ЭПИК 1 и ЭПИК 2 (для наглядности)");
        manager.epics.get(3).setTitle("ЭПИК 1");
        manager.epics.get(4).setTitle("ЭПИК 2");
        System.out.println();
        System.out.println("---ОБЩИЙ СПИСОК:");
        System.out.println("---Список задач:");
        System.out.println(manager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(manager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(manager.getListSubtasks());
        System.out.println();
        System.out.println("---ПОДРОБНЫЙ СПИСОК:");
        System.out.println("---Список задач:");
        for (Task task : manager.tasks.values()) {
            System.out.println("Задача: " + task.getTitle() + " id задачи: " + task.getId() + ". Статус: "
                    + task.getStatus() + ".");
        }
        System.out.println();
        System.out.println("---Список ЭПИКОВ:");
        for (Epic epic : manager.epics.values()) {
            System.out.println((epic.getTitle() + ". " + " id ЭПИКА: " + epic.getId() + ". Статус: "
                    + epic.getStatus() + "."));
            System.out.println("Подзадачи ЭПИКА:");
            for (int subtaskId : epic.subtasksId){
                System.out.println(manager.subtasks.get(subtaskId).getTitle() + " id задачи: "
                        + manager.subtasks.get(subtaskId).getId()
                        + ". Статус: " + manager.subtasks.get(subtaskId).getStatus() + ".");
            }
            System.out.println();
        }
        System.out.println("---Изменяем статус подзадачи 1 (id 5) в ЭПИКЕ 1 на IN_PROGRESS");
        manager.subtasks.get(5).setStatus(manager.IN_PROGRESS);
        manager.updateSubtask(manager.subtasks.get(5));
        System.out.println("---Изменяем статус подзадачи 1 (id 7) в ЭПИКЕ 2 на DONE");
        manager.subtasks.get(7).setStatus(manager.DONE);
        manager.updateSubtask(manager.subtasks.get(7));
        System.out.println("---ПОДРОБНЫЙ СПИСОК:");
        System.out.println("---Список задач:");
        for (Task task : manager.tasks.values()) {
            System.out.println("Задача: " + task.getTitle() + " id задачи: " + task.getId() + ". Статус: "
                    + task.getStatus() + ".");
        }
        System.out.println();
        System.out.println("---Список ЭПИКОВ:");
        for (Epic epic : manager.epics.values()) {
            System.out.println((epic.getTitle() + ". " + " id ЭПИКА: " + epic.getId() + ". Статус: "
                    + epic.getStatus() + "."));
            System.out.println("Подзадачи ЭПИКА:");
            for (int subtaskId : epic.subtasksId){
                System.out.println(manager.subtasks.get(subtaskId).getTitle() + " id задачи: "
                        + manager.subtasks.get(subtaskId).getId()
                        + ". Статус: " + manager.subtasks.get(subtaskId).getStatus() + ".");
            }
            System.out.println();
        }
        System.out.println("---Удаляем Задачу 2 и ЭПИК 1.");
        manager.deleteTask(2);
        manager.deleteEpic(3);
        System.out.println("---ПОДРОБНЫЙ СПИСОК:");
        System.out.println("---Список задач:");
        for (Task task : manager.tasks.values()) {
            System.out.println("Задача: " + task.getTitle() + " id задачи: " + task.getId() + ". Статус: "
                    + task.getStatus() + ".");
        }
        System.out.println();
        System.out.println("---Список ЭПИКОВ:");
        for (Epic epic : manager.epics.values()) {
            System.out.println((epic.getTitle() + ". " + " id ЭПИКА: " + epic.getId() + ". Статус: "
                    + epic.getStatus() + "."));
            System.out.println("Подзадачи ЭПИКА:");
            for (int subtaskId : epic.subtasksId){
                System.out.println(manager.subtasks.get(subtaskId).getTitle() + " id задачи: "
                        + manager.subtasks.get(subtaskId).getId()
                        + ". Статус: " + manager.subtasks.get(subtaskId).getStatus() + ".");
            }
            System.out.println();
        }
        System.out.println("---ОБЩИЙ СПИСОК:");
        System.out.println("---Список задач:");
        System.out.println(manager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(manager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(manager.getListSubtasks());
    }
}
