public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task(1, "Задача_1", "Описание_1", Status.NEW);
        taskManager.addTask(task1);
        System.out.println(taskManager.getTasks());
        task1.setStatus(Status.DONE);
        System.out.println(taskManager.getTasks());

        Task task2 = new Task(2, "Задача_2", "Описание_2", Status.NEW);


        Epic epic1 = new Epic("Эпик_1", "Описание_Большого_Эпика", 3);

        Epic epic2 = new Epic("Эпик_2", "Описание_Маленького_Эпика", 4);
    }
}
