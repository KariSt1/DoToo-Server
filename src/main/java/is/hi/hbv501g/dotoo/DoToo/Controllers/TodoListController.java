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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class TodoListController {

    private TodoListService todoListService;
    private TodoListItemService itemService;

    @Autowired
    public TodoListController(TodoListService todoListService,
                              TodoListItemService itemService) {
        this.todoListService = todoListService;
        this.itemService = itemService;
    }

    @RequestMapping("/todolist")
    public String TodoListPage(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedinuser", sessionUser);
        model.addAttribute("todolists", todoListService.findByUser(sessionUser));
        return "TodoListPage";
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
    public String newTodoList(@RequestParam(value = "name") String name, @RequestParam(value = "color") String color, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        TodoList todolist = new TodoList(name, color, sessionUser);
        todoListService.save(todolist);
        return "redirect:/todolist";
    }


    @RequestMapping(value = "/itemchecked", method = RequestMethod.POST)
    public String itemChecked(@RequestParam(value = "id") long id,
                              @RequestParam(value = "checked") boolean checked) { ;
        TodoListItem item = itemService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item id"));
        item.setChecked(checked);
        itemService.save(item);
        return "redirect:/todolist";
    }
}
