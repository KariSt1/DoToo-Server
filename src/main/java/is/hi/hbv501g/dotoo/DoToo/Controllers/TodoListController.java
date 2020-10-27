package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListItemService;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListService;
import is.hi.hbv501g.dotoo.DoToo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class TodoListController {

    private TodoListService todoListService;
    private TodoListItemService itemService;
    private UserService userService;

    @Autowired
    public TodoListController(TodoListService todoListService,
                              UserService userService,
                              TodoListItemService itemService) {
        this.todoListService = todoListService;
        this.userService = userService;
        this.itemService = itemService;
    }

    @RequestMapping("/todolist")
    public String TodoListPage(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if(sessionUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("todolists", todoListService.findByUser(sessionUser));
        return "TodoListPage";
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String deleteTodoList(@PathVariable("id") long id, Model model) {
        TodoList todolist = todoListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo list id"));
        todoListService.delete(todolist);
        model.addAttribute("todolists", todoListService.findAll());
        return "TodoListPage";
    }

    @RequestMapping(value = "/additem", method = RequestMethod.POST)
    public String addItem(@RequestParam(value = "description") String description,
                          @RequestParam(value = "listId") long id,
                          Model model) {
        Optional<TodoList> todolist = todoListService.findById(id);
        todoListService.addItem(todolist.get(), new TodoListItem(description, false, todolist.get()));
        model.addAttribute("todolists", todoListService.findAll());
        return "redirect:/todolist";
    }

    @RequestMapping(value = "/newtodolist", method = RequestMethod.POST)
    public String newTodoList() {
        return "NewTodoListPage";
    }

    @RequestMapping("/makenewtodolist")
    public String makeTodoList(@RequestParam(value = "name") String name,
                               HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        TodoList todoList = new TodoList(name, "FFFF", sessionUser);
        todoListService.save(todoList);
        return "redirect:/todolist";
    }

    @RequestMapping(value = "/additemnewtodolist", method = RequestMethod.POST)
    public String addItemNewTodoList(@RequestParam(value = "description") String description,
                                     @RequestParam(value = "todolist") TodoList todolist) {
        todoListService.addItem(todolist, new TodoListItem(description, false, todolist));
        todoListService.save(todolist);
        return "redirect:/newtodolist";
    }

    @RequestMapping(value = "/itemchecked", method = RequestMethod.POST)
    public String itemChecked(@RequestParam(value = "id") long id,
                              @RequestParam(value = "checked") boolean checked) { ;
        TodoListItem item = itemService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo list id"));
        item.setChecked(checked);
        itemService.save(item);
        return "redirect:/todolist";
    }
}
