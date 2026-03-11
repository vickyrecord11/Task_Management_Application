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
            System.out.println("8 Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("Enter name:");
                    String name = sc.nextLine();

                    System.out.println("Enter description:");
                    String description = sc.nextLine();

                    System.out.println("Enter priority (HIGH/MEDIUM/LOW):");
                    Priority priority = Priority.valueOf(sc.nextLine().toUpperCase());

                    System.out.println("Enter status (TO_DO/IN_PROGRESS/DONE):");
                    Status status = Status.valueOf(sc.nextLine().toUpperCase());

                    service.createTask(name, description, priority, status);
                    break;

                case 2:
                    System.out.println("Enter task Id to delete:");
                    int deleteId = sc.nextInt();

                    service.deleteTask(deleteId);
                    break;

                case 3:
                    System.out.println("Enter task Id to update:");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    if (!service.taskExists(updateId)) {

                        System.out.println("Invalid Task ID");
                        break;
                    }
                    System.out.println("What do you want to update?");
                    System.out.println("1 Name");
                    System.out.println("2 Description");
                    System.out.println("3 Priority");
                    System.out.println("4 Status");

                    int fieldChoice = sc.nextInt();
                    sc.nextLine();

                    String field = "";

                    switch (fieldChoice) {

                        case 1:
                            field = "name";
                            break;

                        case 2:
                            field = "description";
                            break;

                        case 3:
                            field = "priority";
                            break;

                        case 4:
                            field = "status";
                            break;

                        default:
                            System.out.println("Invalid choice");
                            break;
                    }

                    System.out.println("Enter new value:");
                    String newValue = sc.nextLine();

                    service.updateTask(updateId, field, newValue);

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

            System.out.println("\n=============================\n");
        }
    }
}
