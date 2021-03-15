package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListItemService;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListService;
import is.hi.hbv501g.dotoo.DoToo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoListController {

    private TodoListService todoListService;
    private TodoListItemService itemService;
    private UserService userService;

    @Autowired
    public TodoListController(TodoListService todoListService,
                              TodoListItemService itemService,
                              UserService userService) {
        this.todoListService = todoListService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @RequestMapping("/todolist")
    @ResponseBody
    public List<TodoList> getTodoLists(@Valid @RequestBody User user) {
        User loggedInUser = userService.login(user);
        if (loggedInUser != null) {
            System.out.println("Notandi til, nafn notanda: " + loggedInUser.getName());
            //session.setAttribute("loggedInUser", exists);
            return todoListService.findByUser(loggedInUser);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login unsuccessful");
        }
    }

    @RequestMapping("/favoritetodolists")
    public List<TodoList> getFavoriteTodoLists(@Valid @RequestBody User user) {
        return todoListService.findByUserAndFavorite(user, true);
    }

    @RequestMapping(value = "/deletelists", method = RequestMethod.POST)
    public ResponseEntity<?> deleteTodoLists(@Valid @RequestBody User user, @RequestBody List<TodoList> todoLists) {
        System.out.println("er í delete lists");
        List<TodoList> allLists = todoListService.findByUser(user);

        for (TodoList list : allLists) {
            System.out.println("currently iterating" + list);
            System.out.println("contains? " + todoLists.contains(list));
            if(todoLists.contains(list)) todoListService.delete(list);
        }

        System.out.println("á að vera búið að deleta");
        return (ResponseEntity<?>) ResponseEntity.noContent();


    }

    @RequestMapping(value="/deletelist", method = RequestMethod.POST)
    public String deleteTodoList(@RequestParam(value = "id") long id) {
        TodoList todolist = todoListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo list id"));
        return "redirect:/todolist";
    }

    @RequestMapping(value = "/additem", method = RequestMethod.POST)
    public String addItem(@RequestParam(value = "description") String description,
                          @RequestParam(value = "listId") long id) {
        Optional<TodoList> todolist = todoListService.findById(id);
        todoListService.addItem(todolist.get(), new TodoListItem(description, false, todolist.get()));
        return "redirect:/todolist";
    }

    @RequestMapping(value = "/deleteitem", method = RequestMethod.POST)
    public String deleteItem(@RequestParam(value = "id") long id) {
        TodoListItem item = itemService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item id"));
        itemService.delete(item);
        return "redirect:/todolist";
    }

    @RequestMapping(value = "/newtodolist", method = RequestMethod.POST)
    public String newTodoList(@RequestParam(value = "name") String name, @RequestParam(value = "color") String color, boolean isFavorite, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        TodoList todolist = new TodoList(name, color, sessionUser, isFavorite);
        todoListService.save(todolist);
        return "redirect:/todolist";
    }


    @RequestMapping(value = "/itemchecked", method = RequestMethod.POST)
    public String itemChecked(@RequestParam(value = "id") long id,
                              @RequestParam(value = "checked") boolean checked) {
        TodoListItem item = itemService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item id"));
        item.setChecked(checked);
        itemService.save(item);
        return "redirect:/todolist";
    }

    @RequestMapping(value = "/setFavorite", method = RequestMethod.POST)
    public String setFavorite(@RequestParam(value = "id") long id,
                               @RequestParam(value = "favorite") boolean isFavorite) {
        TodoList todoList = todoListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo list id"));
        todoList.setFavorite(isFavorite);
        todoListService.save(todoList);
        return "redirect:/todolist";
    }
}
