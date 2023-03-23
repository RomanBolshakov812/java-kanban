package manager;

import models.Task;

public class Node {
    public Task task;
    public Node next;
    public Node prev;

    public Node(Node prev, Task task, Node next) {
        this.task = task;
        this.next = null;
        this.prev = null;
    }
}

