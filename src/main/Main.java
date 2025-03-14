package main;


import main.constants.Status;
import main.exceptions.ManagerSaveException;
import main.managers.FileBackedTaskManager;
import main.managers.Managers;
import main.managers.TaskManager;
import main.tasks.Epic;
import main.tasks.Subtask;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class Main {

    public static void main(String[] args) throws ManagerSaveException, IOException {
        File file = File.createTempFile("practicum", ".csv");
        FileBackedTaskManager fb = new FileBackedTaskManager(file);
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        fb.addEpic(epic1);
        System.out.println(epic1);
        Subtask subtask1 = new Subtask("Подзадача1", "Описание подзадачи1", Status.DONE, epic1.getId(),
                Duration.ofMinutes(10), LocalDateTime.of(2025, 1, 10,10, 0));
        Subtask subtask2 = new Subtask("Подзадача2", "Описание подзадачи2", Status.DONE, epic1.getId(),
                Duration.ofMinutes(10), LocalDateTime.of(2025, 1, 10,10, 20));
        fb.addSubtask(subtask1);
        fb.addSubtask(subtask2);
        System.out.println(epic1);
        FileBackedTaskManager newFile = FileBackedTaskManager.loadFromFile(file);
        Subtask subtask3 = new Subtask("Подзадача3", "Описание подзадачи3", Status.DONE, epic1.getId(),
                Duration.ofMinutes(10), LocalDateTime.of(2025, 1, 10,10, 40));
        newFile.addSubtask(subtask3);
        System.out.println();
    }
}
