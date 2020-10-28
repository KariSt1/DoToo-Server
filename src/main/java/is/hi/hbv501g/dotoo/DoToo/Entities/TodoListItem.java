package is.hi.hbv501g.dotoo.DoToo.Entities;

import javax.persistence.*;

@Entity
public class TodoListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
    private boolean checked;

    @ManyToOne
    private TodoList todoList;

    public TodoListItem() {
    }

    public TodoListItem(String description, boolean checked, TodoList todoList) {
        this.description = description;
        this.checked = checked;
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

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean done) {
        checked = done;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

}
