package tasks;

import main.constants.Status;
import main.tasks.Epic;
import main.tasks.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubtaskTest {
    @Test
    public void shouldSubtasksWithSameIdEquals() {
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача1", "Описание подзадачи1", Status.DONE, epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача1", "Описание подзадачи1", Status.DONE, epic1.getId());
        Assertions.assertEquals(subtask1.getId(), subtask2.getId(), "Подзадачи разные.");
        Assertions.assertEquals(subtask1, subtask2, "Подзадачи разные.");
    }
}
