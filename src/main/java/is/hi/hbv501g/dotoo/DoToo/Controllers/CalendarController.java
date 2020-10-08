package is.hi.hbv501g.dotoo.DoToo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CalendarController {

    @RequestMapping("/calendar")
    public String CalendarPage() {
        return "CalendarPage";
    }
}
