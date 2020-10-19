package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import is.hi.hbv501g.dotoo.DoToo.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;

@Controller
public class EventController {

    private EventService eventService;
    
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping("/calendar")
    public String CalendarPage(Model model) {
        Calendar now = Calendar.getInstance();
        model.addAttribute("day", now.DAY_OF_WEEK);
        model.addAttribute("week", now.WEEK_OF_MONTH);
        model.addAttribute("month", now.MONTH);
        return "CalendarPage";
    }

    @RequestMapping("/makenewevent")
    public String makeEvent(@RequestParam(value = "startDate") Date startDate, @RequestParam(value = "endDate") Date endDate,
                            @RequestParam(value = "title") String title, @RequestParam(value = "category") String category,
                            @RequestParam(value = "color") String color) {
        Event event = new Event(startDate, endDate, title, category, color);
        eventService.save(event);
        return "redirect:/calendar";
    }
}
