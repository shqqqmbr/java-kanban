package main;

import main.constants.Status;
import main.exceptions.ManagerSaveException;
import main.managers.FileBackedTaskManager;
import main.managers.Managers;
import main.managers.TaskManager;
import main.tasks.*;

import java.io.File;
import java.io.IOException;


public class Main {

    //внизу вопрос
    public static void main(String[] args) throws ManagerSaveException, IOException {
        File file = File.createTempFile("tasks", ".csv");
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic1);


        Subtask subtask1 = new Subtask("Подзадача 1_1", "Описание подзадачи 1_1", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 1_2", "Описание подзадачи 1_2", Status.NEW, epic1.getId());
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        FileBackedTaskManager FBmanager = FileBackedTaskManager.loadFromFile(file);
        System.out.println(FBmanager.getAllTasks());
    }
}
