package is.hi.hbv501g.dotoo.DoToo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

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
    public String TodoListPage() {
        return "TodoListPage";
    }}
