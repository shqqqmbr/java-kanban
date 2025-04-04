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

class SubtaskTest {
    TaskManager manager = Managers.getDefaultTaskManager();

    @Test
    public void shouldSubtasksWithSameIdEquals() {
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача1", "Описание подзадачи1", Status.DONE, epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача1", "Описание подзадачи1", Status.DONE, epic1.getId());
        Assertions.assertEquals(subtask1.getId(), subtask2.getId(), "Подзадачи разные.");
        Assertions.assertEquals(subtask1, subtask2, "Подзадачи разные.");
    }

    @Test
    public void shouldSubtaskHaveEpic() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getId(),
                Duration.ofMinutes(10), LocalDateTime.of(2025, 1, 10, 10, 0));
        manager.addSubtask(subtask1);
        Assertions.assertTrue(manager.getAllEpics().contains(epic));
    }
}
