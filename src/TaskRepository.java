import java.io.*;
import java.util.ArrayList;

public class TaskRepository {

    private final StorageMode mode;

    ArrayList<Task> tasks = new ArrayList<>();

    private final String DATA_FILE = "data/tasks.txt";

    private int nextId = 1;

    private static final int NAME_SIZE = 40;
    private static final int DESC_SIZE = 100;
    private static final int PRIORITY_SIZE = 10;
    private static final int STATUS_SIZE = 15;

    public TaskRepository(StorageMode mode) {
        this.mode = mode;

        // ✅ Ensure data folder exists
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdir();
        }

        if (mode == StorageMode.DISC) {
            loadTasksFromFile();
        }
    }

    // ================= LOAD =================

    private void loadTasksFromFile() {

        try (RandomAccessFile file = new RandomAccessFile(DATA_FILE, "rw")) {

            while (file.getFilePointer() < file.length()) {

                int id = file.readInt();

                String name = readFixedString(NAME_SIZE, file).trim();
                String desc = readFixedString(DESC_SIZE, file).trim();
                String priority = readFixedString(PRIORITY_SIZE, file).trim();
                String status = readFixedString(STATUS_SIZE, file).trim();

                if (id != -1) {

                    Task task = new Task(id, name, desc,
                            Priority.valueOf(priority),
                            Status.valueOf(status));

                    tasks.add(task);   // ✅ FIXED

                    if (id >= nextId) {
                        nextId = id + 1;
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading tasks", e);
        }
    }

    // ================= ADD =================

    public void addTask(Task task) {

        if (mode == StorageMode.MEMORY) {
            tasks.add(task);
            return;
        }

        try (RandomAccessFile file = new RandomAccessFile(DATA_FILE, "rw")) {

            long position = file.length();

            file.seek(position);

            file.writeInt(task.getId());

            writeFixedString(task.getName(), NAME_SIZE, file);
            writeFixedString(task.getDescription(), DESC_SIZE, file);
            writeFixedString(task.getPriority().name(), PRIORITY_SIZE, file);
            writeFixedString(task.getStatus().name(), STATUS_SIZE, file);

            tasks.add(task); // optional cache

        } catch (Exception e) {
            throw new RuntimeException("Error adding task", e);
        }
    }

    public int generateId() {
        return nextId++;
    }

    // ================= DELETE =================

    public void deleteTask(int id) {

        if (mode == StorageMode.MEMORY) {
            tasks.removeIf(t -> t.getId() == id);
            System.out.println("Task Deleted Successfully");
            return;
        }

        ArrayList<Task> list = getAllTasks();
        list.removeIf(t -> t.getId() == id);

        rewriteFileFromList(list);

        System.out.println("Task Deleted Successfully");
    }

    // ================= UPDATE =================

    public void updateTask(int id, String field, String newValue) {

        if (mode == StorageMode.MEMORY) {

            for (Task t : tasks) {
                if (t.getId() == id) {
                    applyUpdate(t, field, newValue);
                }
            }

            System.out.println("Task Updated Successfully");
            return;
        }

        ArrayList<Task> list = getAllTasks();

        for (Task t : list) {
            if (t.getId() == id) {
                applyUpdate(t, field, newValue);
            }
        }

        rewriteFileFromList(list);

        System.out.println("Task Updated Successfully");
    }

    // ================= HELPER =================

    private void applyUpdate(Task t, String field, String newValue) {

        try {

            if (field.equalsIgnoreCase("name")) {
                t.setName(newValue);
            }

            else if (field.equalsIgnoreCase("description")) {
                t.setDescription(newValue);
            }

            else if (field.equalsIgnoreCase("priority")) {
                t.setPriority(Priority.valueOf(newValue.toUpperCase()));
            }

            else if (field.equalsIgnoreCase("status")) {
                t.setStatus(Status.valueOf(newValue.toUpperCase()));
            }

            else {
                System.out.println("Invalid field name");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid value for " + field);
        }
    }

    private void rewriteFileFromList(ArrayList<Task> list) {

        try (RandomAccessFile file = new RandomAccessFile(DATA_FILE, "rw")) {

            file.setLength(0);

            for (Task task : list) {

                file.writeInt(task.getId());

                writeFixedString(task.getName(), NAME_SIZE, file);
                writeFixedString(task.getDescription(), DESC_SIZE, file);
                writeFixedString(task.getPriority().name(), PRIORITY_SIZE, file);
                writeFixedString(task.getStatus().name(), STATUS_SIZE, file);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error rewriting file", e);
        }
    }

    // ================= READ =================

    public ArrayList<Task> getAllTasks() {

        if (mode == StorageMode.MEMORY) {
            return tasks;
        }

        return readAllFromFile();
    }

    private ArrayList<Task> readAllFromFile() {

        ArrayList<Task> list = new ArrayList<>();

        try (RandomAccessFile file = new RandomAccessFile(DATA_FILE, "r")) {

            while (file.getFilePointer() < file.length()) {

                int id = file.readInt();

                String name = readFixedString(NAME_SIZE, file).trim();
                String desc = readFixedString(DESC_SIZE, file).trim();
                String priority = readFixedString(PRIORITY_SIZE, file).trim();
                String status = readFixedString(STATUS_SIZE, file).trim();

                if (id != -1) {
                    list.add(new Task(id, name, desc,
                            Priority.valueOf(priority),
                            Status.valueOf(status)));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error reading file", e);
        }

        return list;
    }

    // ================= EXISTS =================

    public boolean taskExists(int id) {

        if (mode == StorageMode.MEMORY) {
            return tasks.stream().anyMatch(t -> t.getId() == id);
        }

        return getAllTasks().stream().anyMatch(t -> t.getId() == id);
    }

    // ================= FIXED STRING =================

    private void writeFixedString(String s, int size, RandomAccessFile file) throws IOException {

        StringBuilder sb = new StringBuilder(s);

        if (sb.length() > size) {
            sb.setLength(size);
        } else {
            while (sb.length() < size) {
                sb.append(" ");
            }
        }

        file.writeChars(sb.toString());
    }

    private String readFixedString(int size, RandomAccessFile file) throws IOException {

        char[] chars = new char[size];

        for (int i = 0; i < size; i++) {
            chars[i] = file.readChar();
        }

        return new String(chars);
    }
}