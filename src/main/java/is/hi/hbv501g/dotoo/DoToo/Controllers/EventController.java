package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Controller
public class EventController {

    private EventService eventService;
    
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping("/calendar")
    public String CalendarPage(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("GMT"));
        model.addAttribute("day", now.DAY_OF_WEEK);
        model.addAttribute("week", now.WEEK_OF_MONTH);
        model.addAttribute("month", now.MONTH);
        model.addAttribute("year", now.YEAR);
        model.addAttribute("loggedinuser", sessionUser);
        model.addAttribute("events", eventService.findByUser(sessionUser));

        return "CalendarPage";
    }

    @RequestMapping("/changeview")
    public String changeCalendarView(@ModelAttribute(value="view") Model model) {

        return "CalendarPage";
    }

    @RequestMapping("/makenewevent")
    public String makeEvent(@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate,
                            @RequestParam(value = "title") String title, @RequestParam(value = "category") String category,
                            @RequestParam(value = "color") String color, HttpSession session) throws ParseException {
        Calendar sd = Calendar.getInstance();
        Calendar ed = Calendar.getInstance();
        startDate = startDate.replace(startDate.charAt(10), ' ');
        endDate = endDate.replace(endDate.charAt(10), ' '); //Get rid of the T from date string
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        sd.setTime(sdf.parse(startDate));
        ed.setTime(sdf.parse(endDate));

        Event event = new Event(sd, ed, title, category, color, (User) session.getAttribute("loggedInUser"));
        eventService.save(event);
        return "redirect:/calendar";
    }

    @RequestMapping(value="/calendar/delete/{id}", method = RequestMethod.GET)
    public String deleteEvent(@PathVariable("id") long id, Model model) {
        Event event = eventService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event id"));
        eventService.delete(event);
        model.addAttribute("events", eventService.findAll());
        return "CalendarPage";
    }
}
