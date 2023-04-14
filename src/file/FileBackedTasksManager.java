package file;

import manager.InMemoryTaskManager;
import models.Epic;
import models.Subtask;
import models.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static util.ConversionsUtility.*;

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
                fileWriter.write(toFileString(task));
            }
            for (Epic epic : epics.values()) {
                fileWriter.write(toFileString(epic));
            }
            for (Subtask subTask : subtasks.values()) {
                fileWriter.write(toFileString(subTask));
            }
            fileWriter.write("\n");
            fileWriter.write(historyToFileString(inMemoryHistoryManager));
            fileWriter.close();

        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка при записи файла!");
        }
    }

    static FileBackedTasksManager loadFromFile(File file) {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        List<String> content = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                content.add(bufferedReader.readLine());
            }
            bufferedReader.close();
        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка при чтении файла!");
        }
        if (!content.isEmpty()) {
            for (int i = 1; i < content.size(); i++) {
                String line = content.get(i);
                if (!line.isBlank()) {
                    Task task = taskFromFileString(line);
                    assert task != null;
                    TaskType type = task.getTaskType();
                    switch (type) {
                        case TASK:
                            fileBackedTasksManager.tasks.put(task.getId(), task);
                            break;
                        case EPIC:
                            fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
                            break;
                        case SUBTASK:
                            fileBackedTasksManager.subtasks.put(task.getId(), (Subtask) task);
                            fileBackedTasksManager.epics.get(((Subtask) task).getEpicId()).setSubtasksId(task.getId());
                    }
                } else {
                    if ((i + 1) < content.size() && !(content.get(i + 1)).isBlank()) {
                        for (Integer taskId : historyFromFileString(content.get(i + 1))) {
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
        super.getTask(id);
        save();
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        super.getEpic(id);
        save();
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        super.getSubtask(id);
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

        File file = new File("resources/historyManagerData.csv");

        //FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(new File("resources/historyManagerData.csv"));
/*
        System.out.println("---Создаем 2 задачи.");
        Task task1 = new Task(1,"Задача 1.", "Задача 1.", Status.NEW);
        fileBackedTasksManager.createTask(task1);
        Task task2 = new Task(2,"Задача 2.", "Задача 2.", Status.NEW);
        fileBackedTasksManager.createTask(task2);

        System.out.println("---Создаем 2 ЭПИКА.");
        Epic epic3 = new Epic(3, "ЭПИК 1", "Эпик 1", Status.NEW);
        fileBackedTasksManager.createEpic(epic3);
        Epic epic4 = new Epic(4, "ЭПИК 2", "Эпик 2", Status.NEW);
        fileBackedTasksManager.createEpic(epic4);

        System.out.println("---Добавляем подзадачи 1-3 к ЭПИКУ 1.");
        Subtask subtask5 = new Subtask(5, "Подзадача 1 к ЭПИКУ 1.", "Подзадача 1 Эпик 1.", Status.NEW, 3);
        fileBackedTasksManager.createSubtask(subtask5);
        Subtask subtask6 = new Subtask(6, "Подзадача 2 к ЭПИКУ 1.", "Подзадача 2 Эпик 1.", Status.NEW, 3);
        fileBackedTasksManager.createSubtask(subtask6);
        Subtask subtask7 = new Subtask(7, "Подзадача 3 к ЭПИКУ 1.", "Подзадача 2 Эпик 1.", Status.NEW, 3);
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
*/
        System.out.println("Удаляем задачу 1 и ЭПИК 1");
        fileBackedTasksManager2.deleteTask(1);
        fileBackedTasksManager2.deleteSubtask(6);

        System.out.println("Вызываем историю просмотров:");
        System.out.println();
        //System.out.println(fileBackedTasksManager.getHistory());
        System.out.println(fileBackedTasksManager2.getListTasks());
        System.out.println(fileBackedTasksManager2.getListSubtasks());
        System.out.println(fileBackedTasksManager2.getListEpics());
        System.out.println(fileBackedTasksManager2.getHistory());
    }
}
