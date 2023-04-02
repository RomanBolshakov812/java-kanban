package manager;

import models.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    public Node first;
    public Node last;
    private int size = 0;
    private final HashMap<Integer, Node> idNode = new HashMap<>();

    private Node linkLast(Task task) {
        final Node l = last;
        final Node newNode = new Node(l, task, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        return newNode;
    }

    private List<Task> getTasks() {
        ArrayList<Task> viewingHistory = new ArrayList<>();
        for (Node current = first; current != null; current = current.next) {
            viewingHistory.add(current.task);
        }
        return viewingHistory;
    }

    private void removeNode(Node n) {
        if (n != null) {
            Node prev = n.prev;
            Node next = n.next;
            if (size != 1) {
                if (prev != null && next != null) {
                    prev.next = next;
                    next.prev = prev;
                } else if (next != null) {
                    next.prev = null;
                    first = next;
                } else if (prev != null) {
                    prev.next = null;
                    last = prev;
                }
            } else {
                first = null;
                last = null;
            }
            size--;
        }
    }

    @Override
    public void addHistory(Task task) {

        if (task != null) {
            remove(task.getId());
            idNode.put(task.getId(), linkLast(task));
        }
    }

    @Override
    public void remove(int id) {
        removeNode(idNode.get(id));
        idNode.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}
