package tasks;

import main.constants.Status;
import main.managers.Managers;
import main.managers.TaskManager;
import main.tasks.Epic;
import main.tasks.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

class EpicTest {

    TaskManager manager = Managers.getDefaultTaskManager();

    @Test
    public void shouldEpicsWithSameIdEquals() {
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик 1", "Описание эпика 1");
        Assertions.assertEquals(epic1.getId(), epic2.getId(), "Эпики разные.");
        Assertions.assertEquals(epic1, epic2, "Эпики разные");
    }

    @Test
    public void checkEpicStatus() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getId(),
                Duration.ofMinutes(10), LocalDateTime.of(2025, 1, 10, 10, 0));
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, epic.getId(),
                Duration.ofMinutes(10), LocalDateTime.of(2025, 1, 10, 10, 20));
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
}