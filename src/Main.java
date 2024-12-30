public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task(1, "Задача_1", "Описание_1", Status.NEW);
        taskManager.addTask(task1);
        System.out.println(taskManager.getTasks());
        task1.setStatus(Status.DONE);
        taskManager.updateTask(task1);
        task1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task1);
        System.out.println(taskManager.getTasks());

        Epic epic1 = new Epic("Эпик_1", "Описание_Большого_Эпика", 2);

        Subtask subtask1 = new Subtask(3, "Подзадача 1", "Описание подзадачи 1", Status.NEW, epic1.getId());
        taskManager.addSubtask(subtask1);

        Subtask subtask2 = new Subtask(4, "Подзадача 2", "Описание подзадачи 2", Status.NEW, epic1.getId());
        taskManager.addSubtask(subtask2);

        Subtask subtask3 = new Subtask(5, "Подзадача 3", "Описание подзадачи 3", Status.NEW, epic1.getId());
        taskManager.addSubtask(subtask3);

    }
}
