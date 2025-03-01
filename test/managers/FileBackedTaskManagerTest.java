package managers;

import main.constants.Status;
import main.exceptions.ManagerSaveException;
import main.managers.FileBackedTaskManager;
import main.tasks.Epic;
import main.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FileBackedTaskManagerTest {
    Task task;
    Epic epic;
    FileBackedTaskManager manager;
    File file;

    @BeforeEach
    public void beforeEach() throws IOException, ManagerSaveException {
        task = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        epic = new Epic("Эпик 1", "Описание эпика 1");
        file = File.createTempFile("tasks", ".csv");
        file.deleteOnExit();
        manager = new FileBackedTaskManager(file);

    }

    @Test
    public void SaveAndUploadEmptyFile() {
        manager.addTask(task);
        manager.addEpic(epic);
        Assertions.assertNotNull(file);

    }
}