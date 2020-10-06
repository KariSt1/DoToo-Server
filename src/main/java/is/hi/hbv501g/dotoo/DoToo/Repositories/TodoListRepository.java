package is.hi.hbv501g.dotoo.DoToo.Repositories;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    TodoList save(TodoList todolist);
    void delete(TodoList todolist);
    List<TodoList> findAll();
    Optional<TodoList> findById(long id);

}
