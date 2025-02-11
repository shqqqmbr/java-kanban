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
        while (node!=null){
            tasks.add(node.task);
            node = node.next;
        }
        return tasks;
    }

    @Override
    public void linkLast(Task task) {
        Node node = new Node(task, null,null);

    }

    @Override
    public void removeNode(Node node){

    }

    @Override
    public void add(Task task) {
        if (Objects.isNull(task)) {
            return;
        }
        historyList.add(task);
        if (historyList.size() > max_size) {
            historyList.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }

    @Override
    public void remove(int id){
        historyList.remove(id);
    }
}
