package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class TodoListController {

    private TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService) {this.todoListService = todoListService;}

    @RequestMapping("/todolist")
    public String TodoListPage(Model model) {
        model.addAttribute("todolists", todoListService.findAll());
        return "TodoListPage";
    }

    @RequestMapping(value="/addtodolist", method = RequestMethod.POST)
    public String addTodoList(@Valid TodoList todolist, BindingResult result, Model model) { // @Valid virkar ekki
        if(result.hasErrors()) {
            return "addtodolist";
        }

        todoListService.save(todolist);
        model.addAttribute("todolists", todoListService.findAll());
        return "TodoListPage";
    }

    @RequestMapping(value = "/addtodolist", method = RequestMethod.GET)
    public String addTodoListFrom(Model model) {
        return "addtodolist";
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String deleteTodoList(@PathVariable("id") long id, Model model) {
        TodoList todolist = todoListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo list id"));
        todoListService.delete(todolist);
        model.addAttribute("todolists", todoListService.findAll());
        return "TodoListPage";
    }

    @RequestMapping(value = "/additem", method = RequestMethod.POST)
    public String addItem(Model model, TodoList list, TodoListItem item) {
        todoListService.addItem(list, item);
        model.addAttribute("todolists", todoListService.findAll());
        return "TodoListPage";
    }
}
