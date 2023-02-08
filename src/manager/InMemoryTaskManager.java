package manager;

import models.Epic;
import models.Subtask;
import models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    public enum Status {
        NEW,
        IN_PROGRESS,
        DONE
    }
    Status NEW = Status.NEW;
    Status IN_PROGRESS = Status.IN_PROGRESS;
    Status DONE = Status.DONE;
    private Integer id = 0;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected List<Task> viewingHistory = new ArrayList<>();

    public ArrayList<Task> getListTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getListEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getListSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtasksId().clear();
            epic.setStatus(NEW);
        }
        subtasks.clear();
    }

    @Override
    public Task getTask(int id) {
        historyUpdate(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyUpdate(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyUpdate(subtasks.get(id));
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
            if (subtask.getStatus().equals(NEW)) {
                statusNew++;
            } else if (subtask.getStatus().equals(DONE)) {
                statusDone++;
            }
        }

       if (epic.getSubtasksId().size() == 0 || statusNew == epic.getSubtasksId().size()) {
           epic.setStatus(NEW);
       } else if (statusDone == epic.getSubtasksId().size()) {
           epic.setStatus(DONE);
       } else {
           epic.setStatus(IN_PROGRESS);
       }
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        for (int subtaskId : epics.get(id).getSubtasksId()) {
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        epics.get(subtasks.get(id).getEpicId()).getSubtasksId().removeIf(p -> (p == id));
        changeEpicStatus(epics.get(subtasks.get(id).getEpicId()));
        subtasks.remove(id);
    }

    @Override
    public ArrayList<Subtask> getListSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> listSubtasksOfEpic = new ArrayList<>();
        for (int subtaskId : epics.get(epicId).getSubtasksId()) {
            if (subtasks.containsKey(subtaskId)) {
                listSubtasksOfEpic.add(subtasks.get(subtaskId));
                historyUpdate(subtasks.get(subtaskId));
            }
        }
        return listSubtasksOfEpic;
    }

    @Override
    public void historyUpdate(Task task) {
        if (viewingHistory.size() >= 10) {
            viewingHistory.remove(0);
        }
        viewingHistory.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return viewingHistory;
    }
}