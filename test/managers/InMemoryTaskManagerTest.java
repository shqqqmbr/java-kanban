package managers;

import main.constants.Status;
import main.managers.InMemoryTaskManager;
import main.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    protected InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager();
    }


    @Test
    public void correctIntersect() {
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW,
                Duration.ofMinutes(10), LocalDateTime.of(2025, 1, 1, 10, 0));
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW,
                Duration.ofMinutes(10), LocalDateTime.of(2025, 1, 1, 10, 8));
        boolean isIntersect = manager.isTasksOverlapping(task1, task2);
        Assertions.assertTrue(isIntersect);
    }
}
