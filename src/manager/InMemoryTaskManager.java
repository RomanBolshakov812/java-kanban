package manager;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected Integer id = 0;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected Set<Task> tasksByStartTime = new TreeSet<Task>(new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2) {
            if (task2.getStartTime() == null) {
                return -1;
            } else {
                return task1.getStartTime().compareTo(task2.getStartTime());
            }
        }
    });

    protected HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

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
/*
    @Override
    public void addTaskByStartTime(Task task) {
        tasksByStartTime.add(task);
    }
*/
    @Override
    public Set<Task> getPrioritizedTasks() {
        return tasksByStartTime;
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
                tasksByStartTime.remove(subtasks.get(subtaskId));
            }
            epic.getSubtasksId().clear();
            epic.getSubtasksByStartTime().clear();
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
    public boolean isTimeIntervalCheck(Task newTask) {
        boolean isCheck = true;
        if (newTask.getStartTime() != null) {
            for (Task task : tasksByStartTime) {
                if (task.getStartTime() != null) {
                    if (newTask.getStartTime().isAfter(task.getStartTime()) &&
                            newTask.getStartTime().isBefore(task.getEndTime())) {
                        isCheck = false;
                    } else if (newTask.getEndTime().isAfter(task.getStartTime()) &&
                            newTask.getEndTime().isBefore(task.getEndTime())) {
                        isCheck = false;
                    } else if (newTask.getStartTime().isBefore(task.getStartTime()) &&
                            newTask.getEndTime().isAfter(task.getEndTime())) {
                        isCheck = false;
                    }
                }
            }
        }
        return isCheck;
    }

    @Override
    public void createTask(Task task) {
        if (isTimeIntervalCheck(task)) {
            id++;
            task.setId(id);
            tasks.put(id, task);
            tasksByStartTime.add(task);
        }
    }

    @Override
    public void createEpic(Epic epic) {
        id++;
        epic.setId(id);
        epics.put(id, epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        if (isTimeIntervalCheck(subtask)) {
            id++;
            subtask.setId(id);
            epics.get(subtask.getEpicId()).setSubtasksId(id);
            subtasks.put(id, subtask);
            changeEpicStatus(epics.get(subtask.getEpicId()));
            tasksByStartTime.add(subtask);
            epics.get(subtask.getEpicId()).setSubtasksByStartTime(subtask);
            setEpicStartTime(epics.get(subtask.getEpicId()));
            setEpicDuration(epics.get(subtask.getEpicId()));
        }
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.size() != 0) {
            if (isTimeIntervalCheck(task)) {
                tasksByStartTime.remove(tasks.get(task.getId()));
                tasks.put(task.getId(), task);
                tasksByStartTime.add(task);
            }
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.size() != 0) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.size() != 0) {
            if (isTimeIntervalCheck(subtask)) {
                tasksByStartTime.remove(subtasks.get(subtask.getId()));
                epics.get(subtask.getEpicId()).getSubtasksByStartTime().remove(subtask);
                subtasks.put(subtask.getId(), subtask);
                changeEpicStatus(epics.get(subtask.getEpicId()));
                tasksByStartTime.add(subtask);
                epics.get(subtask.getEpicId()).getSubtasksByStartTime().add(subtask);
                setEpicStartTime(epics.get(subtask.getEpicId()));
                setEpicDuration(epics.get(subtask.getEpicId()));
            }
        }
    }
/*
    @Override
    LocalDateTime getEpicStartTime(Epic epic) {

        return LocalDateTime.now();
    }
*/
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
    public void setEpicStartTime(Epic epic) {
        if (!epic.getSubtasksByStartTime().isEmpty()) {
            epic.setStartTime(epic.getSubtasksByStartTime().first().getStartTime());
        } else {
            epic.setStartTime(null);
        }
    }

    @Override
    public void setEpicDuration(Epic epic) {
        if (!epic.getSubtasksByStartTime().isEmpty()) {
            long duration = 0;
            for (Subtask subtask : epic.getSubtasksByStartTime()) {
                duration = duration + subtask.getDuration();
            }
            epic.setDuration(duration);
        } else {
            epic.setDuration(0);
        }
    }

    @Override
    public void deleteTask(int id) {
        tasksByStartTime.remove(tasks.get(id));
        tasks.remove(id);
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        for (int subtaskId : epics.get(id).getSubtasksId()) {
            tasksByStartTime.remove(subtasks.get(subtaskId));
            subtasks.remove(subtaskId);
            inMemoryHistoryManager.remove(subtaskId);
        }
        epics.remove(id);
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        tasksByStartTime.remove(subtasks.get(id));
        epics.get(subtasks.get(id).getEpicId()).getSubtasksByStartTime().remove(subtasks.get(id));
        epics.get(subtasks.get(id).getEpicId()).getSubtasksId().removeIf(p -> (p == id));
        changeEpicStatus(epics.get(subtasks.get(id).getEpicId()));
        subtasks.remove(id);
        inMemoryHistoryManager.remove(id);
        setEpicStartTime(epics.get(subtasks.get(id).getEpicId()));
        setEpicDuration(epics.get(subtasks.get(id).getEpicId()));
    }

    @Override
    public ArrayList<Subtask> getListSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> listSubtasksOfEpic = new ArrayList<>();
        for (int subtaskId : epics.get(epicId).getSubtasksId()) {
            if (subtasks.containsKey(subtaskId)) {
                listSubtasksOfEpic.add(subtasks.get(subtaskId));
            }
        }
        return listSubtasksOfEpic;
    }
}
