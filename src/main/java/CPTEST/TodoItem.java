package CPTEST;

import jakarta.persistence.*;

@Entity
@Table(name = "todo_items")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String category;

    @Column
    private String priority;

    @Column
    private String status;

    public TodoItem() {
    }

    public TodoItem(String title) {
        this.title = title;
        this.category = "Maintenance";
        this.priority = "Routine";
        this.status = "Assigned";
    }

    public TodoItem(String title, String category, String priority, String status) {
        this.title = title;
        this.category = category;
        this.priority = priority;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + " - " + title + " [" + category + " | " + priority + " | " + status + "]";
    }
}