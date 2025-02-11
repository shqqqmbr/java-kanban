package main.managers;


import main.tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    ArrayList<Task> historyList = new ArrayList<>();
    private final int max_size = 10;


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
    public ArrayList<Task> getHistory() {
        return historyList;
    }
    DADADAD

}
