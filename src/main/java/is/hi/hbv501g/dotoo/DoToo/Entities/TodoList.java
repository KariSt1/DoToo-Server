package is.hi.hbv501g.dotoo.DoToo.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @JsonBackReference
    @ManyToOne
    private User user;
    private boolean favorite;

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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public TodoList(String name, String color, User user, boolean isFavorite) {
        this.name = name;
        this.color = color;
        this.user = user;
        this.favorite = isFavorite;
    }

    public TodoList() {
    }
}
