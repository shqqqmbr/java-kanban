package managers;

import main.constants.Status;
import main.managers.TaskManager;
import main.tasks.Epic;
import main.tasks.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T tm;

    protected abstract T createTaskManager();

    @BeforeEach
    public void beforeEach(){
        tm = createTaskManager();

    }

    @Test
    public void checkEpicStatus(){
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, epic.getId());
        tm.addEpic(epic);
        tm.addSubtask(subtask1);
        tm.addSubtask(subtask2);
        Assertions.assertTrue(epic.getStatus()==Status.NEW);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        epic.updateEpicStatus();
        Assertions.assertTrue(epic.getStatus()==Status.DONE);
        subtask1.setStatus(Status.NEW);
        epic.updateEpicStatus();
        Assertions.assertTrue(epic.getStatus()==Status.IN_PROGRESS);
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        epic.updateEpicStatus();
        Assertions.assertTrue(epic.getStatus()==Status.IN_PROGRESS);
    }

    @Test
    public void shouldSubtaskHaveEpic(){
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getId());
        tm.addEpic(epic);
        tm.addSubtask(subtask1);
        Assertions.assertTrue(tm.getAllEpics().contains(epic));
    }
}
