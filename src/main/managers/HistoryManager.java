package main.managers;

import main.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface HistoryManager {
//    public void add(Task task);

    public ArrayList<Task> getHistory();

//    public void remove(int id);

    public ArrayList<Task> getTasks();

    public void linkLast(Task task);

    public void removeNode(Node node);
}
