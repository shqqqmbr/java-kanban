package tests.managers;

import main.constants.Status;
import main.managers.Managers;
import main.managers.TaskManager;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryTaskManagerTest {
    TaskManager manager;

    @BeforeEach
    public void beforeEach() {
        manager = Managers.getDefaultTaskManager();
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
    public void shouldTaskNotChangeAfterAdd(){
        Task task = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        manager.addTask(task);
        Task returnTask = manager.getTask(task.getId());
        Assertions.assertEquals("Задача 1", returnTask.getName());
        Assertions.assertEquals("Описание задачи 1", returnTask.getDescription());
        Assertions.assertEquals(Status.NEW, returnTask.getStatus());
        Assertions.assertEquals(task.getId(), returnTask.getId());
    }
}
