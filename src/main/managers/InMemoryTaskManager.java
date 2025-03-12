package main.managers;

import java.util.*;

import main.tasks.*;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    private int maxId = 0;

    HistoryManager manager = Managers.getDefaultHistoryManager();

    public InMemoryTaskManager() {
    }

    public boolean isTasksIntersect(Task task1, Task task2) {
        return !task1.getEndTime().isBefore(task2.getStartTime()) &&
                !task1.getStartTime().isAfter(task2.getEndTime());
    }

    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public void addToSetTasks(Task task) {
        if (task.getStartTime() == null) {
            return;
        }
        prioritizedTasks.add(task);
    }

    public void deleteFromSetTasks(Task task) {
        prioritizedTasks.remove(task);
    }

    public void updateTaskFromSetTasks(Task task, Task newTask) {
        deleteFromSetTasks(task);
        addToSetTasks(newTask);
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public int filesMaxId() {
        Set<Integer> allTasksId = new HashSet<>();
        allTasksId.addAll(tasks.keySet());
        allTasksId.addAll(epics.keySet());
        allTasksId.addAll(subtasks.keySet());
        return Collections.max(allTasksId);
    }

    @Override
    public void addTask(Task task) {
        task.setId(maxId++);
        tasks.put(task.getId(), task);
        boolean isIntersect = prioritizedTasks.stream()
                        .anyMatch(task1 -> isTasksIntersect(task, task1));
        if (isIntersect){
            throw new ArithmeticException("Tasks intersect on a time line.");
        }
        addToSetTasks(task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(maxId++);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(maxId++);
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtask(subtask);
        epic.updateEpicStatus();
        boolean isIntersect = prioritizedTasks.stream()
                .anyMatch(subtask1 -> isTasksIntersect(subtask, subtask1));
        if (isIntersect){
            throw new ArithmeticException("Tasks intersect on a time line.");
        }
        addToSetTasks(subtask);
    }

    @Override
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
        manager.remove(taskId);
        deleteFromSetTasks(tasks.get(taskId));
    }

    @Override
    public void deleteEpic(int epicId) {
        Epic epic = epics.get(epicId);
        epic.getAllSubtasks().clear();
        epics.remove(epicId);
        manager.remove(epicId);
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        Epic epic = epics.get(subtaskId);
        epic.getAllSubtasks().remove(subtasks.get(subtaskId));
        subtasks.remove(subtaskId);
        epic.updateEpicStatus();
        manager.remove(subtaskId);
        deleteFromSetTasks(subtasks.get(subtaskId));
    }

    @Override
    public void updateTask(Task task, Task newTask) {
        tasks.put(task.getId(), newTask);
        updateTaskFromSetTasks(task, newTask);
    }

    @Override
    public void updateEpic(Epic epic, Epic newEpic) {
        epics.put(epic.getId(), newEpic);
        newEpic.updateEpicStatus();//исправлено
    }

    @Override
    public void updateSubtask(Subtask subtask, Subtask newSubtask) {
        newSubtask.setId(subtask.getId());
        subtasks.remove(subtask.getEpicId());
        subtasks.put(newSubtask.getId(), newSubtask);
        Epic epic = epics.get(newSubtask.getEpicId());
        epic.getAllSubtasks().remove(subtask);
        epic.addSubtask(newSubtask);
        epic.updateEpicStatus();
        updateTaskFromSetTasks(subtask, newSubtask);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        prioritizedTasks.stream()
                .filter(task -> task.getClass() == Task.class)
                .peek(task -> prioritizedTasks.remove(task));
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();//исправлено
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getAllSubtasks().clear();
            epic.updateEpicStatus();
        }
        subtasks.clear();
        prioritizedTasks.stream()
                .filter(task -> task.getClass() == Subtask.class)
                .peek(task -> prioritizedTasks.remove(task));
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Subtask> getEpicsSubtasks(Epic epic) {
        return epic.getAllSubtasks();
    }

    @Override
    public Task getTask(int taskId) {
        manager.add(tasks.get(taskId));
        return tasks.get(taskId);
    }

    @Override
    public Epic getEpic(int epicId) {
        manager.add(epics.get(epicId));
        return epics.get(epicId);
    }

    @Override
    public Subtask getSubtask(int subtaskId) {
        manager.add(subtasks.get(subtaskId));
        return subtasks.get(subtaskId);
    }

    @Override
    public List<? extends Task> getHistory() {
        return manager.getHistory();
    }

}
