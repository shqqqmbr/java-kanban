package tasks;

import main.constants.Status;
import main.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    public void shouldClassWithSameIdEquals() {
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Assertions.assertEquals(task1.getId(), task2.getId(), "Задачи разные.");
        Assertions.assertEquals(task1, task2, "Задачи разные.");
    }
}