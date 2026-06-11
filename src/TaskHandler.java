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

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*");

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS");

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Headers",
                    "Content-Type");

            if (method.equalsIgnoreCase("OPTIONS")) {

                exchange.sendResponseHeaders(204, -1);

                return;
            }

            switch (method) {
                case "GET":

                    String query = exchange.getRequestURI()
                            .getQuery();

                    if (query != null &&
                            query.contains("page")) {

                        handleGet(exchange);

                    } else {

                        handleGetAll(exchange);

                    }

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

    private void handleGet(HttpExchange exchange)
            throws IOException {

        String query = exchange.getRequestURI().getQuery();

        int page = 1;
        int size = 7;

        if (query != null) {

            String[] params = query.split("&");

            for (String param : params) {

                String[] pair = param.split("=");

                if (pair.length != 2)
                    continue;

                if (pair[0].equals("page")) {

                    page = Integer.parseInt(pair[1]);

                }

                if (pair[0].equals("size")) {

                    size = Integer.parseInt(pair[1]);

                }
            }
        }

        List<Task> tasks = App.service.getTasksByPage(
                page,
                size);

        JsonObject response = new JsonObject();

        response.addProperty(
                "status",
                200);

        response.addProperty(
                "message",
                "Tasks fetched");

        response.add(
                "data",
                gson.toJsonTree(tasks));

        response.addProperty(
                "totalCount",
                App.service.getAllTasks().size());

        String json = response.toString();

        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "application/json");

        exchange.sendResponseHeaders(
                200,
                json.getBytes().length);

        OutputStream os = exchange.getResponseBody();

        os.write(json.getBytes());

        os.close();
    }

    private void handleGetAll(
            HttpExchange exchange)
            throws IOException {

        List<Task> tasks = App.service.getAllTasks();

        sendJson(
                exchange,
                200,
                "Tasks fetched",
                tasks);
    }

    private void handlePost(HttpExchange exchange) throws IOException {

        String body = readBody(exchange);

        JsonObject obj = JsonParser.parseString(body).getAsJsonObject();

        if (!obj.has("name") || !obj.has("category") || !obj.has("description") ||
                !obj.has("priority") || !obj.has("dueDate") || !obj.has("status")) {

            sendJson(exchange, 400, "Missing required fields", null);
            return;
        }

        String name = obj.get("name").getAsString();
        String category = obj.get("category").getAsString();
        String desc = obj.get("description").getAsString();

        Priority priority = Priority.valueOf(
                obj.get("priority").getAsString().toUpperCase());

        String dueDate = obj.get("dueDate").getAsString();

        Status status = Status.valueOf(
                obj.get("status").getAsString().toUpperCase());

        App.service.createTask(name, category, desc, priority, dueDate, status);

        sendJson(exchange, 201, "Task Created Successfully", null);
    }

    private void handlePut(HttpExchange exchange) throws IOException {

        String body = readBody(exchange);

        JsonObject obj = JsonParser.parseString(body).getAsJsonObject();

        if (!obj.has("id")
                || !obj.has("name")
                || !obj.has("category")
                || !obj.has("description")
                || !obj.has("priority")
                || !obj.has("dueDate")
                || !obj.has("status")) {

            sendJson(exchange, 400,
                    "Missing required fields", null);

            return;
        }

        int id = obj.get("id").getAsInt();
        String name = obj.get("name").getAsString();

        String category = obj.get("category").getAsString();

        String description = obj.get("description").getAsString();

        Priority priority = Priority.valueOf(
                obj.get("priority")
                        .getAsString()
                        .toUpperCase());

        String dueDate = obj.get("dueDate").getAsString();

        Status status = Status.valueOf(
                obj.get("status")
                        .getAsString()
                        .toUpperCase());

        App.service.updateTask(
                id,
                name,
                category,
                description,
                priority,
                dueDate,
                status);

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
                new InputStreamReader(exchange.getRequestBody()));

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