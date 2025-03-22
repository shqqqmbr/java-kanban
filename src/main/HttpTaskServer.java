package main;


import com.sun.net.httpserver.HttpServer;
import main.constants.Status;
import main.handlers.EpicHandler;
import main.handlers.TaskHandler;
import main.managers.Managers;
import main.managers.TaskManager;
import main.tasks.Task;

import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpTaskServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        TaskManager tm = Managers.getDefaultTaskManager();
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler(tm));
//        httpServer.createContext("/subtasks", new );
        httpServer.createContext("/epics", new EpicHandler(tm));
//        httpServer.createContext("/tasks", new );
//        httpServer.createContext("/tasks", new )
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }
}
