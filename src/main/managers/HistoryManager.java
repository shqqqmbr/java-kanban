package main.managers;

import main.tasks.Task;

import java.util.ArrayList;

public interface HistoryManager {
    public void add(Task task);

    public ArrayList<Task> getHistory();
}
