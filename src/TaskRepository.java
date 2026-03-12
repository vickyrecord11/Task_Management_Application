import java.util.ArrayList;
import java.io.*;

public class TaskRepository {
    ArrayList<Task> tasks = new ArrayList<>();
    private final String DATA_FILE = "data/tasks.txt";
    private final String INDEX_FILE = "data/index.txt";

    private int nextId = 1;

    private static final int NAME_SIZE = 40;
    private static final int DESC_SIZE = 100;
    private static final int PRIORITY_SIZE = 10;
    private static final int STATUS_SIZE = 15;

    private static final int RECORD_SIZE = 4 +
            (NAME_SIZE * 2) +
            (DESC_SIZE * 2) +
            (PRIORITY_SIZE * 2) +
            (STATUS_SIZE * 2);

    public TaskRepository() {
        loadTasksFromFile();
    }

    private void loadTasksFromFile() {

        try {

            RandomAccessFile file = new RandomAccessFile(DATA_FILE, "rw");

            while (file.getFilePointer() < file.length()) {

                long position = file.getFilePointer();

                int id = file.readInt();

                String name = readFixedString(NAME_SIZE, file).trim();
                String desc = readFixedString(DESC_SIZE, file).trim();
                String priority = readFixedString(PRIORITY_SIZE, file).trim();
                String status = readFixedString(STATUS_SIZE, file).trim();

                if (id != -1) {

                    Task task = new Task(id, name, desc, Priority.valueOf(priority), Status.valueOf(status));

                    tasks.add(task);

                    addIndex(id, position);

                    if (id >= nextId) {
                        nextId = id + 1;
                    }
                }
            }
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTask(Task task) {

        try {

            RandomAccessFile file = new RandomAccessFile(DATA_FILE, "rw");

            long position = file.length();

            file.seek(position);

            file.writeInt(task.getId());

            writeFixedString(task.getName(), NAME_SIZE, file);
            writeFixedString(task.getDescription(), DESC_SIZE, file);
            writeFixedString(task.getPriority().name(), PRIORITY_SIZE, file);
            writeFixedString(task.getStatus().name(), STATUS_SIZE, file);

            file.close();

            addIndex(task.getId(), position);

            tasks.add(task);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int generateId() {
        return nextId++;
    }

    private void addIndex(int id, long position) {

        try {

            RandomAccessFile index = new RandomAccessFile(INDEX_FILE, "rw");

            index.seek(index.length());

            index.writeInt(id);
            index.writeLong(position);

            index.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getPositionFromIndex(int id) {

        try {

            RandomAccessFile index = new RandomAccessFile(INDEX_FILE, "r");

            while (index.getFilePointer() < index.length()) {

                int storedId = index.readInt();
                long position = index.readLong();

                if (storedId == id) {
                    index.close();
                    return position;
                }
            }

            index.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void rewriteFile() {

        try {

            RandomAccessFile file = new RandomAccessFile(DATA_FILE, "rw");

            file.setLength(0);

            for (Task task : tasks) {

                long position = file.getFilePointer();

                file.writeInt(task.getId());

                writeFixedString(task.getName(), NAME_SIZE, file);
                writeFixedString(task.getDescription(), DESC_SIZE, file);
                writeFixedString(task.getPriority().name(), PRIORITY_SIZE, file);
                writeFixedString(task.getStatus().name(), STATUS_SIZE, file);

            }

            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {

        try {

            long position = getPositionFromIndex(id);

            if (position == -1) {
                System.out.println("Invalid Task ID");
                return;
            }

            RandomAccessFile file = new RandomAccessFile(DATA_FILE, "rw");

            file.seek(position);

            file.writeInt(-1);

            file.close();

            tasks.removeIf(t -> t.getId() == id);

            System.out.println("Task Deleted Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTask(int id, String field, String newValue) {

        try {

            long position = getPositionFromIndex(id);

            if (position == -1) {
                System.out.println("Invalid Task ID");
                return;
            }

            RandomAccessFile file = new RandomAccessFile(DATA_FILE, "rw");

            if (field.equalsIgnoreCase("name")) {

                file.seek(position + 4);
                writeFixedString(newValue, NAME_SIZE, file);

            } else if (field.equalsIgnoreCase("description")) {

                file.seek(position + 4 + (NAME_SIZE * 2));
                writeFixedString(newValue, DESC_SIZE, file);

            } else if (field.equalsIgnoreCase("priority")) {

                file.seek(position + 4 + (NAME_SIZE * 2) + (DESC_SIZE * 2));
                writeFixedString(newValue.toUpperCase(), PRIORITY_SIZE, file);

            } else if (field.equalsIgnoreCase("status")) {

                file.seek(position + 4 + (NAME_SIZE * 2) + (DESC_SIZE * 2) + (PRIORITY_SIZE * 2));
                writeFixedString(newValue.toUpperCase(), STATUS_SIZE, file);
            }

            file.close();

            for (Task t : tasks) {

                if (t.getId() == id) {

                    if (field.equalsIgnoreCase("name"))
                        t.setName(newValue);

                    if (field.equalsIgnoreCase("description"))
                        t.setDescription(newValue);

                    if (field.equalsIgnoreCase("priority"))
                        t.setPriority(Priority.valueOf(newValue.toUpperCase()));

                    if (field.equalsIgnoreCase("status"))
                        t.setStatus(Status.valueOf(newValue.toUpperCase()));
                }
            }

            System.out.println("Task Updated Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

        for (int i = 0; i < size; i++)
            chars[i] = file.readChar();

        return new String(chars);
    }

    public boolean taskExists(int id) {

        for (Task task : tasks) {

            if (task.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

}
