import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    final String NEW = "NEW";
    final String  IN_PROGRESS = "IN_PROGRESS";
    final String  DONE = "DONE";
    private Integer id = 0;
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public ArrayList<Task> getListTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getListEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getListSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtasksId().clear();
            epic.setStatus(NEW);
        }
        subtasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public void createTask(Task task) {
        id++;
        task.setId(id);
        tasks.put(id, task);
    }

    public void createEpic(Epic epic) {
        id++;
        epic.setId(id);
        epics.put(id, epic);
    }

    public void createSubtask(Subtask subtask) {
        id++;
        subtask.setId(id);
        epics.get(subtask.getEpicId()).setSubtasksId(id);
        subtasks.put(id, subtask);
        changeEpicStatus(epics.get(subtask.getEpicId()));
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        changeEpicStatus(epics.get(subtask.getEpicId()));
    }

    public void changeEpicStatus(Epic epic) {
        int count = 0;
        int statusNew = 0;
        int statusDone = 0;

       for (Subtask subtask : subtasks.values()) {
           if (epic.getSubtasksId().contains(subtask.getId())) {
               count++;
               if (subtask.getStatus().equals(NEW)) {
                   statusNew++;
               } else if (subtask.getStatus().equals(DONE)) {
                   statusDone++;
               }
           }
       }

       if (epic.getSubtasksId().size() == 0 || statusNew == count) {
           epic.setStatus(NEW);
       } else if (statusDone == count) {
           epic.setStatus(DONE);
       } else {
           epic.setStatus(IN_PROGRESS);
       }
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        for (int subtaskId : epics.get(id).getSubtasksId()) {
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }

    public void deleteSubtask(int id) {
        epics.get(subtasks.get(id).getEpicId()).getSubtasksId().removeIf(p -> (p == id));
        changeEpicStatus(epics.get(subtasks.get(id).getEpicId()));
        subtasks.remove(id);
    }

    public ArrayList<Subtask> getListSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> listSubtasksOfEpic = new ArrayList<>();
        for (int subtaskId : epics.get(epicId).getSubtasksId()) {
            if (subtasks.containsKey(subtaskId))
                listSubtasksOfEpic.add(subtasks.get(subtaskId));
        }
        return listSubtasksOfEpic;
    }
}


