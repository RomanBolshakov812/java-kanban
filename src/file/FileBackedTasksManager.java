package file;

import exceptions.ManagerSaveException;
import manager.InMemoryTaskManager;
import models.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import static util.ConversionsUtility.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTasksManager() {
    }
    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public void save() {
        try {
            Writer fileWriter = new FileWriter(file);
            fileWriter.write("id,type,name,status,startTime,duration,description,epic\n");

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

    public static FileBackedTasksManager loadFromFile(File file) {

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
                            fileBackedTasksManager.tasksByStartTime.add(task);
                            break;
                        case EPIC:
                            fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
                            break;
                        case SUBTASK:
                            fileBackedTasksManager.subtasks.put(task.getId(), (Subtask) task);
                            fileBackedTasksManager.epics.get(((Subtask) task).getEpicId()).setSubtasksId(task.getId());
                            fileBackedTasksManager.tasksByStartTime.add(task);
                    }
                } else {
                    if ((i + 1) < content.size() && !(content.get(i + 1)).isBlank()) {
                        loadHistory(content.get(i + 1), fileBackedTasksManager);
                        break;
                    }
                }
            }
        }
        return fileBackedTasksManager;
    }

    protected static void loadHistory(String string, FileBackedTasksManager fileBackedTasksManager) {
        for (Integer taskId : historyFromIdString(string)) {
            if (fileBackedTasksManager.tasks.containsKey(taskId)) {
                fileBackedTasksManager.inMemoryHistoryManager
                        .addHistory(fileBackedTasksManager.tasks.get(taskId));
            } else if (fileBackedTasksManager.epics.containsKey(taskId)) {
                fileBackedTasksManager.inMemoryHistoryManager
                        .addHistory(fileBackedTasksManager.epics.get(taskId));
            } else if (fileBackedTasksManager.subtasks.containsKey(taskId)) {
                fileBackedTasksManager.inMemoryHistoryManager
                        .addHistory(fileBackedTasksManager.subtasks.get(taskId));
            }
        }
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
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    protected boolean isTimeIntervalCheck(Task newTask) {
        boolean isCheck = super.isTimeIntervalCheck(newTask);
        save();
        return isCheck;
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
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        System.out.println();
        Task task1 = new Task(1,"Задача 1", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,6), 1,"Задача 1.");
        Task task2 = new Task(2,"Задача 2", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,2), 1,"Задача 2.");
        Task task3 = new Task(3,"Задача 3", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,8), 1,"Задача 3.");
        fileBackedTasksManager.createTask(task1);
        fileBackedTasksManager.createTask(task2);
        fileBackedTasksManager.createTask(task3);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Epic epic1 = new Epic(4, "ЭПИК 1", Status.NEW,null,0,"Эпик 1", null);
        Epic epic2 = new Epic(5, "ЭПИК 2", Status.NEW,null,0,"Эпик 2", null);
        Epic epic3 = new Epic(6, "ЭПИК 3", Status.NEW,null,0,"Эпик 3", null);
        fileBackedTasksManager.createEpic(epic1);
        fileBackedTasksManager.createEpic(epic2);
        fileBackedTasksManager.createEpic(epic3);
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
        fileBackedTasksManager.createSubtask(subtask1);
        fileBackedTasksManager.createSubtask(subtask2);
        fileBackedTasksManager.createSubtask(subtask3);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Task task4 = new Task(10,"Задача 4", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,12), 1,"Задача 4.");
        Task task5 = new Task(11,"Задача 5", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,14), 1,"Задача 5.");
        fileBackedTasksManager.createTask(task4);
        fileBackedTasksManager.createTask(task5);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Subtask subtask4 = new Subtask(12,"Подзадача 2 к ЭПИКУ 3", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,10),
                1,"Подзадача 2 Эпик 3.", 6);
        Subtask subtask5 = new Subtask(13,"Подзадача 1 к ЭПИКУ 2", Status.NEW, LocalDateTime
                .of(2023,Month.APRIL,22,22,18),
                1,"Подзадача 1 Эпик 2.", 5);
        fileBackedTasksManager.createSubtask(subtask4);
        fileBackedTasksManager.createSubtask(subtask5);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        fileBackedTasksManager.getTask(3);
        fileBackedTasksManager.getEpic(4);
        fileBackedTasksManager.getSubtask(7);
        fileBackedTasksManager.getTask(11);
        fileBackedTasksManager.getTask(10);
        fileBackedTasksManager.getEpic(5);
        fileBackedTasksManager.getSubtask(9);
        fileBackedTasksManager.getTask(2);
        fileBackedTasksManager.getSubtask(12);
        fileBackedTasksManager.getSubtask(8);
        fileBackedTasksManager.getTask(11);
        fileBackedTasksManager.getTask(2);
        fileBackedTasksManager.getEpic(6);
        fileBackedTasksManager.getSubtask(13);
        fileBackedTasksManager.getEpic(5);
        fileBackedTasksManager.getTask(3);
        fileBackedTasksManager.getSubtask(7);
        fileBackedTasksManager.getEpic(4);

        System.out.println(fileBackedTasksManager.getPrioritizedTasks());
    }
}
