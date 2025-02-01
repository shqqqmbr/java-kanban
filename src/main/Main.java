package main;

import main.constants.Status;
import main.managers.Managers;
import main.managers.TaskManager;
import main.tasks.*;

import java.util.Objects;


public class Main {

    //внизу вопрос
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefaultTaskManager();
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic1);


        Subtask subtask1_1 = new Subtask("Подзадача 1_1", "Описание подзадачи 1_1", Status.NEW, epic1.getId());
        Subtask subtask1_2 = new Subtask("Подзадача 1_2", "Описание подзадачи 1_2", Status.NEW, epic1.getId());
        manager.addSubtask(subtask1_1);
        manager.addSubtask(subtask1_2);


        Subtask newSubtask1_1 = new Subtask("Подзадача 1_1", "Описание подзадачи 1_1", Status.DONE, epic1.getId());
        Subtask newSubtask1_2 = new Subtask("Подзадача 1_2", "Описание подзадачи 1_2", Status.DONE, epic1.getId());

        manager.updateSubtask(subtask1_1, newSubtask1_1);
        manager.updateSubtask(subtask1_2, newSubtask1_2);
        System.out.println("------------------------------------------");
        System.out.println("           | Вывод всех задач |           ");
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
        System.out.println("------------------------------------------");


        manager.getTask(0);
        manager.getTask(1);
        manager.getTask(0);
        manager.getTask(1);
        manager.getEpic(2);
        manager.getSubtask(3);
        manager.getSubtask(4);
        manager.getSubtask(3);
        manager.getSubtask(4);
        System.out.println("------------------------------------------");
        System.out.println("            | Вывод истории |             ");
        System.out.println(manager.getHistory());
        System.out.println("------------------------------------------");
    }
}
