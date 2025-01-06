import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private int id = 0;

    public void addTask(Task task) {
        task.setId(id++);
        tasks.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epic.setId(id++);
        epics.put(epic.getId(), epic);
    }

    public void addSubtask(Subtask subtask) {
        subtask.setId(id++);
        subtasks.put(subtask.getEpicId(), subtask);
    }

    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    public void deleteEpic(int epicId) {
        Epic epic = epics.get(epicId);
        epic.getAllSubtasks().clear();
        epics.remove(epicId);
    }

    public void deleteSubtask(int subtaskId) {
        Epic epic = epics.get(subtaskId);
        epic.getAllSubtasks().remove(subtasks.get(subtaskId));
        subtasks.remove(subtaskId);
        epic.updateEpicStatus();
    }

    public void updateTask(Task task, Task newTask) {
        tasks.put(task.getId(), newTask);
    }

    public void updateEpic(Epic epic, Epic newEpic) {
        epics.put(epic.getId(), newEpic);
        epic.getAllSubtasks().clear();
    }

    public void updateSubtask(Subtask subtask, Subtask newSubtask) {
        subtasks.put(subtask.getId(), newSubtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getAllSubtasks().remove(subtask);
        epic.getAllSubtasks().add(newSubtask);
        epics.put(epic.getId(), epic);
        epic.updateEpicStatus();
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public void deleteAllSubtasks(int subtaskId) {
        for (Epic epic : epics.values()) {
            epic.getAllSubtasks().clear();
            epic.updateEpicStatus();
        }
        subtasks.clear();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Subtask> getEpicsSubtasks(Epic epic) {
        return epic.getAllSubtasks();
    }

    public Task getTask(int taskId) {
        return tasks.get(taskId);
    }

    public Epic getEpic(int epicId) {
        return epics.get(epicId);
    }

    public Subtask getSubtask(int subtaskId) {
        return subtasks.get(subtaskId);
    }
}
