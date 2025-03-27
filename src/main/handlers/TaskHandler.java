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
                    if (pathPart.length == 2 && pathPart[1].equals("tasks")) {
                        String tasksJson = gson.toJson(taskManager.getAllTasks());
                        sendText(httpExchange, tasksJson, 200);
                    } else if (pathPart.length == 3 && pathPart[1].equals("tasks")) {
                        int id = Integer.parseInt(pathPart[2]);
                        Optional<Task> taskOpt = Optional.ofNullable(taskManager.getTask(id));
                        if (taskOpt.isPresent()) {
                            String taskJson = gson.toJson(taskOpt.get());
                            sendText(httpExchange, taskJson, 200);
                        } else {
                            sendNotFound(httpExchange);
                        }
                    } else {
                        otherExceptions(httpExchange, "Ошибка при обработке запроса.");
                    }
                    break;

                case "POST":
                    if (pathPart.length == 2 && pathPart[1].equals("tasks")) {
                        InputStream inputStream = httpExchange.getRequestBody();

                        Task task = gson.fromJson(new InputStreamReader(
                                inputStream, StandardCharsets.UTF_8), Task.class);
                        inputStream.close();
                        if (task.getId() == 0) {
                            try {
                                taskManager.addTask(task);
                                sendText(httpExchange, "Задача успешно добавлена!", 201);
                            } catch (IllegalArgumentException e) {
                                sendHasInteractions(httpExchange);
                            }
                        } else {
                            try {
                                taskManager.updateTask(task);
                                sendText(httpExchange, "Задача успешно обновлена!", 201);
                            } catch (IllegalArgumentException e) {
                                sendHasInteractions(httpExchange);
                            }
                        }
                    } else {
                        otherExceptions(httpExchange, "Ошибка при обработке запроса.");
                    }
                    break;

                case "DELETE":
                    if (pathPart.length == 3 && pathPart[1].equals("tasks")) {
                        int id = Integer.parseInt(pathPart[2]);
                        taskManager.deleteTask(id);
                        sendText(httpExchange, "Задача удалена успешно!", 200);
                    } else {
                        otherExceptions(httpExchange, "Ошибка при обработке запроса.");
                    }
                    break;
            }
        } catch (Exception e) {
            otherExceptions(httpExchange, e.getMessage());
        }
    }
}
