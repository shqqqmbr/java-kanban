package main.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.managers.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {
    public PrioritizedHandler(TaskManager taskManager){
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().getPath();
        String[] pathPart = path.split("/");
        switch (method) {
            case "GET":
                if (pathPart.length == 2 && pathPart[1].equals("prioritized")) {
                    String prioritTasks = gson.toJson(taskManager.getPrioritizedTasks());
                    sendText(httpExchange, prioritTasks, 200);
                } else {
                    otherExceptions(httpExchange, "Некорректный путь.");
                }
                break;
            default:
                otherExceptions(httpExchange, "Некорректный метод запроса.");
                break;
        }
    }
}
