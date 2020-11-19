package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.EventService;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListItemService;
import is.hi.hbv501g.dotoo.DoToo.Services.TodoListService;
import is.hi.hbv501g.dotoo.DoToo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

@Controller
public class HomeController {

    private UserService userService;
    private TodoListService todoListService;
    private EventService eventService;
    private TodoListItemService itemService;

    @Autowired
    public HomeController(UserService userService, TodoListService todoListService,
                          EventService eventService, TodoListItemService itemService) {
        this.userService = userService;
        this.todoListService = todoListService;
        this.eventService = eventService;
        this.itemService = itemService;
    }

    @RequestMapping("/")
    public String HomePage(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        model.addAttribute("events", eventService.findByWeek(date.getYear(), date.get(weekFields.weekOfWeekBasedYear()), sessionUser));

        model.addAttribute("todolists", todoListService.findByUser(sessionUser));

        model.addAttribute("loggedinuser", sessionUser);
        //model.addAttribute("users", userService.findAll());
        return "Velkomin";
    }

    @RequestMapping(value = "/homeitemchecked", method = RequestMethod.POST)
    public String itemChecked(@RequestParam(value = "id") long id,
                              @RequestParam(value = "checked") boolean checked) { ;
        TodoListItem item = itemService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item id"));
        item.setChecked(checked);
        itemService.save(item);
        return "redirect:/";
    }

    @RequestMapping("/makedata")
    public String makeData(HttpSession session) {

        User nonni = new User("nonni", "Nonni", "1234");
        userService.save(nonni);
        session.setAttribute("loggedInUser", nonni);


        for (int i = 1; i < 5; i++) {
            TodoList todoList = new TodoList("Innkaupalisti " + i, "pink", nonni);
            todoListService.save(todoList);

            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            startDate.clear();
            endDate.clear();
            startDate.set(2020, 10, 20 + i, 10 + i, 30);
            endDate.set(2020, 10, 20 + i, 10 + i + 2, 45);
            Event event = new Event(startDate, endDate, "Æfing " + i, "Íþróttir", "0x00ff", nonni);
            eventService.save(event);

            for (int j = 1; j < 4; j++) {
                todoListService.addItem(todoList, new TodoListItem(i + " mjólk", false, todoList));
            }
        }
        return "redirect:/";
    }
}
