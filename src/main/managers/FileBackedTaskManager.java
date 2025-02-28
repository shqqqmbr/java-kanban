package main.managers;

import main.exceptions.ManagerSaveException;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileBackedTaskManager extends InMemoryTaskManager {
    File file;

    public FileBackedTaskManager(File file) throws ManagerSaveException{
        this.file = file;
    }


    public void save() throws ManagerSaveException {
        try (Writer fileWriter = new FileWriter(file)) {
            fileWriter.write("id,type,name,status,description,epic");
            for (Task task : getAllTasks()) {
                fileWriter.write(task.toString());
            }
            for (Epic epic: getAllEpics()){
                fileWriter.write(epic.toString());
            }
            for (Subtask subtask: getAllSubtasks()){
                4
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл.");
        }
    }

    @Override
    public void addTask(Task task){
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        sa  ve();
    }

    @Override
    public void deleteEpic(int epicId) {
        super.deleteEpic(epicId);
        save();
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        super.deleteSubtask(subtaskId);
        save();
    }

    @Override
    public void updateTask(Task task, Task newTask) {
        super.updateTask(task, newTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic, Epic newEpic) {
        super.updateEpic(epic, newEpic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask, Subtask newSubtask) {
        super.updateSubtask(subtask, newSubtask);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public String toString(){
        return
    }
}
