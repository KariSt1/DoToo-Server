package is.hi.hbv501g.dotoo.DoToo.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String color;

    @OneToMany(mappedBy = "todoList", cascade=CascadeType.ALL)
    private List<TodoListItem> items = new ArrayList<>();

    @ManyToOne
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<TodoListItem> getItems() {
        return items;
    }

    public void setItems(List<TodoListItem> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TodoList(String name, String color, User user) {
        this.name = name;
        this.color = color;
        this.user = user;
    }

    public TodoList() {
    }
}
