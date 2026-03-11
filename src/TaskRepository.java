import java.util.ArrayList;
import java.io.*;

public class TaskRepository {
    ArrayList<Task> tasks = new ArrayList<>();
    private final String FILE_NAME = "tasks.txt";

    private int nextId = 1;

    public TaskRepository() {
        loadTasksFromFile();
    }

    private void loadTasksFromFile() {

        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                file.createNewFile();
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String description = parts[2];
                    Priority priority = Priority.valueOf(parts[3]);
                    Status status = Status.valueOf(parts[4]);

                    Task task = new Task(id, name, description, priority, status);
                    tasks.add(task);

                    if (id >= nextId) {
                        nextId = id + 1;
                    }
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTaskToFile(task);
    }

    public int generateId() {
        return nextId++;
    }

    private void saveTaskToFile(Task task) {

        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            fw.write(task.getId() + "|" +
                    task.getName() + "|" +
                    task.getDescription() + "|" +
                    task.getPriority() + "|" +
                    task.getStatus() + "\n");

            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {

        boolean found = false;

        for (int i = 0; i < tasks.size(); i++) {

            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                found = true;
                break;
            }
        }
        if (found) {
            rewriteFile();
            System.out.println("Task Deleted Successfully");

        } else {

            System.out.println("Invalid Task ID");
        }
    }

    public void updateTask(int id, String field, String newValue) {

        boolean found = false;
        for (Task task : tasks) {

            if (task.getId() == id) {

                found = true;

                switch (field.toLowerCase()) {

                    case "name":
                        task.setName(newValue);
                        break;

                    case "description":
                        task.setDescription(newValue);
                        break;

                    case "priority":
                        task.setPriority(Priority.valueOf(newValue.toUpperCase()));
                        break;

                    case "status":
                        task.setStatus(Status.valueOf(newValue.toUpperCase()));
                        break;

                    default:
                        System.out.println("Invalid field");
                        return;
                }

                rewriteFile();

                System.out.println(field + " updated successfully");

                return;
            }
        }

        if (!found) {

            System.out.println("Invalid Task ID");
        }
    }

    public boolean taskExists(int id) {

        for (Task task : tasks) {

            if (task.getId() == id) {
                return true;
            }
        }

        return false;
    }

    public void rewriteFile() {

        try {
            FileWriter fw = new FileWriter(FILE_NAME);

            for (Task task : tasks) {
                fw.write(task.getId() + "|" +
                        task.getName() + "|" +
                        task.getDescription() + "|" +
                        task.getPriority() + "|" +
                        task.getStatus() + "\n");
            }

            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

}
