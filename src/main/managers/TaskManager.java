package main.managers;

import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {
    public void addTask(Task task);

    public void addEpic(Epic epic);

    public void addSubtask(Subtask subtask);

    public void deleteTask(int taskId);

    public void deleteEpic(int epicId);

    public void deleteSubtask(int subtaskId);

    public void updateTask(Task task);

    public void updateEpic(Epic epic);

    public void updateSubtask(Subtask subtask);

    public void deleteAllTasks();

    public void deleteAllEpics();

    public void deleteAllSubtasks();

    public ArrayList<Task> getAllTasks();

    public ArrayList<Epic> getAllEpics();

    public ArrayList<Subtask> getAllSubtasks();

    public Task getTask(int taskId);

    public Epic getEpic(int epicId);

    public Subtask getSubtask(int subtaskId);

    public ArrayList<Subtask> getEpicsSubtasks(Epic epic);

    public List<? extends Task> getHistory();

    public TreeSet<Task> getPrioritizedTasks();
}
