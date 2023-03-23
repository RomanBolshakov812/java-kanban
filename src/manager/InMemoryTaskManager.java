package manager;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private Integer id = 0;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    @Override
    public ArrayList<Task> getListTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getListEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getListSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public void deleteAllTasks() {
        for (Task task : tasks.values()) {
            inMemoryHistoryManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        deleteAllSubtasks();
        for (Epic epic : epics.values()) {
                inMemoryHistoryManager.remove(epic.getId());
            }
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            for (int subtaskId : epic.getSubtasksId()) {
                inMemoryHistoryManager.remove(subtaskId);
            }
            epic.getSubtasksId().clear();
            epic.setStatus(Status.NEW);
        }
        subtasks.clear();
    }

    @Override
    public Task getTask(int id) {
        inMemoryHistoryManager.addHistory(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        inMemoryHistoryManager.addHistory(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        inMemoryHistoryManager.addHistory(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void createTask(Task task) {
        id++;
        task.setId(id);
        tasks.put(id, task);
    }

    @Override
    public void createEpic(Epic epic) {
        id++;
        epic.setId(id);
        epics.put(id, epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        id++;
        subtask.setId(id);
        epics.get(subtask.getEpicId()).setSubtasksId(id);
        subtasks.put(id, subtask);
        changeEpicStatus(epics.get(subtask.getEpicId()));
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        changeEpicStatus(epics.get(subtask.getEpicId()));
    }

    @Override
    public void changeEpicStatus(Epic epic) {
        int statusNew = 0;
        int statusDone = 0;

        for (int id : epic.getSubtasksId()) {
            Subtask subtask = subtasks.get(id);
            if (subtask.getStatus().equals(Status.NEW)) {
                statusNew++;
            } else if (subtask.getStatus().equals(Status.DONE)) {
                statusDone++;
            }
        }

       if (epic.getSubtasksId().size() == 0 || statusNew == epic.getSubtasksId().size()) {
           epic.setStatus(Status.NEW);
       } else if (statusDone == epic.getSubtasksId().size()) {
           epic.setStatus(Status.DONE);
       } else {
           epic.setStatus(Status.IN_PROGRESS);
       }
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        for (int subtaskId : epics.get(id).getSubtasksId()) {
            subtasks.remove(subtaskId);
            inMemoryHistoryManager.remove(subtaskId);
        }
        epics.remove(id);
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        epics.get(subtasks.get(id).getEpicId()).getSubtasksId().removeIf(p -> (p == id));
        changeEpicStatus(epics.get(subtasks.get(id).getEpicId()));
        subtasks.remove(id);
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public ArrayList<Subtask> getListSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> listSubtasksOfEpic = new ArrayList<>();
        for (int subtaskId : epics.get(epicId).getSubtasksId()) {
            if (subtasks.containsKey(subtaskId)) {
                listSubtasksOfEpic.add(subtasks.get(subtaskId));
                inMemoryHistoryManager.addHistory(subtasks.get(subtaskId));
            }
        }
        return listSubtasksOfEpic;
    }
}
