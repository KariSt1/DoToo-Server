package is.hi.hbv501g.dotoo.DoToo.Services.Implementations;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Repositories.TodoListItemRepository;
import is.hi.hbv501g.dotoo.DoToo.Repositories.TodoListRepository;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoListServiceImplementation implements TodoListService {

    TodoListRepository listRepository;
    TodoListItemRepository itemRepository;

    @Autowired
    public TodoListServiceImplementation(TodoListRepository todoListRepository, TodoListItemRepository itemRepository) {
        this.listRepository = todoListRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public TodoList save(TodoList todolist) {
        return listRepository.save(todolist);
    }

    @Override
    public void delete(TodoList todolist) {
        listRepository.delete(todolist);
    }

    @Override
    public List<TodoList> findAll() {
        return listRepository.findAll();
    }

    @Override
    public Optional<TodoList> findById(long id) {
        return listRepository.findById(id);
    }

    @Override
    public TodoList addItem(TodoList list, TodoListItem item) {
       List<TodoListItem> currentItems = list.getItems();
       itemRepository.save(item);
       currentItems.add(item);
       list.setItems(currentItems);
       return listRepository.save(list);
    }
}
