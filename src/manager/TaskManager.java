package manager;

import models.Epic;
import models.Subtask;
import models.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface TaskManager {

    ArrayList<Task> getListTasks();

    ArrayList<Epic> getListEpics();

    ArrayList<Subtask> getListSubtasks();

    public Set<Task> getPrioritizedTasks();

    List<Task> getHistory();

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    List<Subtask> getListSubtasksOfEpic(int epicId);
}
