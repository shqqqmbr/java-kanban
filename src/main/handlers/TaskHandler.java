package main.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.exceptions.ManagerSaveException;
import main.managers.Managers;
import main.managers.TaskManager;
import main.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TaskHandler extends BaseHttpHandler implements HttpHandler{
    public TaskHandler(TaskManager taskManager) {
        super(taskManager);
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String[] pathPart = path.split("/");
            switch (method){
                case "GET":
                    if (pathPart.length==2 && pathPart[1]=="tasks"){
                        String tasksJson = gson.toJson(taskManager.getAllTasks());
                        sendText(httpExchange, tasksJson, 200);
                    }
                    if (pathPart.length==3 && pathPart[1]=="tasks"){
                        String id = pathPart[2];
                        String taskJson = gson.toJson(taskManager.getTask(Integer.valueOf(id)));
                        sendText(httpExchange,taskJson,200);
                    }else {
                        sendNotFound(httpExchange);
                    }
                case "POST":
                    if (pathPart.length==2 && pathPart[1]=="tasks"){
                        InputStreamReader reader = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
                        Task task = gson.fromJson(reader, Task.class);
                        if (task.getId()==0){
                            taskManager.createTask(task);
                        }
                    }



                case "DELETE":
                    if (pathPart.length==3 && pathPart[1]=="tasks"){
                        String id = pathPart[2];
                        taskManager.deleteTask(Integer.valueOf(id));
                        sendText(httpExchange, "Задача удалена успешно!", 200);
                    }
            }
        } catch (Exception e){
            otherExceptions(httpExchange);
        }
    }
}
