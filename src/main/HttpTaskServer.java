package main;


import com.sun.net.httpserver.HttpServer;
import main.handlers.*;
import main.managers.Managers;
import main.managers.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpTaskServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        TaskManager tm = Managers.getDefaultTaskManager();
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler(tm));
        httpServer.createContext("/subtasks", new SubtaskHandler(tm));
        httpServer.createContext("/epics", new EpicHandler(tm));
        httpServer.createContext("/history", new HistoryHandler(tm));
        httpServer.createContext("/prioritized", new PrioritizedHandler(tm));
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }
}
