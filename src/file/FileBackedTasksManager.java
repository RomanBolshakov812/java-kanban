package file;

import manager.InMemoryTaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
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
        System.out.println("Создали МЕНЕДЖЕР 1 из пустого файла.");

        System.out.println("---Создаем 4 задачи.");
        Task task1 = new Task(1,"Задача 1", Status.NEW, null, 50,"Задача 1.");
        fileBackedTasksManager.createTask(task1);
        Task task2 = new Task(2,"Задача 2", Status.NEW, null, 70,"Задача 2.");
        fileBackedTasksManager.createTask(task2);
        Task task3 = new Task(3,"Задача 3", Status.NEW, LocalDateTime.of(2023,Month.APRIL,22,22,3), 70,"Задача 3.");
        fileBackedTasksManager.createTask(task3);
        Task task4 = new Task(4,"Задача 4", Status.NEW, LocalDateTime.of(2023,Month.APRIL,22,22,1), 70,"Задача 4.");
        fileBackedTasksManager.createTask(task4);

        System.out.println("---Создаем 3 ЭПИКА.");
        Epic epic5 = new Epic(5, "ЭПИК 1", Status.NEW,null,0,"Эпик 1");
        fileBackedTasksManager.createEpic(epic5);
        Epic epic6 = new Epic(6, "ЭПИК 2", Status.NEW,null,0,"Эпик 2");
        fileBackedTasksManager.createEpic(epic6);
        Epic epic7 = new Epic(7, "ЭПИК 3", Status.NEW,null,0,"Эпик 3");
        fileBackedTasksManager.createEpic(epic7);

        System.out.println("---Добавляем подзадачи 1-3 к ЭПИКУ 1.");
        Subtask subtask8 = new Subtask(8,"Подзадача 1 к ЭПИКУ 1", Status.NEW, LocalDateTime.of
                (2023,Month.APRIL,22,22,2), 10,"Подзадача 1 Эпик 1.", 5);
        fileBackedTasksManager.createSubtask(subtask8);
        Subtask subtask9 = new Subtask(9,"Подзадача 2 к ЭПИКУ 1", Status.NEW, LocalDateTime.of
                (2023,Month.APRIL,22,22,4), 10,"Подзадача 2 Эпик 1.", 5);
        fileBackedTasksManager.createSubtask(subtask9);
        Subtask subtask10 = new Subtask(10,"Подзадача 3 к ЭПИКУ 1", Status.NEW, LocalDateTime.of
                (2023,Month.APRIL,22,22,6), 10,"Подзадача 3 Эпик 1.", 5);
        fileBackedTasksManager.createSubtask(subtask10);

        System.out.println("---Добавляем подзадачи 1-2 к ЭПИКУ 2.");
        Subtask subtask11 = new Subtask(11,"Подзадача 1 к ЭПИКУ 2", Status.NEW, LocalDateTime.of
                (2023,Month.APRIL,22,22,9), 10,"Подзадача 1 Эпик 2.", 6);
        fileBackedTasksManager.createSubtask(subtask11);
        Subtask subtask12 = new Subtask(12,"Подзадача 2 к ЭПИКУ 2", Status.NEW, LocalDateTime.of
                (2023,Month.APRIL,22,22,8), 10,"Подзадача 2 Эпик 2.", 6);
        fileBackedTasksManager.createSubtask(subtask12);

        System.out.println("Вызываем задачи.");
        System.out.println();
        fileBackedTasksManager.getTask(1);
        fileBackedTasksManager.getTask(2);
        fileBackedTasksManager.getEpic(6);
        fileBackedTasksManager.getTask(1);
        fileBackedTasksManager.getEpic(5);
        fileBackedTasksManager.getSubtask(12);
        fileBackedTasksManager.getEpic(7);
        fileBackedTasksManager.getSubtask(8);
        fileBackedTasksManager.getTask(2);
        fileBackedTasksManager.getTask(4);
        fileBackedTasksManager.getSubtask(9);
        fileBackedTasksManager.getEpic(6);
        fileBackedTasksManager.getSubtask(8);
        fileBackedTasksManager.getSubtask(10);
        fileBackedTasksManager.getSubtask(11);

        System.out.println("Вызываем историю просмотров:");
        System.out.println("Должно быть: 1,5,12,7,2,4,9,6,8,10,11");
        System.out.println();
        System.out.println(fileBackedTasksManager.getHistory());

//////////////////////////////////////////////////////////////////////
        System.out.println();
        System.out.println("Создаем МЕНЕДЖЕР 2");
        System.out.println();
        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(new File("resources/historyManagerData.csv"));

        System.out.println("---ОБЩИЙ СПИСОК МЕНЕДЖЕРА 1:");
        System.out.println("---Список задач:");
        System.out.println(fileBackedTasksManager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(fileBackedTasksManager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(fileBackedTasksManager.getListSubtasks());
        System.out.println();

        System.out.println("---ОБЩИЙ СПИСОК МЕНЕДЖЕРА 2:");
        System.out.println("---Список задач:");
        System.out.println(fileBackedTasksManager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(fileBackedTasksManager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(fileBackedTasksManager.getListSubtasks());
        System.out.println();

        System.out.println("История просмотров МЕНЕДЖЕРА 2:");
        System.out.println();
        System.out.println(fileBackedTasksManager2.getHistory());

        System.out.println("Список задач/подзадач ПО ПРИОРИТЕТУ менеджера 1:");
        System.out.println(fileBackedTasksManager.getPrioritizedTasks());

        System.out.println("Список задач/подзадач ПО ПРИОРИТЕТУ менеджера 2:");
        System.out.println(fileBackedTasksManager2.getPrioritizedTasks());


/*


        System.out.println("Удаляем задачу 1, ЭПИК 2 и ЭПИК 3, подзадачи 2 и 3 ЭПИКА 1");
        fileBackedTasksManager.deleteTask(1);
        fileBackedTasksManager.deleteEpic(4);
        fileBackedTasksManager.deleteEpic(5);
        fileBackedTasksManager.deleteSubtask(7);
        fileBackedTasksManager.deleteSubtask(8);

        System.out.println("---Обновленный ОБЩИЙ СПИСОК:");
        System.out.println("---Список задач:");
        System.out.println(fileBackedTasksManager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(fileBackedTasksManager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(fileBackedTasksManager.getListSubtasks());
        System.out.println();

        System.out.println("Обновленная история просмотров:");
        System.out.println();
        System.out.println(fileBackedTasksManager.getHistory());

        System.out.println();
        System.out.println("Создаем МЕНЕДЖЕР 2");
        System.out.println();
        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(new File("resources/historyManagerData.csv"));

        System.out.println("---ОБЩИЙ СПИСОК МЕНЕДЖЕРА 2:");
        System.out.println("---Список задач:");
        System.out.println(fileBackedTasksManager.getListTasks());
        System.out.println("---Список ЭПИКОВ:");
        System.out.println(fileBackedTasksManager.getListEpics());
        System.out.println("---Список подзадач:");
        System.out.println(fileBackedTasksManager.getListSubtasks());
        System.out.println();

        System.out.println("История просмотров МЕНЕДЖЕРА 2:");
        System.out.println();
        System.out.println(fileBackedTasksManager2.getHistory());
        */
    }
}
