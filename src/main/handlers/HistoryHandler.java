package main.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.managers.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    public HistoryHandler(TaskManager taskManager){
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException{
        String method = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().getPath();
        String[] pathPart = path.split("/");
        switch (method){
            case "GET":
                if (pathPart.length==2 && pathPart[1].equals("history")){
                    String historyJson = gson.toJson(taskManager.getHistory());
                    sendText(httpExchange, historyJson, 200);
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
