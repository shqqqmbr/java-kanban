package managers;

import main.constants.Status;
import main.managers.TaskManager;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;
    Task task1;
    Epic epic1;
    Subtask subtask1;

    protected abstract T createTaskManager();

    @BeforeEach
    public void beforeEach() {
        manager = createTaskManager();
        epic1 = new Epic("Эпик 1", "Описание эпика 1");
        task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW,
                Duration.ofMinutes(10), LocalDateTime.of(2025, 1, 1, 10, 0));
        subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic1.getId(),
                Duration.ofMinutes(15), LocalDateTime.of(2025, 1, 1, 10, 20));
        manager.addEpic(epic1);
        manager.addTask(task1);
        manager.addSubtask(subtask1);
    }

    @Test
    public void addTask() {
        Assertions.assertEquals(1, manager.getAllTasks().size());
        Assertions.assertTrue(manager.getPrioritizedTasks().contains(task1));
    }

    @Test
    public void addEpic() {
        Assertions.assertEquals(1, manager.getAllEpics().size());
    }

    @Test
    public void addSubtask() {
        Assertions.assertEquals(1, manager.getAllSubtasks().size());
        Assertions.assertTrue(manager.getPrioritizedTasks().contains(subtask1));
    }

    @Test
    public void deleteTask() {
        manager.deleteTask(task1.getId());
        Assertions.assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void deleteEpic() {
        manager.deleteEpic(epic1.getId());
        Assertions.assertEquals(0, manager.getAllEpics().size());
    }

    @Test
    public void deleteSubtask() {
        manager.deleteSubtask(subtask1.getId());
        Assertions.assertEquals(0, manager.getAllSubtasks().size());
    }

    @Test
    public void updateTask() {
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW,
                Duration.ofMinutes(10), LocalDateTime.of(2025, 1, 1, 10, 40));
        manager.updateTask(task1, task2);
        Assertions.assertEquals(task1.getId(), task2.getId());
        Assertions.assertFalse(manager.getAllTasks().contains(task1));
        Assertions.assertTrue(manager.getAllTasks().contains(task2));
    }

    @Test
    public void updateEpic() {
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        List<Subtask> epic1Subtasks = manager.getEpicsSubtasks(epic1);
        manager.updateEpic(epic1, epic2);
        Assertions.assertEquals(epic1.getId(), epic2.getId());
        Assertions.assertFalse(manager.getAllEpics().contains(epic1));
        Assertions.assertTrue(manager.getAllEpics().contains(epic2));
    }

    @Test
    public void updateSubtask() {
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, epic1.getId(),
                Duration.ofMinutes(15), LocalDateTime.of(2025, 1, 1, 11, 0));
        manager.updateSubtask(subtask1, subtask2);
        Assertions.assertEquals(subtask1.getId(), subtask2.getId());
        Assertions.assertFalse(manager.getAllSubtasks().contains(subtask1));
        Assertions.assertTrue(manager.getAllSubtasks().contains(subtask2));
    }

    @Test
    public void deleteAllTasks() {
        manager.deleteAllTasks();
        Assertions.assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void deleteAllEpics() {
        manager.deleteAllEpics();
        Assertions.assertEquals(0, manager.getAllEpics().size());
    }

    @Test
    public void deleteAllSubtasks() {
        manager.deleteAllSubtasks();
        Assertions.assertEquals(0, manager.getAllSubtasks().size());
    }

    @Test
    public void getAllTasks() {
        Assertions.assertEquals(1, manager.getAllTasks().size());
    }

    @Test
    public void getAllEpics() {
        Assertions.assertEquals(1, manager.getAllEpics().size());
    }

    @Test
    public void getAllSubtasks() {
        Assertions.assertEquals(1, manager.getAllSubtasks().size());
    }

    @Test
    public void getEpicsSubtasks() {

    }

    @Test
    public void getTask() {
        Assertions.assertEquals(task1, manager.getTask(task1.getId()));
    }

    @Test
    public void getEpic() {
        Assertions.assertEquals(epic1, manager.getEpic(epic1.getId()));
    }

    @Test
    public void getSubtask() {
        Assertions.assertEquals(subtask1, manager.getSubtask(subtask1.getId()));
    }

    @Test
    public void getHistory() {
        manager.getTask(task1.getId());
        manager.getEpic(epic1.getId());
        manager.getSubtask(subtask1.getId());
        Assertions.assertEquals(3, manager.getHistory().size());
    }
}