package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.HttpTaskServer;
import main.constants.Status;
import main.managers.Managers;
import main.managers.TaskManager;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;
import main.typeAdapters.DurationAdapter;
import main.typeAdapters.LocalDateTimeAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    TaskManager manager;
    HttpTaskServer taskServer;
    HttpClient client = HttpClient.newHttpClient();
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public HandlersTest() throws IOException {
    }

    @BeforeEach
    public void setUp() throws IOException {
        manager = Managers.getDefaultTaskManager();
        task = new Task("Задача 1", "Описание задачи 1", Status.NEW,
                Duration.ofMinutes(10), LocalDateTime.of(2020, 1, 1, 10, 0));
        epic = new Epic("Эпик 1", "Описание эпика 1");
        subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getId(),
                Duration.ofMinutes(10), LocalDateTime.of(2021, 1, 10, 10, 0));
        taskServer = new HttpTaskServer(manager);
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void getTest() throws IOException, InterruptedException {
        String jsonTask = gson.toJson(manager.getAllTasks());
        String jsonEpic = gson.toJson(manager.getAllEpics());
        String jsonSubtask = gson.toJson(manager.getAllSubtasks());
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
}
