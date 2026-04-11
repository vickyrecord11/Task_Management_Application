import java.util.List;

public class TaskServices {

    private TaskRepository repository;

    public TaskServices(StorageMode mode) {
        repository = new TaskRepository(mode);
    }

    public void createTask(String name, String description, Priority priority, Status status) {

        int id = repository.generateId();

        Task task = new Task(id, name, description, priority, status);

        repository.addTask(task);

        System.out.println("Task Created Successfully");
    }

    public void deleteTask(int id) {

        if (!repository.taskExists(id)) {
            throw new RuntimeException("Task not found");
        }
        
        repository.deleteTask(id);
    }

    public void updateTask(int id, String field, String newValue) {
        
        if (!repository.taskExists(id)) {
            throw new RuntimeException("Task not found");
        }

        repository.updateTask(id, field, newValue);
    }

    public boolean taskExists(int id) {
        return repository.taskExists(id);
    }

    public void viewTasks() {

        List<Task> tasks = repository.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("No Tasks Available");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public List<Task> getAllTasks() {
        return repository.getAllTasks();
    }
}