import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        TaskServices service = new TaskServices();

        while (true) {
            System.out.println("1 Create Task");
            System.out.println("2 Delete Task");
            System.out.println("3 Update Task");
            System.out.println("4 View Tasks");
            System.out.println("5 Sort by Priority");
            System.out.println("6 Sort by Name");
            System.out.println("7 Sort by Status");
            System.out.println("5 Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("Enter name:");
                    String name = sc.nextLine();

                    System.out.println("Enter description:");
                    String description = sc.nextLine();

                    System.out.println("Enter priority (HIGH / MEDIUM / LOW):");
                    String priority = sc.nextLine();

                    System.out.println("Enter status (IN_PROGRESS / TO_DO / DONE):");
                    String status = sc.nextLine();

                    service.createTask(name, description, priority, status);
                    break;

                case 2:
                    System.out.println("Enter task name to delete:");
                    String deleteName = sc.nextLine();

                    service.deleteTask(deleteName);
                    break;

                case 3:
                    System.out.println("Enter task name to update:");
                    String updateName = sc.nextLine();

                    System.out.println("Enter new description:");
                    String newDescription = sc.nextLine();

                    System.out.println("Enter new priority (HIGH / MEDIUM / LOW):");
                    String newPriority = sc.nextLine();

                    System.out.println("Enter new status (IN_PROGRESS / TO_DO / DONE):");
                    String newStatus = sc.nextLine();

                    service.updateTask(updateName, newDescription, newPriority, newStatus);
                    break;

                case 4:
                    service.viewTasks();
                    break;

                case 5:
                    service.sortByPriority();
                    service.viewTasks();
                    break;

                case 6:
                    service.sortByName();
                    service.viewTasks();
                    break;

                case 7:
                    service.sortByStatus();
                    service.viewTasks();
                    break;

                case 8:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
