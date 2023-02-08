package models;

import manager.InMemoryTaskManager;

public class Task {
    private int id;
    private String title;
    private String description;
    private InMemoryTaskManager.Status status;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
         status = InMemoryTaskManager.Status.NEW;
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

    public InMemoryTaskManager.Status getStatus() {
        return status;
    }

    public void setStatus(InMemoryTaskManager.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  getClass() + " {" +
                "Id: " + id + ". " +
                "Title: " + title + " " +
                "Description.length: " + description.length() + ". " +
                "Status: " + status + "." + "}" + '\n';
    }
}
