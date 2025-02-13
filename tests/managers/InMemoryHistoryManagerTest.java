package managers;

import main.constants.Status;
import main.managers.HistoryManager;
import main.managers.Managers;
import main.managers.Node;
import main.managers.TaskManager;
import main.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class InMemoryHistoryManagerTest {
    HistoryManager manager;
    TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        manager = Managers.getDefaultHistoryManager();
        taskManager = Managers.getDefaultTaskManager();
    }

    @Test
    public void shouldLastVersionTaskSaveInHistoryManager() {
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.DONE);
        Task task3 = new Task("Задача 3", "Описание задачи 3", Status.IN_PROGRESS);
        manager.linkLast(task1);
        manager.linkLast(task2);
        manager.linkLast(task3);
        Assertions.assertNotNull(manager.getHistory());
    }

    @Test
    public void removeNodeTest() {
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.DONE);
        Task task3 = new Task("Задача 3", "Описание задачи 3", Status.IN_PROGRESS);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        manager.linkLast(task1);
        manager.linkLast(task2);
        manager.linkLast(task3);
        Map<Integer, Node> map = manager.getHistoryMap();
        Assertions.assertTrue(map.containsKey(task1.getId()));
        Assertions.assertTrue(map.containsKey(task2.getId()));
        Assertions.assertTrue(map.containsKey(task3.getId()));
        manager.removeNode(map.get(task2.getId()));
        Assertions.assertFalse(map.containsKey(task2.getId()));
        Assertions.assertEquals(map.get(task1.getId()).next, map.get(task3.getId()));
//        Assertions.assertEquals(map.get(task3.getId()).prev, map.get(task1.getId()));
    }
}
