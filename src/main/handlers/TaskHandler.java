package main.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.managers.TaskManager;
import main.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    public TaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String[] pathPart = path.split("/");
            switch (method) {
                case "GET":
                    if (path.equals("/tasks")) {
                        String tasksJson = gson.toJson(taskManager.getAllTasks());
                        sendText(httpExchange, tasksJson, 200);
                    }else if (path.equals("/tasks/%d")) {
                        int id = Integer.parseInt(pathPart[2]);
                        Optional<Task> task = Optional.ofNullable(taskManager.getTask(id));
                        if (task.isPresent()){
                            String taskJson = gson.toJson(task.get());
                            sendText(httpExchange, taskJson, 200);
                        } else {
                            sendNotFound(httpExchange);
                        }
                    } else {
                        sendNotFound(httpExchange);
                    }
                    httpExchange.close();
                    break;

                case "POST":
                    if (pathPart.equals("/tasks")){
                        InputStream inputStream = httpExchange.getRequestBody();
                        Task task = gson.fromJson(new InputStreamReader
                                (inputStream, StandardCharsets.UTF_8), Task.class);
                        if (task.getId() == 0) {
                            try {
                                taskManager.addTask(task);
                                sendText(httpExchange, "Задача успешно добавлена!", 201);
                            } catch (IllegalArgumentException e) {
                                sendHasInteractions(httpExchange);
                            }
                        } else if (task.getId() > 0) {

                        }
                    }
                    httpExchange.close();
                    break;

                case "DELETE":
                    if (path.equals("/tasks/%d")) {
                        String id = pathPart[2];
                        taskManager.deleteTask(Integer.valueOf(id));
                        sendText(httpExchange, "Задача удалена успешно!", 200);
                    } else {
                        sendNotFound(httpExchange);
                    }
                    httpExchange.close();
                    break;
            }
        } catch (Exception e) {
            otherExceptions(httpExchange, "Общее исключение");
        }
    }
}
