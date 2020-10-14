package is.hi.hbv501g.dotoo.DoToo.Repositories;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoListItemRepository extends JpaRepository<TodoListItem, Long> {
    TodoListItem save(TodoListItem todoListItem);
    void delete(TodoListItem todolist);
    List<TodoListItem> findAll();
    Optional<TodoListItem> findById(long id);
}
