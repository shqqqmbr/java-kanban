package main.managers;

import main.tasks.Task;

public class Node {
    public Node next;
    public Node prev;
    public Task task;

    public Node(Node prev,Task task, Node next){
        this.task = task;
        this.next = next;
        this.prev = prev;
    }
}
