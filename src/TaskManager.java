import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private int id = 1;

    public ArrayList<Task> getTasks(){
        return new ArrayList<>(tasks.values());
    }
    public ArrayList<Subtask> getSubtasks(){
        return new ArrayList<>(subtasks.values());
    }
    public ArrayList<Epic> getEpic(){
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getEpicsSubtasks(int epicId){
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> epicsSubtasks = new ArrayList<>();
        if (epics!=null){
            for (Integer subtaskId: epic.getAllTaskId()){
                epicsSubtasks.add(subtasks.get(subtaskId));
            }
        }
        return epicsSubtasks;
    }

    public void deleteTasks() {
        tasks.clear();
    }
    public void deleteEpics(){
        epics.clear();
        subtasks.clear();
    }

    public void deleteSubtasks(){
        subtasks.clear();
    }

    public Task getTask(int taskId){
        return tasks.get(taskId);
    }

    public Subtask getSubtask(int subtaskId){
        return subtasks.get(subtaskId);
    }

    public Epic getEpic(int epicId){
        return epics.get(epicId);
    }

    public void addTask(Task task){
        task.setId(id++);
        tasks.put(task.getId(), task);
    }

    public void addEpic(Epic epic){
        epic.setId(id++);
        epics.put(epic.getId(), epic);
    }
    public void addSubtask(Subtask subtask){
        subtask.setId(id++);
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic!=null){
            epic.getAllTaskId().add(subtask.getId());
        }
    }

    public void updateTask(Task task){
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic){
        epics.put(epic.getId(), epic);
    }
    public void updateEpicStatus(Epic epic){
        epic.updateStatus();
    }

    public void updateSubtask(Subtask subtask){
        subtasks.put(subtask.getId(), subtask);
    }

    public void deleteTask(int taskId){
        tasks.remove(taskId);
    }

    public void deleteEpic(int epicId){
        Epic epic = epics.remove(epicId);
        if(epic!=null){
            for (Integer subtaskId: epic.getAllTaskId()){
                subtasks.remove(subtaskId);
            }
        }
    }

    public void deleteSubtask(int subtaskId){
        Subtask subtask = subtasks.remove(subtaskId);
        if(subtask!=null){
            Epic epic = epics.get(subtask.getEpicId());
            if (epic!=null){
                epic.getAllTaskId().remove(subtaskId);
            }
        }
    }
}
