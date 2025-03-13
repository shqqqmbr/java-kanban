package managers;

import main.constants.Status;
import main.managers.InMemoryTaskManager;
import main.tasks.Epic;
import main.tasks.Subtask;
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
    public void shouldAddAndReturnTaskById() {
        Task task = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        manager.addTask(task);
        Assertions.assertTrue(manager.getAllTasks().size() == 1);
        Assertions.assertEquals(task, manager.getTask(task.getId()));
    }

    @Test
    public void shouldAddAndReturnEpicById() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic);
        Assertions.assertTrue(manager.getAllEpics().size() == 1);
        Assertions.assertEquals(epic, manager.getEpic(epic.getId()));
    }

    @Test
    public void shouldAddAndReturnSubtaskById() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getId());
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        Assertions.assertTrue(manager.getAllSubtasks().size() == 1);
        Assertions.assertEquals(subtask, manager.getSubtask(subtask.getId()));
    }

    @Test
    public void shouldTasksConflictWithSetAndGeneratedId() {
        Task taskWithSetId = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        taskWithSetId.setId(1);
        manager.addTask(taskWithSetId);
        Task taskWithGeneratedId = new Task("Задача 2", "Описание задачи 2", Status.IN_PROGRESS);
        manager.addTask(taskWithGeneratedId);
        Task returnTaskWithSetId = manager.getTask(taskWithSetId.getId());
        Task returnTaskWithGeneratedId = manager.getTask(taskWithGeneratedId.getId());
        Assertions.assertNotNull(returnTaskWithSetId);
        Assertions.assertNotNull(returnTaskWithGeneratedId);
        Assertions.assertNotEquals(returnTaskWithSetId, returnTaskWithGeneratedId);
    }

    @Test
    public void shouldTaskNotChangeAfterAdd() {
        Task task = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        manager.addTask(task);
        Task returnTask = manager.getTask(task.getId());
        Assertions.assertEquals("Задача 1", returnTask.getName());
        Assertions.assertEquals("Описание задачи 1", returnTask.getDescription());
        Assertions.assertEquals(Status.NEW, returnTask.getStatus());
        Assertions.assertEquals(task.getId(), returnTask.getId());
    }

    @Test
    public void shouldDeletedSubtaskHaveNotID() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getId());
        manager.addSubtask(subtask);
        int subtaskId = subtask.getEpicId();
        manager.deleteSubtask(subtaskId);
        subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getId());
        manager.addSubtask(subtask);
        int subtask2Id = subtask.getEpicId();
        Assertions.assertEquals(subtaskId, subtask2Id);
    }

    @Test
    public void shouldEpicHaveNotIrrelevantSubtasksID() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getId());
        epic.addSubtask(subtask);
        int subtaskId = subtask.getEpicId();
        epic.getAllSubtasks().remove(subtaskId);
        subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getId());
        epic.addSubtask(subtask);
        int subtask2ID = subtask.getEpicId();
        Assertions.assertEquals(subtaskId, subtask2ID);
    }

    @Test
    public void checkEpicStatus() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, epic.getId());
        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        Assertions.assertTrue(epic.getStatus() == Status.NEW);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        epic.updateEpicStatus();
        Assertions.assertTrue(epic.getStatus() == Status.DONE);
        subtask1.setStatus(Status.NEW);
        epic.updateEpicStatus();
        Assertions.assertTrue(epic.getStatus() == Status.IN_PROGRESS);
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        epic.updateEpicStatus();
        Assertions.assertTrue(epic.getStatus() == Status.IN_PROGRESS);
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
