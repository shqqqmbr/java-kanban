package managers;

import main.constants.Status;
import main.managers.HistoryManager;
import main.managers.Managers;
import main.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryHistoryManagerTest {
    HistoryManager manager;

    @BeforeEach
    public void beforeEach() {
        manager = Managers.getDefaultHistoryManager();
    }

    @Test
    public void shouldLastVersionTaskSaveInHistoryManager(){
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.DONE);
        Task task3 = new Task("Задача 3", "Описание задачи 3", Status.IN_PROGRESS);
        manager.linkLast(task1);
        manager.linkLast(task2);
        manager.linkLast(task3);
        Assertions.assertNotNull(manager.getHistory());
    }
}
