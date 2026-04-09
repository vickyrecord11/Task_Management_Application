import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Select Mode:");
        System.out.println("1 MEMORY");
        System.out.println("2 DISC");

        int modeChoice = sc.nextInt();
        sc.nextLine();

        StorageMode mode = null;

        if (modeChoice == 1) {
            mode = StorageMode.MEMORY;
        } else if( modeChoice == 2) {
            mode = StorageMode.DISC;
        } else{
            System.out.println("Invalid choice");
        }

        TaskServices service = new TaskServices(mode);

        while (true) {

            System.out.println("1 Create Task");
            System.out.println("2 Delete Task");
            System.out.println("3 Update Task");
            System.out.println("4 View Tasks");
            System.out.println("5 Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("Enter name:");
                    String name = sc.nextLine();

                    System.out.println("Enter description:");
                    String description = sc.nextLine();

                    System.out.println("Enter priority (HIGH/MEDIUM/LOW):");
                    Priority priority;
                    try {
                        priority = Priority.valueOf(sc.nextLine().toUpperCase());
                    } catch (Exception e) {
                        System.out.println("Invalid Priority");
                        break;
                    }

                    System.out.println("Enter status (TO_DO/IN_PROGRESS/DONE):");
                    Status status;
                    try {
                        status = Status.valueOf(sc.nextLine().toUpperCase());
                    } catch (Exception e) {
                        System.out.println("Invalid Status");
                        break;
                    }

                    service.createTask(name, description, priority, status);
                    break;

                case 2:
                    System.out.println("Enter task Id to delete:");
                    int deleteId = sc.nextInt();
                    sc.nextLine();

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

                    String field;

                    if (fieldChoice == 1) field = "name";
                    else if (fieldChoice == 2) field = "description";
                    else if (fieldChoice == 3) field = "priority";
                    else if (fieldChoice == 4) field = "status";
                    else {
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
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }

            System.out.println("\n=============================\n");
        }
    }
}