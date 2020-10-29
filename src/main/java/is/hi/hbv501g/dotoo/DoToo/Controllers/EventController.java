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
        model.addAttribute("day", now.get(Calendar.DAY_OF_MONTH));
        model.addAttribute("week", now.get(Calendar.WEEK_OF_YEAR));
        model.addAttribute("month", now.get(Calendar.MONTH));
        model.addAttribute("year", now.get(Calendar.YEAR));
        model.addAttribute("loggedinuser", sessionUser);
        model.addAttribute("events", eventService.findByUser(sessionUser));

        return "CalendarPage";
    }

    @RequestMapping("/changeview")
    public String changeCalendarView(@RequestParam(value="view") String view, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("GMT"));
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int week = now.get(Calendar.WEEK_OF_YEAR);
        int day = now.get(Calendar.DAY_OF_MONTH);
        if (view.equals("day"))
            model.addAttribute("events", eventService.findByDay(year, month, day, sessionUser));
        else if(view.equals("week"))
            model.addAttribute("events", eventService.findByWeek(year, week, sessionUser));
        else if(view.equals("month"))
            model.addAttribute("events", eventService.findByMonth(year, month, sessionUser));
        else if(view.equals("year"))
            model.addAttribute("events", eventService.findByYear(year, sessionUser));
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
