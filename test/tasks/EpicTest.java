package tasks;

import main.tasks.Epic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EpicTest {

    @Test
    public void shouldEpicsWithSameIdEquals() {
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик 1", "Описание эпика 1");
        Assertions.assertEquals(epic1.getId(), epic2.getId(), "Эпики разные.");
        Assertions.assertEquals(epic1, epic2, "Эпики разные");
    }
}