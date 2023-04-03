package manager;

import models.Task;

public class Node {
    public Task task;
    public Node next;
    public Node prev;

    public Node(Node prev, Task task, Node next) {
        this.task = task;
        this.next = next;
        this.prev = prev;
    }
}
// По идее ссылка next у новой ноды всегда должна быть null. Я так и не нашел правильное решение -
// сделать так как есть сейчас или оставить: this.next = null.
// В любом случае она будет null.

