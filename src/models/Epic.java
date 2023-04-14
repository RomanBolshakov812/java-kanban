package models;

import file.TaskType;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subtasksId = new ArrayList<>();
    private final TaskType taskType = TaskType.EPIC;

    public Epic(int id, String title, String description, Status status) {
        super(id, title, description, status);
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

    @Override
    public String toString() {
        return  getClass() + " {" +
                "id: " + this.getId() + ". " +
                "title: " + this.getTitle() + " " +
                "description.length: " + this.getDescription().length() + ". " +
                "status: " + this.getStatus() + "." +
                "subtasksId: " + subtasksId + "}" + '\n';
    }
}
