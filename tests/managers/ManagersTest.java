package managers;

import main.constants.Status;
import main.managers.HistoryManager;
import main.managers.Managers;
import main.managers.TaskManager;
import main.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManagersTest {
    @Test
    public void shouldManagersWorkCorrect(){
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        TaskManager manager = Managers.getDefaultTaskManager();
        manager.addTask(task1);
        Assertions.assertNotNull(manager.getAllTasks());

        HistoryManager manager1 = Managers.getDefaultHistoryManager();
        manager1.linkLast(task1);
        Assertions.assertNotNull(manager1.getHistory());
    }
}
