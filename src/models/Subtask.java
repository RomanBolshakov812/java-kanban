package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(int id, String title, Status status, LocalDateTime startTime, long duration, String description, int epicId) {
        super(id, title, status, startTime, duration, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return  getClass() + " {" +
                "id: " + this.getId() + ". " +
                "title: " + this.getTitle() + ". " +
                "status: " + this.getStatus() + ". " +
                "Start: " + this.getStartTime() + ". " +
                "End: " + getEndTime() + ". " +
                "Duration: " + this.getDuration() + ". " +
                "description.length: " + this.getDescription().length() + ". " +
                "epicId: " + epicId + "}" + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }
}
