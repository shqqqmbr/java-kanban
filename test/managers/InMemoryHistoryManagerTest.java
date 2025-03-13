package managers;

import main.constants.Status;
import main.managers.*;
import main.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class InMemoryHistoryManagerTest {
    InMemoryHistoryManager historyManager;
    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    public void beforeEach() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        task2 = new Task("Задача 2", "Описание задачи 2", Status.DONE);
        task3 = new Task("Задача 3", "Описание задачи 3", Status.IN_PROGRESS);

        task1.setId(0);
        task2.setId(1);
        task3.setId(2);
    }

    @Test
    public void shouldLastVersionTaskSaveInHistoryManager() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        Assertions.assertNotNull(historyManager.getHistory());
    }

    @Test
    public void linkLastTest() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        Map<Integer, Node> history = historyManager.getHistoryMap();
        Assertions.assertNull(history.get(task3.getId()).next);
        Assertions.assertNotNull(history.get(task3.getId()).prev);
    }

    @Test
    public void removeNodeTest() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        Map<Integer, Node> history = historyManager.getHistoryMap();
        Assertions.assertTrue(history.containsKey(task1.getId()));
        Assertions.assertTrue(history.containsKey(task2.getId()));
        Assertions.assertTrue(history.containsKey(task3.getId()));
        historyManager.remove(task2.getId());
        Assertions.assertFalse(history.containsKey(task2.getId()));
        Assertions.assertEquals(history.get(task1.getId()).next, history.get(task3.getId()));
        Assertions.assertEquals(history.get(task1.getId()).next, history.get(task3.getId()));
        Assertions.assertEquals(history.get(task3.getId()).prev, history.get(task1.getId()));
    }

    @Test
    public void addTaskTest() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        List<Task> history = historyManager.getHistory();
        Assertions.assertEquals(history.size(), 3);
        Assertions.assertEquals(task1, history.get(0));
        Assertions.assertEquals(task2, history.get(1));
        Assertions.assertEquals(task3, history.get(2));
    }

    @Test
    public void removeFromBegin() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task1.getId());
        Assertions.assertFalse(historyManager.getHistory().contains(task1));
        List<Task> history = historyManager.getHistory();
        Assertions.assertEquals(2, history.size());
        Assertions.assertEquals(task2, history.get(0));
        Assertions.assertEquals(task3, history.get(1));
    }

    @Test
    public void removeFromMid() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getId());
        Assertions.assertFalse(historyManager.getHistory().contains(task2));
        List<Task> history = historyManager.getHistory();
        Assertions.assertEquals(2, history.size());
        Assertions.assertEquals(task1, history.get(0));
        Assertions.assertEquals(task3, history.get(1));
    }

    @Test
    public void removeFromEnd() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task3.getId());
        Assertions.assertFalse(historyManager.getHistory().contains(task3));
        List<Task> history = historyManager.getHistory();
        Assertions.assertEquals(2, history.size());
        Assertions.assertEquals(task1, history.get(0));
        Assertions.assertEquals(task2, history.get(1));
    }

    @Test
    public void shouldHistoryHaveDublicate() {
        historyManager.add(task1);
        historyManager.add(task1);
        Assertions.assertEquals(1, historyManager.getHistory().size());
    }
}
