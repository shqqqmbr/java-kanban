package main.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.managers.TaskManager;
import main.tasks.Subtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    public SubtaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().getPath();
        String[] pathPart = path.split("/");
        switch (method) {
            case "GET":
                if (pathPart.length == 2 && pathPart[1].equals("subtasks")) {
                    String subtasksJson = gson.toJson(taskManager.getAllSubtasks());
                    sendText(httpExchange, subtasksJson, 200);
                } else if (pathPart.length == 3 && pathPart[1].equals("subtasks")) {
                    int id = Integer.parseInt(pathPart[2]);
                    Optional<Subtask> subtaskOpt = Optional.ofNullable(taskManager.getSubtask(id));
                    if (subtaskOpt.isPresent()) {
                        String subtaskJson = gson.toJson(subtaskOpt.get());
                        sendText(httpExchange, subtaskJson, 200);
                    } else {
                        sendNotFound(httpExchange);
                    }
                } else {
                    sendExceptions(httpExchange, "Ошибка при обработке запроса.");
                }
                break;
            case "POST":
                if (pathPart.length == 2 && pathPart[1].equals("subtasks")) {
                    InputStream inputStream = httpExchange.getRequestBody();
                    Subtask subtask = gson.fromJson(new InputStreamReader(
                            inputStream, StandardCharsets.UTF_8), Subtask.class);
                    inputStream.close();
                    if (subtask.getId() == 0) {
                        try {
                            taskManager.addSubtask(subtask);
                            sendText(httpExchange, "Подзадача успешно добавлена!", 201);
                        } catch (IllegalArgumentException e) {
                            sendHasInteractions(httpExchange);
                        }
                    } else {
                        try {
                            taskManager.updateSubtask(subtask);
                            sendText(httpExchange, "Подзадача успешно обновлена!", 201);
                        } catch (IllegalArgumentException e) {
                            sendHasInteractions(httpExchange);
                        }
                    }
                } else {
                    sendExceptions(httpExchange, "Ошибка при обработке запроса.");
                }
                break;
            case "DELETE":
                if (pathPart.length == 3 && pathPart[1].equals("subtasks")) {
                    int id = Integer.parseInt(pathPart[2]);
                    taskManager.deleteSubtask(id);
                    sendText(httpExchange, "Подзадача успешно удалена!", 200);
                } else {
                    sendExceptions(httpExchange, "Произошла ошибка при обработке запроса.");
                }
                break;
        }
    }
}
