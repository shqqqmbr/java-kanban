package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.HttpTaskServer;
import main.constants.Status;
import main.managers.InMemoryTaskManager;
import main.managers.Managers;
import main.managers.TaskManager;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;
import main.typeAdapters.DurationAdapter;
import main.typeAdapters.LocalDateTimeAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;


public class HandlersTest {
    Task task;
    Epic epic;
    Subtask subtask;
    // создаём экземпляр InMemoryTaskManager
    HttpClient client = HttpClient.newHttpClient();
    TaskManager taskManager = new InMemoryTaskManager();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    HttpTaskServer taskServer = new HttpTaskServer(taskManager);
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration .class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime .class, new LocalDateTimeAdapter())
            .create();

    public HandlersTest(){
    }

    @BeforeEach
    public void setUp() throws IOException {
        taskManager = Managers.getDefaultTaskManager();
        epic = new Epic("epic", "desc");
        taskManager.addEpic(epic);
        subtask = new Subtask("subtask1", "description", Status.DONE, epic.getId(),
                Duration.ofMinutes(30), LocalDateTime.of(2025, 3, 2, 14, 20));
        task = new Task("task1", "description", Status.NEW,
                Duration.ofMinutes(30), LocalDateTime.of(2025, 3, 5, 23, 45));
        taskManager.addSubtask(subtask);
        taskManager.addTask(task);
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void getTest() throws IOException, InterruptedException {
        String jsonTask = gson.toJson(taskManager.getAllTasks());
        String jsonEpic = gson.toJson(taskManager.getAllEpics());
        String jsonSubtask = gson.toJson(taskManager.getAllSubtasks());
        URI tasksURL = URI.create("http://localhost:8080/tasks");
        URI epicsURL = URI.create("http://localhost:8080/epics");
        URI subtasksURL = URI.create("http://localhost:8080/subtasks");
        HttpRequest tasksRequest = HttpRequest.newBuilder()
                .GET()
                .uri(tasksURL)
                .build();
        HttpRequest epicsRequest = HttpRequest.newBuilder()
                .GET()
                .uri(epicsURL)
                .build();
        HttpRequest subtasksRequest = HttpRequest.newBuilder()
                .GET()
                .uri(subtasksURL)
                .build();
        HttpResponse<String> tasksResponse = client.send(tasksRequest, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> epicsResponse = client.send(epicsRequest, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> subtasksResponse = client.send(subtasksRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(jsonTask, tasksResponse.body());
        Assertions.assertEquals(jsonEpic, epicsResponse.body());
        Assertions.assertEquals(jsonSubtask, subtasksResponse.body());
        Assertions.assertEquals(200, tasksResponse.statusCode());
        Assertions.assertEquals(200, epicsResponse.statusCode());
        Assertions.assertEquals(200, epicsResponse.statusCode());
    }

    @Test
    public void postTaskTest() throws IOException, InterruptedException {
        Task task2 = new Task("task2","description2", Status.NEW, Duration.ofMinutes(5),
                LocalDateTime.of(2025, 3,26,12,20));
        String taskJson = gson.toJson(task2);
        URI taskUrl = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(taskUrl)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201, response.statusCode());
//        Assertions.assertTrue();
    }
}