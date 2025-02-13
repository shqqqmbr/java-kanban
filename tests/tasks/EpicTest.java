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

//    @Test
//    public void shouldEpicAddToEpic(){
//        Epic epic = new Epic("Эпик", "Описание эпика");
//        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
//        epic.addSubtask(epic1);
//        Assertions.assertNull(epic.getAllSubtasks());
//    }

//    Исходя из моего кода этот тест сделать невозможно, как и добавить эпик в эпик в виде подзадачи
}