package is.hi.hbv501g.dotoo.DoToo.Services.Implementations;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Repositories.TodoListItemRepository;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoListItemServiceImplementation implements TodoListItemService {

    TodoListItemRepository itemRepository;

    @Override
    public TodoListItem save(TodoListItem item) {
        return itemRepository.save(item);
    }

    @Override
    public void delete(TodoListItem item) {
        itemRepository.delete(item);
    }

    @Override
    public List<TodoListItem> findByTodoList(TodoList todoList) {
        return itemRepository.findByTodoList(todoList);
    }

    @Override
    public Optional<TodoListItem> findById(long id) {
        return itemRepository.findById(id);
    }
}
