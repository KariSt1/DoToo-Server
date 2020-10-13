package is.hi.hbv501g.dotoo.DoToo.Entities;

import javax.persistence.*;

@Entity
public class TodoListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
    private boolean isDone;

    @ManyToOne
    private TodoList todoList;

    public TodoListItem() {
    }

    public TodoListItem(String description, boolean isDone, TodoList todoList) {
        this.description = description;
        this.isDone = isDone;
        this.todoList = todoList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

}
