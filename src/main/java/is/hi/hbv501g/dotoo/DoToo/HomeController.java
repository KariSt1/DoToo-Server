package is.hi.hbv501g.dotoo.DoToo;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private TodoListService todoListService;

    @Autowired
    public HomeController(TodoListService todoListService) {this.todoListService = todoListService;}

    @RequestMapping("/")
    public String HomePage() {

        return "Velkomin";
    }

    @RequestMapping("/login")
    public String LoginPage() {
        return "LoginPage";
    }

    @RequestMapping("/signup")
    public String SignupPage() {
        return "SignupPage";
    }

    @RequestMapping("/main")
    public String MainPage() {
        return "MainPage";
    }

    @RequestMapping("/calendar")
    public String CalendarPage() {
        return "CalendarPage";
    }

    @RequestMapping("/todolist")
    public String TodoListPage(Model model) {
        model.addAttribute("todolists", todoListService.findAll());
        return "TodoListPage";
    }

    @RequestMapping(value="/addtodolist", method = RequestMethod.POST)
    public String addTodoList(TodoList todolist, BindingResult result, Model model) { // @Valid virkar ekki
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
}
