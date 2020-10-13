package is.hi.hbv501g.dotoo.DoToo.Services;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;

import java.util.List;
import java.util.Optional;

public interface TodoListService {

    TodoList save(TodoList todolist);
    void delete(TodoList todolist);
    List<TodoList> findAll();
    Optional<TodoList> findById(long id);
    TodoList addItem(TodoList list, TodoListItem item);
}
