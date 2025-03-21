package main.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.exceptions.ManagerSaveException;
import main.managers.Managers;
import main.managers.TaskManager;

import java.io.IOException;
import java.io.OutputStream;

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
                        taskManager.getAllTasks();
                        sendText(httpExchange, "Задачи выведены успешно!", 200);
                    }
                    if (pathPart.length==3 && pathPart[1]=="tasks"){
                        String id = pathPart[2];
                        taskManager.getTask(Integer.valueOf(id));
                        sendText(httpExchange,"Задача выведена успешно!",200);
                    }else {
                        sendNotFound(httpExchange);
                    }
                case "POST":
                    if (pathPart.length==2 && pathPart[1]=="tasks"){

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
