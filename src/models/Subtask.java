package models;

import file.TaskType;

import java.time.LocalDateTime;

public class Subtask extends Task {
    private final int epicId;
    private final TaskType taskType = TaskType.SUBTASK;

    public Subtask(int id, String title, Status status, LocalDateTime startTime, long duration, String description, int epicId) {
        super(id, title, status, startTime, duration, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    @Override
    public String toString() {
        return  getClass() + " {" +
                "id: " + this.getId() + ". " +
                "title: " + this.getTitle() + ". " +
                "status: " + this.getStatus() + ". " +
                "Start: " + this.getStartTime() + ". " +
                "Duration: " + this.getDuration() + ". " +
                "description.length: " + this.getDescription().length() + ". " +
                "epicId: " + epicId + "}" + '\n';
    }
}
