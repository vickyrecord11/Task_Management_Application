import java.util.List;

public class TaskServices {

    private final TaskRepository repository;

    public TaskServices(StorageMode mode) {
        repository = new TaskRepository(mode);
    }

    public void createTask(String name, String category, String description, Priority priority, String dueDate, Status status) {

        int id = repository.generateId();

        Task task = new Task(id, name, category, description, priority, dueDate, status);

        repository.addTask(task);

        System.out.println("Task Created Successfully");
    }

    public void deleteTask(int id) {

        if (!repository.taskExists(id)) {
            throw new RuntimeException("Task not found");
        }
        
        repository.deleteTask(id);
    }

    public void updateTask(
        int id,
        String name,
        String category,
        String description,
        Priority priority,
        String dueDate,
        Status status) {

    if (!repository.taskExists(id)) {

        throw new RuntimeException("Task not found");
    }

    repository.updateTask(
            id,
            name,
            category,
            description,
            priority,
            dueDate,
            status);
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

    public List<Task> getTasksByPage(int page, int size) {

    List<Task> tasks = repository.getAllTasks();

    int startIndex = (page - 1) * size;

    int endIndex = Math.min(
            startIndex + size,
            tasks.size());

    if (startIndex >= tasks.size()) {
        return List.of();
    }

    return tasks.subList(startIndex, endIndex);
}
}