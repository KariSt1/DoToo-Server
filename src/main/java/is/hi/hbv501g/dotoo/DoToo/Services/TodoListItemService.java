package is.hi.hbv501g.dotoo.DoToo.Services;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;

import java.util.List;
import java.util.Optional;

public interface TodoListItemService {

    TodoListItem save(TodoListItem item);
    void delete(TodoListItem item);
    List<TodoListItem> findByTodoList(TodoList todoList);
    Optional<TodoListItem> findById(long id);
}
