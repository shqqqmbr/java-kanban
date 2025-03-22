package main.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import main.managers.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class BaseHttpHandler {
    protected final TaskManager taskManager;
    protected final Gson gson;
    public BaseHttpHandler(TaskManager taskManager){
        this.taskManager = taskManager;
        this.gson = new Gson();
    }

    protected void sendText(HttpExchange httpExchange, String text, int rCode) throws IOException {
        byte[] response = text.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        httpExchange.sendResponseHeaders(rCode, 0);
        httpExchange.getResponseBody().write(response);
        httpExchange.close();
    }

    protected void sendNotFound(HttpExchange httpExchange) throws IOException {
        String response = "Задача не найдена.";
        httpExchange.sendResponseHeaders(404, 0);
        httpExchange.getResponseBody().write(response.getBytes());
        httpExchange.close();
    }

    protected void sendHasInteractions(HttpExchange httpExchange) throws IOException {
        String response = "Задача пересекается с существующими.";
        httpExchange.sendResponseHeaders(406, 0);
        httpExchange.getResponseBody().write(response.getBytes());
        httpExchange.close();
    }

    protected void otherExceptions(HttpExchange httpExchange,String text) throws IOException{
        httpExchange.sendResponseHeaders(500, 0);
        httpExchange.getResponseBody().write(text.getBytes());
        httpExchange.close();
    }
}
