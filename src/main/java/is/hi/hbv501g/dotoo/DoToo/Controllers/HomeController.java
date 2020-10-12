package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListService;
import is.hi.hbv501g.dotoo.DoToo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private UserService userService;
    private TodoListService todoListService;

    @Autowired
    public HomeController(UserService userService, TodoListService todoListService) {
        this.userService = userService;
        this.todoListService = todoListService;
    }

    @RequestMapping("/")
    public String HomePage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "Velkomin";
    }

    @RequestMapping("/main")
    public String MainPage() {
        return "MainPage";
    }
}
