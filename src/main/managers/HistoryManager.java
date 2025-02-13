package main.managers;

import main.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HistoryManager {
    public void addTask(Task task);

    public ArrayList<Task> getHistory();

    public ArrayList<Task> getTasks();

    public void linkLast(Task task);

    public void removeNode(Node node);

    public Map<Integer, Node> getHistoryMap();
}
