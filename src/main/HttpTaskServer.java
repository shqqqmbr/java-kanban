package main;


import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpTaskServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0); // связываем сервер с сетевым портом
        httpServer.createContext("/tasks", new );
        httpServer.createContext("/subtasks", new );
        httpServer.createContext("/tasks", new );
        httpServer.createContext("/tasks", new );
        httpServer.createContext("/tasks", new );
    }
}
