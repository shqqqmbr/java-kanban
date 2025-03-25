package managers;

import main.constants.Status;
import main.exceptions.ManagerSaveException;
import main.managers.FileBackedTaskManager;
import main.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    File file = File.createTempFile("tests", ".csv");

    public FileBackedTaskManagerTest() throws IOException {
    }

    @Override
    protected FileBackedTaskManager createTaskManager() {
        return new FileBackedTaskManager(file);
    }

    @Test
    public void fromStringTest() {
        String taskStr = "0,TASK,Задача 1,NEW,Описание задачи 1";
        String[] taskArr = taskStr.split(",");
        Task task = new Task(taskArr[2], taskArr[4], Status.valueOf(taskArr[3]));
        task.setId(Integer.parseInt(taskArr[0]));
        Task newTask = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        newTask.setId(0);
        Assertions.assertEquals(task, newTask);
    }

    @Test
    public void SaveAndUploadEmptyFile() throws ManagerSaveException {
        Task task = new Task("Задача 1", "Описание задачи 1", Status.NEW,
                Duration.ofMinutes(10), LocalDateTime.of(2025, 1, 10, 10, 0));
        Task task1 = new Task("Задача 2", "Описание задачи 2", Status.NEW,
                Duration.ofMinutes(10), LocalDateTime.of(2020, 1, 10, 10, 20));
        manager = new FileBackedTaskManager(file);
        manager.addTask(task);
        manager.addTask(task1);
        Assertions.assertEquals(2, manager.getAllTasks().size());
        FileBackedTaskManager secManager = FileBackedTaskManager.loadFromFile(file);
        secManager.addTask(task);
        secManager.addTask(task1);
        Assertions.assertEquals(4, secManager.getAllTasks().size());
    }
}