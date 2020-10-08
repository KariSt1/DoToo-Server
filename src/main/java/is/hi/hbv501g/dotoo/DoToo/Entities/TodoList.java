package is.hi.hbv501g.dotoo.DoToo.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String color;
    @OneToMany
    private List<TodoListItem> items;

}
