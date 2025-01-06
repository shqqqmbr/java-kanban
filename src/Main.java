public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.addEpic(epic1);


        Subtask subtask1_1 = new Subtask("Подзадача 1_1", "Описание подзадачи 1_1", Status.NEW, epic1.getId());
        Subtask subtask1_2 = new Subtask("Подзадача 1_2", "Описание подзадачи 1_2", Status.NEW, epic1.getId());
        taskManager.addSubtask(subtask1_1);
        taskManager.addSubtask(subtask1_2);


        Subtask newSubtask1_1 = new Subtask("Подзадача 1_1", "Описание подзадачи 1_1", Status.DONE, epic1.getId());
        Subtask newSubtask1_2 = new Subtask("Подзадача 1_2", "Описание подзадачи 1_2", Status.DONE, epic1.getId());


        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());


        taskManager.updateSubtask(subtask1_1, newSubtask1_1);
        taskManager.updateSubtask(subtask1_2, newSubtask1_2);
        taskManager.deleteTask(1);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());


    }
}
