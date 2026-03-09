import java.util.ArrayList;

public class TaskRepository {
    ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(String name) {

        for (int i = 0; i < tasks.size(); i++) {

            if (tasks.get(i).getName().equalsIgnoreCase(name)) {
                tasks.remove(i);
                break;
            }
        }
    }

    public void updateTask(String name, String description, String priority, String status) {

        for (Task task : tasks) {

            if (task.getName().equalsIgnoreCase(name)) {

                task.setDescription(description);
                task.setPriority(priority);
                task.setStatus(status);
                break;
            }
        }
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

}
