package main.managers;

import main.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HistoryManager {
    public void addTask(Task task);

    public List<Task> getHistory();

    public ArrayList<Task> getTasks();
}
