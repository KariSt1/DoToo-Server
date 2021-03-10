package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListItemService;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @Autowired
    public TodoListController(TodoListService todoListService,
                              TodoListItemService itemService) {
        this.todoListService = todoListService;
        this.itemService = itemService;
    }

    @RequestMapping(value="/todolist", method = RequestMethod.POST)
    @ResponseBody
    public List<TodoList> postTodoLists(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Fengum villu í result");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }
        System.out.println("User: " + user.getName());
        return todoListService.findByUser(user);
    }

    @RequestMapping("/favoritetodolists")
    public List<TodoList> getFavoriteTodoLists(@Valid @RequestBody User user) {
        return todoListService.findByUserAndFavorite(user, true);
    }

    @RequestMapping(value = "/deletelist", method = RequestMethod.POST)
    public String deleteTodoList(@RequestParam(value = "id") long id) {
        TodoList todolist = todoListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo list id"));
        todoListService.delete(todolist);
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
