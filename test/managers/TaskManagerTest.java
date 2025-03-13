package managers;

import main.managers.TaskManager;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;

    protected abstract T createTaskManager();

    @BeforeEach
    public void beforeEach() {
        manager = createTaskManager();
    }

    @Test
    public void addTask() {
    }

    @Test
    public void addEpic() {
    }

    @Test
    public void addSubtask() {
    }

    @Test
    public void deleteTask() {

    }

    @Test
    public void deleteEpic() {

    }

    @Test
    public void deleteSubtask() {

    }

    @Test
    public void updateTask() {

    }

    @Test
    public void updateEpic() {
    }

    @Test
    public void updateSubtask() {

    }

    @Test
    public void deleteAllTasks() {

    }

    @Test
    public void deleteAllEpics() {
//исправлено
    }

    @Test
    public void deleteAllSubtasks() {

    }

    @Test
    public void getAllTasks() {

    }

    @Test
    public void getAllEpics() {

    }

    @Test
    public void getAllSubtasks() {

    }

    @Test
    public void getEpicsSubtasks() {

    }

    @Test
    public void getTask() {

    }

    @Test
    public void getEpic() {

    }

    @Test
    public void getSubtask() {

    }

    @Test
    public void getHistory() {

    }

}
