package main.managers;

import main.constants.Status;
import main.constants.Type;
import main.exceptions.ManagerSaveException;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;

import java.io.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException {
        FileBackedTaskManager fb = new FileBackedTaskManager(file);
        fb.load(file);
        return fb;
    }


    @Override
    public void addTask(Task task) {
        super.addTask(task);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEpic(int epicId) {
        super.deleteEpic(epicId);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        super.deleteSubtask(subtaskId);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateTask(Task task, Task newTask) {
        super.updateTask(task, newTask);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEpic(Epic epic, Epic newEpic) {
        super.updateEpic(epic, newEpic);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateSubtask(Subtask subtask, Subtask newSubtask) {
        super.updateSubtask(subtask, newSubtask);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    private void save() throws ManagerSaveException {
        try (Writer fileWriter = new FileWriter(file)) {
            fileWriter.write("id,type,name,status,description,epic\n");
            for (Task task : getAllTasks()) {
                fileWriter.write(toString(task) + "\n");
            }
            for (Epic epic : getAllEpics()) {
                fileWriter.write(toString(epic) + "\n");
            }
            for (Subtask subtask : getAllSubtasks()) {
                fileWriter.write(toString(subtask) + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл.");
        }
    }

    private Task fromString(String value) {
        String[] splValue = value.split(",");
        int id = Integer.parseInt(splValue[0]);
        Type type = Type.valueOf(splValue[1]);
        String name = splValue[2];
        Status status = Status.valueOf(splValue[3]);
        String description = splValue[4];
        int epicId;

        switch (type) {
            case TASK:
                Task task = new Task(name, description, status);
                task.setId(id);
                tasks.put(task.getId(), task);
                return task;
            case EPIC:
                Epic epic = new Epic(name, description);
                epic.setId(id);
                epics.put(epic.getId(), epic);
                return epic;
            case SUBTASK:
                epicId = Integer.parseInt(splValue[5]);
                Subtask subtask = new Subtask(name, description, status, epicId);
                subtask.setId(id);
                subtasks.put(subtask.getId(), subtask);

                Epic parentEpic = epics.get(epicId);
                if (parentEpic != null) {
                    parentEpic.addSubtask(subtask);
                }
                return subtask;
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
        }
    }

    private String toString(Task task) {
        if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            return String.format("%d,SUBTASK,%s,%s,%s,%d", subtask.getId(), subtask.getName(),
                    subtask.getStatus(), subtask.getDescription(), subtask.getEpicId());
        } else if (task instanceof Epic) {
            return String.format("%d,EPIC,%s,%s,%s", task.getId(), task.getName(), task.getStatus(), task.getDescription());
        } else if (task instanceof Task) {
            return String.format("%d,TASK,%s,%s,%s", task.getId(), task.getName(), task.getStatus(), task.getDescription());
        } else {
            throw new IllegalArgumentException("Неизвестный тип задачи.");
        }
    }

    private void load(File file) throws ManagerSaveException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            while (br.ready()) {
                fromString(br.readLine());
            }
            setCount(filesMaxId());
        } catch (FileNotFoundException e) {
            throw new ManagerSaveException("Файл не найден.");
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении файла.");
        }
    }
}
