import java.util.ArrayList;
import java.io.*;

public class TaskRepository {
    ArrayList<Task> tasks = new ArrayList<>();
    private final String FILE_NAME = "tasks.txt";

    public TaskRepository(){
        loadTasksFromFile();
    }

    private void loadTasksFromFile(){

        try{
            File file = new File(FILE_NAME);

            if(!file.exists()){
                file.createNewFile();
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while((line = br.readLine()) != null){

                String[] parts = line.split(",");

                System.out.println("Title");
                String name = parts[0];
                String description = parts[1];
                String priority = parts[2];
                String status = parts[3];

                Task task = new Task(name, description, priority, status);
                tasks.add(task);
            }

            br.close();
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTaskToFile(task);
    }

    private void saveTaskToFile(Task task){

        try{
            FileWriter fw = new FileWriter(FILE_NAME, true);
            fw.write(task.getName() + "," +
                    task.getDescription() + "," +
                    task.getPriority() + "," +
                    task.getStatus() + "\n");

            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(String name) {

        for (int i = 0; i < tasks.size(); i++) {

            if (tasks.get(i).getName().equalsIgnoreCase(name)) {
                tasks.remove(i);
                break;
            }
        }
        rewriteFile();
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
        rewriteFile();
    }

    public void rewriteFile(){

        try{
            FileWriter fw = new FileWriter(FILE_NAME);

            for(Task task : tasks){
            fw.write(task.getName() + "," +
                    task.getDescription() + "," +
                    task.getPriority() + "," +
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
