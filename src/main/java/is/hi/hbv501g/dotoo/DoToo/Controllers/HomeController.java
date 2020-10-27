package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.EventService;
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

import java.util.Calendar;

@Controller
public class HomeController {

    private UserService userService;
    private TodoListService todoListService;
    private EventService eventService;

    @Autowired
    public HomeController(UserService userService, TodoListService todoListService, EventService eventService) {
        this.userService = userService;
        this.todoListService = todoListService;
        this.eventService = eventService;
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

    @RequestMapping("/makedata")
    public String makeData(Model model) {

        User nonni = new User("nonni", "Nonni", "1234");
        userService.save(nonni);


        for(int i=1; i<5; i++) {
            TodoList todoList = new TodoList("Innkaupalisti " + i, "FFFFFF", nonni);
            todoListService.save(todoList);

            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            startDate.clear();
            endDate.clear();
            startDate.set(2020, 10, 20+i, 10+i, 30);
            endDate.set(2020, 10, 20+i, 10+i+2, 45);
            Event event = new Event(startDate, endDate, "Æfing " + i, "Íþróttir", "0x00ff", nonni);
            eventService.save(event);

            for(int j=1; j<4;j++) {
                 todoListService.addItem(todoList, new TodoListItem(i + " mjólk", false, todoList));
            }
        }
        return "redirect:/";
    }
}
