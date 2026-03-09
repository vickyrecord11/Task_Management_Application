public class Task {

    String name;
    String description;
    String priority;
    String status;

    Task(String name, String description, String priority, String status) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "Name: " + name +
                ", Description: " + description +
                ", Priority: " + priority +
                ", Status: " + status;
    }
}
