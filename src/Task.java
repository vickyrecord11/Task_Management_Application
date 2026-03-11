public class Task {

    int id;
    String name;
    String description;
    Priority priority;
    Status status;

    Task(int id, String name, String description, Priority priority, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString() {
        return "Id: " + id +
                ", Name: " + name +
                ", Description: " + description +
                ", Priority: " + priority +
                ", Status: " + status;
    }
}
