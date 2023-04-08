package fileSavingManager;

import manager.HistoryManager;
import manager.InMemoryTaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public void save() {
        try {
            Writer fileWriter = new FileWriter(file);
            fileWriter.write("id,type,name,status,description,epic\n");

            for (Task task : tasks.values()) {
                fileWriter.write(toString(task));
            }
            for (Epic epic : epics.values()) {
                fileWriter.write(toString(epic));
            }
            for (Subtask subTask : subtasks.values()) {
                fileWriter.write(toString(subTask));
            }
            fileWriter.write("\n");
            fileWriter.write(historyToString(inMemoryHistoryManager));
            fileWriter.close();

        } catch (IOException exception) {
            // Я пока так и не разобрался как и зачем нужно использовать ManagerSaveException
            System.out.println("Ошибка при записи файла");
        }
    }

    private static String toString(Task task) {
        return  task.getId() + "," +
                TaskType.TASK + "," +
                task.getTitle() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," + '\n';
    }

    private static String toString(Epic epic) {
        return  epic.getId() + "," +
                TaskType.EPIC + "," +
                epic.getTitle() + "," +
                epic.getStatus() + "," +
                epic.getDescription() + "," + '\n';
    }

    private static String toString(Subtask subtask) {
        return  subtask.getId() + "," +
                TaskType.SUBTASK + "," +
                subtask.getTitle() + "," +
                subtask.getStatus() + "," +
                subtask.getDescription() + "," +
                subtask.getEpicId() + "," + '\n';
    }

    private static String historyToString(HistoryManager manager) {
        String historyString = "";
        for (Task task : manager.getHistory()) {
           historyString = historyString + task.getId() + ",";
        }
        return historyString;
    }

    private static Task taskFromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        String type = parts[1];
        String title = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];
        Task task = null;
        switch (type) {
            case ("TASK"):
                task = new Task(title, description);
                break;
            case ("EPIC"):
                task = new Epic(title, description);
                break;
            case ("SUBTASK"):
                int epicId = Integer.parseInt(parts[5]);
                task = new Subtask(title, description, epicId);
                break;
            default:
                break;
        }
        assert task != null;
        task.setId(id);
        task.setStatus(status);
        return task;
    }

    private static List<Integer> historyFromString(String value) {

        List<Integer> history = new ArrayList<>();
        String[] parts = value.split(",");
        for (String part : parts) {
            history.add(Integer.parseInt(part));
        }
        return history;
    }

    static FileBackedTasksManager loadFromFile(File file) {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        List<String> content = FileReader.readFileContents(file.getPath());
        if (!content.isEmpty()) {
            for (int i = 1; i < content.size(); i++) {
                String line = content.get(i);
                if (!line.isBlank()) {
                    Task task = taskFromString(line);
                    if (task.getClass().equals(Task.class)) {
                        fileBackedTasksManager.tasks.put(task.getId(), task);
                    } else if (task.getClass().equals(Epic.class)) {
                        fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
                    } else if (task.getClass().equals(Subtask.class)) {
                        fileBackedTasksManager.subtasks.put(task.getId(), (Subtask) task);
                    }
                } else {
                    if ((i + 1) < content.size() && !(content.get(i + 1)).isBlank()) {
                        for (Integer taskId : historyFromString(content.get(i + 1))) {
                            if (fileBackedTasksManager.tasks.containsKey(taskId)) {
                                fileBackedTasksManager.inMemoryHistoryManager.addHistory(fileBackedTasksManager.tasks.get(taskId));
                            } else if (fileBackedTasksManager.epics.containsKey(taskId)) {
                                fileBackedTasksManager.inMemoryHistoryManager.addHistory(fileBackedTasksManager.epics.get(taskId));
                            } else if (fileBackedTasksManager.subtasks.containsKey(taskId)) {
                                fileBackedTasksManager.inMemoryHistoryManager.addHistory(fileBackedTasksManager.subtasks.get(taskId));
                            }
                        }
                        break;
                    }
                }
            }
        }
        return fileBackedTasksManager;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public Task getTask(int id) {
        inMemoryHistoryManager.addHistory(tasks.get(id));
        save();
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        inMemoryHistoryManager.addHistory(epics.get(id));
        save();
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        inMemoryHistoryManager.addHistory(subtasks.get(id));
        save();
        return subtasks.get(id);
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void changeEpicStatus(Epic epic) {
        super.changeEpicStatus(epic);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    public static void main(String[] args) {

        //FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File("resources/historyManagerData.csv"));
        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(new File("resources/historyManagerData.csv"));

        /*
        System.out.println("---Создаем 2 задачи.");
        Task task1 = new Task("Задача 1.", "Задача 1.");
        fileBackedTasksManager.createTask(task1);
        Task task2 = new Task("Задача 2.", "Задача 2.");
        fileBackedTasksManager.createTask(task2);

        System.out.println("---Создаем 2 ЭПИКА.");
        Epic epic3 = new Epic("ЭПИК 1", "Эпик 1");
        fileBackedTasksManager.createEpic(epic3);
        Epic epic4 = new Epic("ЭПИК 2", "Эпик 2");
        fileBackedTasksManager.createEpic(epic4);

        System.out.println("---Добавляем подзадачи 1-3 к ЭПИКУ 1.");
        Subtask subtask5 = new Subtask("Подзадача 1 к ЭПИКУ 1.", "Подзадача 1 Эпик 1.", 3);
        fileBackedTasksManager.createSubtask(subtask5);
        Subtask subtask6 = new Subtask("Подзадача 2 к ЭПИКУ 1.", "Подзадача 2 Эпик 1.", 3);
        fileBackedTasksManager.createSubtask(subtask6);
        Subtask subtask7 = new Subtask("Подзадача 3 к ЭПИКУ 1.", "Подзадача 2 Эпик 1.", 3);
        fileBackedTasksManager.createSubtask(subtask7);

        System.out.println("Вызываем задачи.");
        System.out.println();
        fileBackedTasksManager.getTask(1);
        fileBackedTasksManager.getTask(2);
        fileBackedTasksManager.getEpic(3);
        fileBackedTasksManager.getTask(1);
        fileBackedTasksManager.getEpic(4);
        fileBackedTasksManager.getSubtask(5);
        fileBackedTasksManager.getEpic(3);
        fileBackedTasksManager.getSubtask(6);
        fileBackedTasksManager.getSubtask(7);
        fileBackedTasksManager.getSubtask(5);

        System.out.println("Вызываем историю просмотров:");
        System.out.println();
        System.out.println(fileBackedTasksManager.getHistory());


        System.out.println("Удаляем задачу 1 и ЭПИК 1");
        fileBackedTasksManager.deleteTask(1);
        fileBackedTasksManager.deleteEpic(3);

        System.out.println("Вызываем историю просмотров (менеджер 1):");
        System.out.println();
        System.out.println(fileBackedTasksManager.getHistory());
        */

        System.out.println("Вызываем историю просмотров (менеджер 2):");
        System.out.println();
        System.out.println(fileBackedTasksManager2.getHistory());
    }
}
