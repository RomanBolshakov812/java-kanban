package models;

import file.TaskType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;

public class Epic extends Task {

    private final ArrayList<Integer> subtasksId = new ArrayList<>();
    private final TreeSet<Subtask> subtasksByStartTime = new TreeSet<>((subtask1, subtask2) -> {
        if (subtask1.getStartTime() == null && subtask2.getStartTime() == null) {
            return 1;
        } else if (subtask1.getStartTime() == null) {
            return 1;
        } else if (subtask2.getStartTime() == null) {
            return -1;
        } else {
            return subtask1.getStartTime().compareTo(subtask2.getStartTime());
        }
    });

    private final TaskType taskType = TaskType.EPIC;

    public Epic(int id, String title, Status status, LocalDateTime startTime, long duration, String description) {
        super(id, title,  status, startTime, duration, description);
    }

    public void setSubtasksId(int id) {
        subtasksId.add(id);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setSubtasksByStartTime(Subtask subtask) {
        subtasksByStartTime.add(subtask);
    }

    public TreeSet<Subtask> getSubtasksByStartTime() {
        return subtasksByStartTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return  subtasksByStartTime.last().getEndTime();
    }

    @Override
    public String toString() {
        return  getClass() + " {" +
                "id: " + this.getId() + ". " +
                "title: " + this.getTitle() + " " +
                "status: " + this.getStatus() + "." +
                "Start: " + this.getStartTime() + ". " +
                "Duration: " + this.getDuration() + ". " +
                "description.length: " + this.getDescription().length() + ". " +
                "subtasksId: " + subtasksId + "}" + '\n';
    }
}
