package manager;

import models.Task;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private final List<Task> viewingHistory = new LinkedList<>();

    @Override
    public void addHistory(Task task) {
        if (viewingHistory.size() >= 10) {
            viewingHistory.remove(0);
        }
        viewingHistory.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return viewingHistory;
    }
}
