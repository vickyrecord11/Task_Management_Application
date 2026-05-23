public class Task {

    int id;
    String name;
    String category;
    String description;
    Priority priority;
    String dueDate;
    Status status;

    Task(int id, String name, String category, String description, Priority priority, String dueDate, Status status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getDueDate() {
        return dueDate;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                ", Name: " + name +
                ", Category: " + category +
                ", Description: " + description +
                ", Priority: " + priority +
                ", DueDate: " + dueDate +
                ", Status: " + status;
    }
}
