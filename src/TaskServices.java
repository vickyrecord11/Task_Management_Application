import java.util.List;

public class TaskServices {

    private TaskRepository repository = new TaskRepository();

    public void createTask(String name, String description, Priority priority, Status status) {

        int id = repository.generateId();

        Task task = new Task(id, name, description, priority, status);

        repository.addTask(task);

        System.out.println("Task Created Successfully");
    }

    public void deleteTask(int id) {

        repository.deleteTask(id);

    }

    public void updateTask(int id, String field, String newValue) {

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

    public void sortByName() {

        List<Task> tasks = repository.getAllTasks();

        int n = tasks.size();

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (tasks.get(j).getName().compareTo(tasks.get(j + 1).getName()) > 0) {

                    Task temp = tasks.get(j);
                    tasks.set(j, tasks.get(j + 1));
                    tasks.set(j + 1, temp);
                }
            }
        }

        repository.rewriteFile();
        System.out.println("Tasks Sorted By Name");
    }

    public void sortByPriority() {

        List<Task> tasks = repository.getAllTasks();

        int n = tasks.size();

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                int p1 = getPriorityValue(tasks.get(j).getPriority());
                int p2 = getPriorityValue(tasks.get(j + 1).getPriority());

                if (p1 > p2) {

                    Task temp = tasks.get(j);
                    tasks.set(j, tasks.get(j + 1));
                    tasks.set(j + 1, temp);

                }
            }
        }

        repository.rewriteFile();
        System.out.println("Tasks Sorted By Priority (HIGH → MEDIUM → LOW)");
    }

    private int getPriorityValue(Priority priority) {

        if (priority == Priority.HIGH) {
            return 1;
        }

        if (priority == Priority.MEDIUM) {
            return 2;
        }

        if (priority == Priority.LOW) {
            return 3;
        }

        return 4;
    }

    public void sortByStatus() {

        List<Task> tasks = repository.getAllTasks();

        int n = tasks.size();

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                int s1 = getStatusValue(tasks.get(j).getStatus());
                int s2 = getStatusValue(tasks.get(j + 1).getStatus());

                if (s1 > s2) {

                    Task temp = tasks.get(j);
                    tasks.set(j, tasks.get(j + 1));
                    tasks.set(j + 1, temp);

                }
            }
        }

        repository.rewriteFile();
        System.out.println("Tasks Sorted By Status (IN_PROGRESS → TO_DO → DONE)");
    }

    private int getStatusValue(Status status) {

        if (status == Status.IN_PROGRESS) {
            return 1;
        }

        if (status == Status.TO_DO) {
            return 2;
        }

        if (status == Status.DONE) {
            return 3;
        }

        return 4;
    }
}
