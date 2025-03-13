package managers;

import main.constants.Status;
import main.managers.*;
import main.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class InMemoryHistoryManagerTest{
    InMemoryHistoryManager manager;
    TaskManager taskManager;
    protected Task task1;
    protected Task task2;
    protected Task task3;


    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryHistoryManager();
        taskManager = Managers.getDefaultTaskManager();
        task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        task2 = new Task("Задача 2", "Описание задачи 2", Status.DONE);
        task3 = new Task("Задача 3", "Описание задачи 3", Status.IN_PROGRESS);

        task1.setId(0);
        task2.setId(1);
        task3.setId(2);
    }

    @Test
    public void shouldLastVersionTaskSaveInHistoryManager() {
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        Assertions.assertNotNull(manager.getHistory());
    }

    @Test
    public void linkLastTest() {
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        Map<Integer, Node> history = manager.getHistoryMap();
        Assertions.assertNull(history.get(task3.getId()).next);
        Assertions.assertNotNull(history.get(task3.getId()).prev);
    }

    @Test
    public void removeNodeTest() {
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        Map<Integer, Node> history = manager.getHistoryMap();
        Assertions.assertTrue(history.containsKey(task1.getId()));
        Assertions.assertTrue(history.containsKey(task2.getId()));
        Assertions.assertTrue(history.containsKey(task3.getId()));
        manager.remove(task2.getId());
        Assertions.assertFalse(history.containsKey(task2.getId()));
        Assertions.assertEquals(history.get(task1.getId()).next, history.get(task3.getId()));
        Assertions.assertEquals(history.get(task1.getId()).next, history.get(task3.getId()));
        Assertions.assertEquals(history.get(task3.getId()).prev, history.get(task1.getId()));
    }

    @Test
    public void addTaskTest() {
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        List<Task> history = manager.getHistory();
        Assertions.assertEquals(history.size(), 3);
        Assertions.assertEquals(task1, history.get(0));
        Assertions.assertEquals(task2, history.get(1));
        Assertions.assertEquals(task3, history.get(2));
    }

}
