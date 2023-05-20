package util;

import models.TaskType;
import manager.HistoryManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class ConversionsUtility {

    public static String toFileString(Task task) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(task.getId()).append(",").
                append(task.getTaskType()).append(",").
                append(task.getTitle()).append(",").
                append(task.getStatus()).append(",").
                append(task.getStartTime()).append(",").
                append(task.getDuration()).append(",").
                append(task.getDescription()).append(",");

        if (task.getTaskType() == TaskType.SUBTASK) {
            stringBuilder.append(((Subtask) task).getEpicId()).append('\n');
        } else if (task.getTaskType() == TaskType.EPIC) {
            stringBuilder.append(((Epic) task).getEndTime()).append('\n');
        } else {
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public static String historyToFileString(HistoryManager manager) {
        StringBuilder historyBuilder = new StringBuilder();
        for (Task task : manager.getHistory()) {
            historyBuilder.append(task.getId()).append(",");
        }
        return historyBuilder.toString();
    }

    public static Task taskFromFileString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        TaskType type = TaskType.valueOf(parts[1]);
        String title = parts[2];
        Status status = Status.valueOf(parts[3]);
        LocalDateTime startTime;
        if (parts[4].equals("null")) {
            startTime = null;
        } else {
            startTime = LocalDateTime.parse(parts[4]);
        }
        long duration = Long.parseLong(parts[5]);
        String description = parts[6];

        switch (type) {
            case TASK:
                return new Task(id, title, status, startTime, duration, description);
            case EPIC:
                LocalDateTime endTime = LocalDateTime.parse(parts[7]);
                return new Epic(id, title, status, startTime, duration, description, endTime);
            case SUBTASK:
                int epicId = Integer.parseInt(parts[7]);
                return new Subtask(id, title, status, startTime, duration, description, epicId);
            default:
                break;
        }
        return null;
    }

    public static List<Integer> historyFromIdString(String value) {

        List<Integer> history = new ArrayList<>();
        String[] parts = value.split(",");
        for (String part : parts) {
            history.add(Integer.parseInt(part));
        }

        return history;
    }
}
