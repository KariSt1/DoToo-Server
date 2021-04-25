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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

@RestController
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

    @RequestMapping("/makedata")
    public String makeData(HttpSession session) {

        User nonni = new User("nonni", "Nonni", "1234");
        userService.save(nonni);
        session.setAttribute("loggedInUser", nonni);

        String colors[] = {"yellow", "orange", "red", "green", "blue", "pink", "purple"};

        // Todo-lists:
        TodoList todoList = new TodoList("Innkaupalisti", colors[0], nonni, false);
        todoListService.save(todoList);
        todoListService.addItem(todoList, new TodoListItem("Mjólk", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Brauð", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Egg", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Gulrætur", false, todoList));

        todoList = new TodoList("Morgun rútína", colors[5], nonni, true);
        todoListService.save(todoList);
        todoListService.addItem(todoList, new TodoListItem("Borða morgunmat", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Fara í sturtu", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Bursta tennurnar", false, todoList));

        todoList = new TodoList("Skóli", colors[4], nonni, false);
        todoListService.save(todoList);
        todoListService.addItem(todoList, new TodoListItem("Lesa kafla 4 til 6", true, todoList));
        todoListService.addItem(todoList, new TodoListItem("Vinna í verkefni", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Horfa á fyrirlestur", false, todoList));

        todoList = new TodoList("Jólagjafakaup", colors[2], nonni, false);
        todoListService.save(todoList);
        todoListService.addItem(todoList, new TodoListItem("Kaupa gjöf handa mömmu", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Kaupa gjöf handa pabba", true, todoList));
        todoListService.addItem(todoList, new TodoListItem("Kaupa gjöf handa Jonna", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Pakka inn gjöf handa Donna", true, todoList));
        todoListService.addItem(todoList, new TodoListItem("Kaupa gjöf handa Konna", false, todoList));

        todoList = new TodoList("Heimilisstörf", colors[1], nonni, true);
        todoListService.save(todoList);
        todoListService.addItem(todoList, new TodoListItem("Ryksuga stofuna", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Taka úr uppþvottavélinni", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Vökva blómin", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Þrífa baðherbergið", false, todoList));
        todoListService.addItem(todoList, new TodoListItem("Skipta á ljósaperunni í eldhúsinu", false, todoList));

        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < 31; j++) {
                if (j % 6 == 4) {
                    Calendar startDate = Calendar.getInstance();
                    Calendar endDate = Calendar.getInstance();
                    startDate.clear();
                    endDate.clear();
                    startDate.set(2020, 10 + i, 0 + j, 10 + i, 30);
                    endDate.set(2020, 10 + i, 0 + j, 10 + i + 2, 45);
                    Event event = new Event(startDate, endDate, "Æfing - Cardio", "Training", colors[3], nonni);
                    eventService.save(event);
                } else if (j % 6 == 2) {
                    Calendar startDate = Calendar.getInstance();
                    Calendar endDate = Calendar.getInstance();
                    startDate.clear();
                    endDate.clear();
                    startDate.set(2020, 10 + i, 0 + j, 10 + i, 30);
                    endDate.set(2020, 10 + i, 0 + j, 10 + i + 2, 45);
                    Event event = new Event(startDate, endDate, "Æfing - Fætur", "Training", colors[3], nonni);
                    eventService.save(event);
                } else if (j % 6 == 0) {
                    Calendar startDate = Calendar.getInstance();
                    Calendar endDate = Calendar.getInstance();
                    startDate.clear();
                    endDate.clear();
                    startDate.set(2020, 10 + i, 0 + j, 10 + i, 30);
                    endDate.set(2020, 10 + i, 0 + j, 10 + i + 2, 45);
                    Event event = new Event(startDate, endDate, "Æfing - Hendur", "Training", colors[3], nonni);
                    eventService.save(event);
                }
                if (j % 3 == 0) {
                    Calendar startDate = Calendar.getInstance();
                    Calendar endDate = Calendar.getInstance();
                    startDate.clear();
                    endDate.clear();
                    startDate.set(2020, 10 + i, 0 + j, 8, 0);
                    endDate.set(2020, 10 + i, 0 + j, 16, 30);
                    Event event = new Event(startDate, endDate, "Vinna", "Work", colors[4], nonni);
                    eventService.save(event);
                }
                if (j % 7 == 0) {
                    Calendar startDate = Calendar.getInstance();
                    Calendar endDate = Calendar.getInstance();
                    startDate.clear();
                    endDate.clear();
                    startDate.set(2020, 10 + i, 0 + j, 13, 20);
                    endDate.set(2020, 10 + i, 0 + j, 14, 50);
                    Event event = new Event(startDate, endDate, "Fyrirlestur", "School", colors[6], nonni);
                    eventService.save(event);
                }
            }
        }
        return "redirect:/";
    }
}
