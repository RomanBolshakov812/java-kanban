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

    public ArrayList<String> getListTasks() {
        ArrayList<String> listTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            listTasks.add(task.getTitle());
        }
        return listTasks;
    }

    public ArrayList<String> getListEpics() {
        ArrayList<String> listEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            listEpics.add(epic.getTitle());
        }
        return listEpics;
    }

    public ArrayList<String> getListSubtasks() {
        ArrayList<String> listSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            listSubtasks.add(subtask.getTitle());
        }
        return listSubtasks;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        for (int id : epics.keySet()) {
            deleteEpic(id);
        }
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.subtasksId.clear();
            epic.subtasksId.add(null);// НАДО УДАЛИТЬ ПЕРЕД РЕВЬЮ???
        }
        subtasks.clear();
    }

    public void deleteAll() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
        id = 0;
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

    public void createTask(String title, String description, String status) {
        id += 1;
        Task task = new Task(id,title, description, status);
        tasks.put(id, task);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {

        if (epic.subtasksId.size() != 0) {
            if (!epic.subtasksStatus.containsValue(NEW) & !epic.subtasksStatus.containsValue(IN_PROGRESS)) {
                epic.setStatus(DONE);
            } else if (epic.subtasksStatus.containsValue(IN_PROGRESS)) {
                epic.setStatus(IN_PROGRESS);
            }
            epics.put(epic.getId(), epic);
        } else {
            Task task = new Task(epic.getId(),epic.getTitle(), epic.getDescription(), NEW);
            tasks.put(epic.getId(), task);
            epics.remove(epic.getId());
        }
    }

    public void updateSubtask(Subtask subtask) {
        epics.get(subtask.epicId).subtasksStatus.put(subtask.getId(), subtask.getStatus());
        updateEpic(epics.get(subtask.epicId));
        subtasks.put(subtask.getId(), subtask);
    }

    public void addSubtask(String title, String description, String status, int targetId) {
        if (tasks.containsKey(targetId)) {
            addSubtaskToTask(title, description, status, tasks.get(targetId));
        } else {
            addSubtaskToEpic(title, description, status, epics.get(targetId));
        }
    }

    private void addSubtaskToTask(String title, String description, String status, Task task) {
        id += 1;
        Subtask subtask = new Subtask(id, title, description, status, task.getId());
        ArrayList<Integer> subtasksId = new ArrayList<>();
        HashMap<Integer,String> subtasksStatus = new HashMap<>();
        subtasksId.add(id);
        subtasksStatus.put(subtask.getId(), subtask.getStatus());
        Epic epic = new Epic(task.getId(), task.getTitle(),task.getDescription(),subtask.getStatus(),
                subtasksId, subtasksStatus);
        deleteTask(task.getId());
        subtasks.put(id, subtask);
        epics.put(task.getId(), epic);
    }

    private void addSubtaskToEpic(String title, String description, String status, Epic epic) {
        id += 1;
        Subtask subtask = new Subtask(id, title, description, status, epic.getId());
        subtasks.put(id, subtask);
        epic.subtasksId.add(id);
        epic.subtasksStatus.put(subtask.getId(), subtask.getStatus());
        updateEpic(epic);
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        for (int subtaskId : epics.get(id).subtasksId) {
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }

    public void deleteSubtask(int id) {
        epics.get(subtasks.get(id).epicId).subtasksId.removeIf(p -> p.equals(id));
        epics.get(subtasks.get(id).epicId).subtasksStatus.remove(id);
        updateEpic(epics.get(subtasks.get(id).epicId));
        subtasks.remove(id);
    }

    public ArrayList<String> getListSubtasksOfEpic(Epic epic) {
        ArrayList<String> listSubtasksOfEpic = new ArrayList<>();
        for (int subtaskId : epic.subtasksId) {
            if (subtasks.containsKey(subtaskId))
                listSubtasksOfEpic.add(subtasks.get(subtaskId).getTitle());
        }
        return listSubtasksOfEpic;
    }
}


