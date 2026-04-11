import com.sun.net.httpserver.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;

public class TaskHandler implements HttpHandler {

    private static final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            String method = exchange.getRequestMethod();

            switch (method) {
                case "GET":
                    handleGet(exchange);
                    break;
                case "POST":
                    handlePost(exchange);
                    break;
                case "PUT":
                    handlePut(exchange);
                    break;
                case "DELETE":
                    handleDelete(exchange);
                    break;
                default:
                    sendJson(exchange, 405, "Method not allowed", null);
            }

        } catch (Exception e) {
            e.printStackTrace(); 
            sendJson(exchange, 500, "Internal Server Error", null);
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {

        List<Task> tasks = App.service.getAllTasks();

        sendJson(exchange, 200, "Tasks fetched", tasks);
    }

    private void handlePost(HttpExchange exchange) throws IOException {

        String body = readBody(exchange);

        JsonObject obj = JsonParser.parseString(body).getAsJsonObject();

        // Validation
        if (!obj.has("name") || !obj.has("description") ||
            !obj.has("priority") || !obj.has("status")) {

            sendJson(exchange, 400, "Missing required fields", null);
            return;
        }

        String name = obj.get("name").getAsString();
        String desc = obj.get("description").getAsString();

        Priority priority = Priority.valueOf(
                obj.get("priority").getAsString().toUpperCase()
        );

        Status status = Status.valueOf(
                obj.get("status").getAsString().toUpperCase()
        );

        App.service.createTask(name, desc, priority, status);

        sendJson(exchange, 201, "Task Created Successfully", null);
    }


    private void handlePut(HttpExchange exchange) throws IOException {

        String body = readBody(exchange);

        JsonObject obj = JsonParser.parseString(body).getAsJsonObject();

        if (!obj.has("id") || !obj.has("field") || !obj.has("value")) {
            sendJson(exchange, 400, "Missing required fields", null);
            return;
        }

        int id = obj.get("id").getAsInt();
        String field = obj.get("field").getAsString();
        String value = obj.get("value").getAsString();

        App.service.updateTask(id, field, value);

        sendJson(exchange, 200, "Task Updated Successfully", null);
    }

    private void handleDelete(HttpExchange exchange) throws IOException {

        String query = exchange.getRequestURI().getQuery();

        if (query == null || !query.contains("=")) {
            sendJson(exchange, 400, "Invalid query. Use ?id=1", null);
            return;
        }

        int id = Integer.parseInt(query.split("=")[1]);

        App.service.deleteTask(id);

        sendJson(exchange, 200, "Task Deleted Successfully", null);
    }


    private String readBody(HttpExchange exchange) throws IOException {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody())
        );

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    private void sendJson(HttpExchange exchange, int status, String message, Object data) throws IOException {

        JsonObject res = new JsonObject();

        res.addProperty("status", status);
        res.addProperty("message", message);
        res.add("data", gson.toJsonTree(data));

        String json = res.toString();

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, json.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes());
        os.close();
    }
}