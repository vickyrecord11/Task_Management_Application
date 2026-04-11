import com.sun.net.httpserver.*;
import java.net.InetSocketAddress;
//import com.google.gson.*;

public class App {

    public static TaskServices service;
    public static void start() throws Exception {

        System.out.println("TEST START");

        service = new TaskServices(StorageMode.MEMORY);
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
        
        server.createContext("/tasks", new TaskHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server started at http://localhost:8000/tasks");
        
    }
}
