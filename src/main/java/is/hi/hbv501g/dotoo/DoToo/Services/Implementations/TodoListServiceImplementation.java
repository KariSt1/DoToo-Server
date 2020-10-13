package is.hi.hbv501g.dotoo.DoToo.Services.Implementations;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Repositories.TodoListRepository;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoListServiceImplementation implements TodoListService {

    TodoListRepository repository;

    @Autowired
    public TodoListServiceImplementation(TodoListRepository todoListRepository) {this.repository = todoListRepository;}

    @Override
    public TodoList save(TodoList todolist) {
        return repository.save(todolist);
    }

    @Override
    public void delete(TodoList todolist) {
        repository.delete(todolist);
    }

    @Override
    public List<TodoList> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TodoList> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public TodoList addItem(TodoList list, TodoListItem item) {
       List<TodoListItem> currentItems = list.getItems();
       currentItems.add(item);
       list.setItems(currentItems);
       return repository.save(list);
    }
}
