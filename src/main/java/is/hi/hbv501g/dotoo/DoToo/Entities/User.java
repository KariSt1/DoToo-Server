package is.hi.hbv501g.dotoo.DoToo.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    public String username;

    public String name;
    @NotNull
    public String password;

    @ElementCollection
    public List<String> friends = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TodoList> todoLists = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Event> events = new ArrayList<>();

    private int finishedTodoLists;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getFriends() { return friends; }

    public void addFriend(String username) { friends.add(username); }

    public int getFinishedTodoLists() {
        return finishedTodoLists;
    }

    public void setFinishedTodoLists(int finishedTodoLists) {
        this.finishedTodoLists = finishedTodoLists;
    }

    public User(String username, String name, @NotNull String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public User(String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }
}
