package manager;

import models.Epic;
import models.Subtask;
import models.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface TaskManager {

    ArrayList<Task> getListTasks();

    ArrayList<Epic> getListEpics();

    ArrayList<Subtask> getListSubtasks();

    //void addTaskByStartTime(Task task);

    public Set<Task> getPrioritizedTasks();

    List<Task> getHistory();

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    //boolean isTimeIntervalCheck(Task task);

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    /////////////////// ДОБАВЛЕНО 2 (!!!) МЕТОДА:    ///////////////////////////////
    //LocalDateTime getEpicStartTime (Epic epic);

    //long getEpicDuration(Epic epic);

    //void changeEpicStatus(Epic epic);

    //void setEpicStartTime(Epic epic);

    //void setEpicDuration(Epic epic);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    List<Subtask> getListSubtasksOfEpic(int epicId);
}
