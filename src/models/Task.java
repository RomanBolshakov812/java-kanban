package models;

import file.TaskType;

public class Task {
    private int id;
    private String title;
    private String description;
    private Status status;
    private final TaskType taskType = TaskType.TASK;

    public Task(int id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    @Override
    public String toString() {
        return  getClass() + " {" +
                "Id: " + id + ". " +
                "Title: " + title + " " +
                "Description.length: " + description.length() + ". " +"Status: " + status + "." + "}" + '\n';
    }
}
