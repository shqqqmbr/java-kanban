package main.managers;

import java.util.*;

import main.tasks.*;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    protected int maxId = 0;

    HistoryManager manager = Managers.getDefaultHistoryManager();

    public InMemoryTaskManager() {
    }

    public boolean isTasksOverlapping(Task task1, Task task2) {
        return !task1.getEndTime().isBefore(task2.getStartTime()) &&
                !task1.getStartTime().isAfter(task2.getEndTime());
    }

    public int addId() {
        maxId++;
        return maxId;
    }

    public void addToSetTasks(Task task) {
        if (task.getStartTime() == null) {
            return;
        }
        prioritizedTasks.add(task);
    }

    public void updateTaskFromSetTasks(Task oldTask, Task newTask) {
        prioritizedTasks.remove(oldTask);
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
        boolean isIntersect = prioritizedTasks.stream()
                .anyMatch(task1 -> isTasksOverlapping(task, task1));
        if (isIntersect) {
            throw new IllegalArgumentException(task.getName() + " пересекается с другой задачей");
        }
        task.setId(addId());
        tasks.put(task.getId(), task);
        addToSetTasks(task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(addId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        boolean isIntersect = prioritizedTasks.stream()
                .anyMatch(subtask1 -> isTasksOverlapping(subtask, subtask1));
        if (isIntersect) {
            throw new IllegalArgumentException(subtask.getName() + " пересекается с другой задачей");
        }
        subtask.setId(addId());
        Epic epic = epics.get(subtask.getEpicId());
        subtasks.put(subtask.getId(), subtask);
        addToSetTasks(subtask);
        epic.addSubtask(subtask);
        epic.updateEpicStatus();
        epic.updateEpicTime();
    }

    @Override
    public void deleteTask(int taskId) {
        Task task = tasks.get(taskId);
        tasks.remove(taskId);
        manager.remove(taskId);
        prioritizedTasks.remove(task);
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
        Subtask subtask = subtasks.get(subtaskId);
        Epic epic = epics.get(subtasks.get(subtaskId).getEpicId());
        epic.getAllSubtasks().remove(subtasks.get(subtaskId));
        subtasks.remove(subtaskId);
        epic.updateEpicStatus();
        manager.remove(subtaskId);
        epic.updateEpicTime();
        prioritizedTasks.remove(subtask);
    }

    @Override
    public void updateTask(Task task) throws IllegalStateException { //имея одинаковый id старая задача заменится новой
        Task oldTask = tasks.get(task.getId());
        tasks.remove(oldTask);
        tasks.put(task.getId(), task);
        updateTaskFromSetTasks(oldTask, task);
    }

    @Override
    public void updateSubtask(Subtask subtask) throws IllegalStateException {
        Subtask oldSubtask = subtasks.get(subtask.getId());
        Epic epic = epics.get(subtask.getEpicId());
        epic.deleteFromEpicSubtasks(oldSubtask);
        epic.addSubtask(subtask);
        subtasks.remove(oldSubtask);
        subtasks.put(subtask.getId(), subtask);
        updateTaskFromSetTasks(oldSubtask, subtask);
    }

    @Override
    public void updateEpic(Epic epic) throws IllegalStateException {
        Epic oldEpic = epics.get(epic.getId());
        epics.remove(oldEpic);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();//исправлено
    }

    @Override
    public void deleteAllSubtasks() {
        epics.values().stream()
                .forEach(epic -> {
                    epic.getAllSubtasks().clear();
                    epic.updateEpicStatus();
                });
        subtasks.clear();
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

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }
}
