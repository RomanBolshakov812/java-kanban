package manager;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected Integer id = 0;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final Set<Task> tasksByStartTime = new TreeSet<>(Comparator.comparing(Task::getStartTime));
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
            tasksByStartTime.remove(task);
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
            epic.setStatus(Status.NEW);
            epic.setStartTime(null);
            epic.setDuration(0);
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

    protected boolean isTimeIntervalCheck(Task newTask) {
        boolean isCheck = true;
        for (Task task : tasksByStartTime) {
            if (!newTask.getStartTime().isAfter(task.getEndTime())
                    && !newTask.getEndTime().isBefore(task.getStartTime())) {
                isCheck = false;
            }
        }
        return isCheck;
    }

    @Override
    public void createTask(Task task) {
        id++;
        task.setId(id);
        tasks.put(id, task);
        if (isTimeIntervalCheck(task)) {
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
        id++;
        subtask.setId(id);
        epics.get(subtask.getEpicId()).setSubtasksId(id);
        subtasks.put(id, subtask);
        changeEpicStatus(epics.get(subtask.getEpicId()));
        if (isTimeIntervalCheck(subtask)) {
            tasksByStartTime.add(subtask);
        }
        setIntervalTimeEpic(epics.get(subtask.getEpicId()));
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.size() != 0 && tasks.containsKey(task.getId())) {
            if (tasksByStartTime.contains(task)) {
                tasksByStartTime.remove(tasks.get(task.getId()));
            }
            tasks.put(task.getId(), task);
            if (isTimeIntervalCheck(task)) {
                tasksByStartTime.add(task);
            }
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.size() != 0 && epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.size() != 0 && subtasks.containsKey(subtask.getId())) {
            tasksByStartTime.remove(subtasks.get(subtask.getId()));
            subtasks.put(subtask.getId(), subtask);
            changeEpicStatus(epics.get(subtask.getEpicId()));
            if (isTimeIntervalCheck(subtask)) {
                tasksByStartTime.add(subtask);
            }
            setIntervalTimeEpic(epics.get(subtask.getEpicId()));
        }
    }

    private void changeEpicStatus(Epic epic) {
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

    private void setIntervalTimeEpic(Epic epic) {
        long duration = 0;
        if (!epic.getSubtasksId().isEmpty()) {
            LocalDateTime startTime;
            LocalDateTime endTime;
            for (Integer subtaskId : epic.getSubtasksId()) {
                startTime = subtasks.get(subtaskId).getStartTime();
                endTime = subtasks.get(subtaskId).getEndTime();
                if (epic.getStartTime() == null
                        || startTime.isBefore(epic.getStartTime())) {
                    epic.setStartTime(startTime);
                }
                if (epic.getEndTime() == null
                        || endTime.isAfter(epic.getEndTime())) {
                    epic.setEndTime(endTime);
                }
                duration = duration + subtasks.get(subtaskId).getDuration();
            }
            epic.setDuration(duration);
        } else {
            epic.setStartTime(null);
            epic.setEndTime(null);
            epic.setDuration(duration);
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
        epics.get(subtasks.get(id).getEpicId()).getSubtasksId().removeIf(p -> (p == id));
        changeEpicStatus(epics.get(subtasks.get(id).getEpicId()));
        Epic currentEpic = epics.get(subtasks.get(id).getEpicId());
        subtasks.remove(id);
        inMemoryHistoryManager.remove(id);
        setIntervalTimeEpic(currentEpic);
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
