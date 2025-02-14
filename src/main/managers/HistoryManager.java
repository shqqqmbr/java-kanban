package main.managers;

import main.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface HistoryManager {
    public void addTask(Task task);

    public List<Task> getHistory();

    public void remove(int id);

    public Map<Integer, Node> getHistoryMap();

    public ArrayList<Task> getTasks();
}
