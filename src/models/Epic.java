package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private LocalDateTime endTime;
    private final ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(int id, String title, Status status, LocalDateTime startTime,
                long duration, String description, LocalDateTime endTime) {
        super(id, title,  status, startTime, duration, description);
        this.endTime = endTime;
    }

    public void setSubtasksId(int id) {
        subtasksId.add(id);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    public void  setEndTime(LocalDateTime time) {
        endTime = time;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return  getClass() + " {" +
                "id: " + this.getId() + ". " +
                "title: " + this.getTitle() + " " +
                "status: " + this.getStatus() + "." +
                "Start: " + this.getStartTime() + ". " +
                "End: " + endTime + ". " +
                "Duration: " + this.getDuration() + ". " +
                "description.length: " + this.getDescription().length() + ". " +
                "subtasksId: " + subtasksId + "}" + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(endTime, epic.endTime) && Objects.equals(subtasksId, epic.subtasksId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), endTime, subtasksId);
    }
}
