package manager;

import models.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    public Node first;
    public Node last;
    private int size = 0;
    public ArrayList<Task> tasks = new ArrayList<>();
    private final HashMap<Integer, Node> idNode = new HashMap<>();

    public Node linkLast(Task task) {
        final Node l = last;
        final Node newNode = new Node(null, task, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
            newNode.prev = l;
        }
        size++;
        return newNode;
    }

    public List<Task> getTasks() {
        for (Node x = first; x != null; x = x.next) {
            tasks.add(x.task);
        }
        return tasks;
    }

    public void removeNode(Node n) {
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

    @Override
    public void addHistory(Task task) {

        if (idNode.get(task.getId()) != null) {
            remove(task.getId());
        }
        idNode.put(task.getId(), linkLast(task));
    }

    @Override
    public void remove(int id) {
        removeNode(idNode.get(id));
        idNode.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> viewingHistory = new ArrayList<>();
        for (Node x = first; x != null; x = x.next) {
            viewingHistory.add(x.task);
        }
        return viewingHistory;
    }
}
