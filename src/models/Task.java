package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private long duration;
    private Status status;

    public Task(int id, String title, Status status, LocalDateTime startTime, long duration, String description) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public TaskType getTaskType() {
        return TaskType.TASK;
    }

    @Override
    public String toString() {
        return  getClass() + " {" +
                "Id: " + id + ". " +
                "Title: " + title + ". " +
                "Status: " + status + ". " +
                "Start: " + startTime + ". " +
                "End: " + getEndTime() + ". " +
                "Duration: " + duration + ". " +
                "Description.length: " + description.length() + ".}" + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id
                && duration == task.duration
                && Objects.equals(title, task.title)
                && Objects.equals(description, task.description)
                && Objects.equals(startTime, task.startTime)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, startTime, duration, status);
    }
}
