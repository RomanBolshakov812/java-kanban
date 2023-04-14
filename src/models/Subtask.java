package models;

import file.TaskType;

public class Subtask extends Task {
    private final int epicId;
    private final TaskType taskType = TaskType.SUBTASK;

    public Subtask(int id, String title, String description, Status status, int epicId) {
        super(id, title, description, status);
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
                "title: " + this.getTitle() + " " +
                "description.length: " + this.getDescription().length() + ". " +
                "status: " + this.getStatus() + ". " +
                "epicId: " + epicId + "}" + '\n';
    }
}
