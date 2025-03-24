//package handlers;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import main.HttpTaskServer;
//import main.constants.Status;
//import main.managers.InMemoryTaskManager;
//import main.managers.TaskManager;
//import main.tasks.Task;
//import main.typeAdapters.DurationAdapter;
//import main.typeAdapters.LocalDateTimeAdapter;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class TaskHandlerTest {
//    TaskManager manager = new InMemoryTaskManager();
//    // передаём его в качестве аргумента в конструктор HttpTaskServer
//    HttpTaskServer taskServer = new HttpTaskServer(manager);
//    Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Duration .class, new DurationAdapter())
//                .registerTypeAdapter(LocalDateTime .class, new LocalDateTimeAdapter())
//                .create();
//
//    public TaskHandlerTest() throws IOException {
//    }
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        manager.deleteAllTasks();
//        manager.deleteAllSubtasks();
//        manager.deleteAllEpics();
//        taskServer.start();
//    }
//
//    @AfterEach
//    public void shutDown() {
//        taskServer.stop();
//    }
//
//    @Test
//    public void testAddTask() throws IOException, InterruptedException {
//        // создаём задачу
//        Task task = new Task("Test 2", "Testing task 2",
//                Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());
//        // конвертируем её в JSON
//        String taskJson = gson.toJson(task);
//
//        // создаём HTTP-клиент и запрос
//        HttpClient client = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/tasks");
//        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();
//
//        // вызываем рест, отвечающий за создание задач
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        // проверяем код ответа
//        Assertions.assertEquals(200, response.statusCode());
//
//        // проверяем, что создалась одна задача с корректным именем
//        List<Task> tasksFromManager = manager.getAllTasks();
//
//        Assertions.assertNotNull(tasksFromManager, "Задачи не возвращаются");
//        Assertions.assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
//        Assertions.assertEquals("Test 2", tasksFromManager.get(0).getName(), "Некорректное имя задачи");
//    }
//}
//
