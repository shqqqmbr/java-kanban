package main.managers;


import main.tasks.Task;


import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    Map<Integer, Node> historyMap = new HashMap();
    private Node head;
    private Node tail;

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node node = head;
        while (node != null) {
            tasks.add(node.task);
            node = node.next;
        }
        return tasks;
    }

    @Override
    public void linkLast(Task task) {
        final Node oldTail = tail;
        final Node newNode = new Node(tail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        historyMap.put(task.getId(), newNode);

    }

    @Override
    public void removeNode(Node node) {
        if (node == null) {
            return;
        }
        if (node.next == null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        if (node.prev == null) {
            node.prev.next = node.prev;
        } else {
            head = node.next;
        }
    }

//    @Override
//    public void add(Task task) {
//        final Node oldTail = tail;
//        final Node newNode = new Node(tail, task, null);
//        tail = newNode;
//        if (oldTail == null) {
//            head = newNode;
//        } else {
//            oldTail.next = newNode;
//        }
//    }

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> history = new ArrayList<>();
        for (Node value: historyMap.values()){
            history.add(value.task);
        }
        return history;
    }

//    @Override
//    public void remove(int id) {
//        historyMap.remove(id);
//    }
}
