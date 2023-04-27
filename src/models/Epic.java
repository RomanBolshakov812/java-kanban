package models;

import file.TaskType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Epic extends Task {

    //private LocalDateTime endTime;
    private final ArrayList<Integer> subtasksId = new ArrayList<>();
    private final TreeSet<Subtask> subtasksByStartTime = new TreeSet<>(new Comparator<Subtask>() {
        @Override
        public int compare(Subtask subtask1, Subtask subtask2) {
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
