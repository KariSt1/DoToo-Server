package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
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

import javax.servlet.http.HttpSession;

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
    public String HomePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if(user == null ) {
            return "redirect:/login";
        }
        model.addAttribute("users", userService.findAll());
        return "Velkomin";
    }

    @RequestMapping("/main")
    public String MainPage() {
        return "MainPage";
    }

    @RequestMapping("/makedata")
    public String makeData() {

        User nonni = new User("nonni", "Nonni", "1234");
        userService.save(nonni);


        for(int i=1; i<5; i++) {
            TodoList todoList = new TodoList("Innkaupalisti " + i, "FFFFFF", nonni);
            todoListService.save(todoList);
            for(int j=1; j<4;j++) {
                 todoListService.addItem(todoList, new TodoListItem(i + " mjólk", false, todoList));
            }
        }
        return "redirect:/";
    }
}
