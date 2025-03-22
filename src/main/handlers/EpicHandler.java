package main.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.managers.TaskManager;
import main.tasks.Epic;
import main.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {
    public EpicHandler(TaskManager taskManager) {
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
                    if (pathPart.length == 2 && pathPart[1].equals("epics")) {
                        String epicsJson = gson.toJson(taskManager.getAllEpics());
                        sendText(httpExchange, epicsJson, 200);
                    } else if (pathPart.length == 3 && pathPart[1].equals("epics")) {
                        int id = Integer.parseInt(pathPart[2]);
                        Optional<Epic> epic = Optional.ofNullable(taskManager.getEpic(id));
                        if (epic.isPresent()) {
                            String epicJson = gson.toJson(epic.get());
                            sendText(httpExchange, epicJson, 200);
                        } else {
                            sendNotFound(httpExchange);
                        }
                    } else if (pathPart.length == 4 && pathPart[1].equals("epics") && pathPart[3].equals("subtasks")) {
                        int id = Integer.parseInt(pathPart[2]);
                        Optional<Epic> epicOpt = Optional.ofNullable(taskManager.getEpic(id));
                        if (epicOpt.isPresent()) {
                            String allEpicsSubtasks = gson.toJson(epicOpt.get().getAllSubtasks());
                            sendText(httpExchange, allEpicsSubtasks, 200);
                        } else {
                            sendNotFound(httpExchange);
                        }
                    } else {
                        otherExceptions(httpExchange, "Ошибка при обработке запроса.");
                    }
                    break;

                case "POST":
                    if (pathPart.length == 2 && pathPart[1].equals("epics")) {
                        InputStream inputStream = httpExchange.getRequestBody();

                        Epic epic = gson.fromJson(new InputStreamReader
                                (inputStream, StandardCharsets.UTF_8), Epic.class);
                        inputStream.close();
                        taskManager.addEpic(epic);
                        sendText(httpExchange, "Задача успешно добавлена!", 201);
                    } else {
                        otherExceptions(httpExchange, "Ошибка при обработке запроса.");
                    }
                    break;

                case "DELETE":
                    if (pathPart.length == 3 && pathPart[1].equals("epics")) {
                        int id = Integer.parseInt(pathPart[2]);
                        taskManager.deleteEpic(id);
                        sendText(httpExchange, "Задача удалена успешно!", 200);
                    } else {
                        otherExceptions(httpExchange,"Ошибка при обработке запроса.");
                    }
                    break;
            }
        } catch (Exception e) {
            otherExceptions(httpExchange, e.getMessage());
        }
    }
}
