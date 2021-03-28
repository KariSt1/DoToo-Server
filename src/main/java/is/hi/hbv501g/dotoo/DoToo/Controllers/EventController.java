package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.EventService;
import is.hi.hbv501g.dotoo.DoToo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@RestController
public class EventController {

    private EventService eventService;
    private UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @RequestMapping("/events")
    @ResponseBody
    public List<Event> getEvents(@Valid @RequestBody User user) {
        User loggedInUser = userService.login(user);
        if (loggedInUser != null) {
            System.out.println("Notandi til, nafn notanda: " + loggedInUser.getName());
            //session.setAttribute("loggedInUser", exists);
            return eventService.findByUser(loggedInUser);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login unsuccessful");
        }
    }

    @RequestMapping("/changeview")
    public String changeEventView(@RequestParam(value = "viewDate", required = false) String viewDate, @RequestParam(value = "view", required = false) String view,
                                  @RequestParam(value = "nav", required = false) String nav, @RequestParam(value = "category", required = false) String category, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        int offset = 0;

        if (sessionUser == null) {
            return "redirect:/login";
        }
        if (viewDate.length() > 0) {
            LocalDate date = LocalDate.parse(viewDate);
            session.setAttribute("date", date);
        }

        if (category == null || category.equals("")) {
            category = session.getAttribute("category").toString();
        }

        if (view == null || view.equals("")) {
            view = session.getAttribute("view").toString();
        }

        if (nav == null || nav.equals("")) offset = 0;
        else if (nav.equals("next")) offset = 1;
        else if (nav.equals("prev")) offset = -1;

        session.setAttribute("view", view);
        session.setAttribute("offset", offset);
        session.setAttribute("category", category);
        return "redirect:/events";
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
        session.setAttribute("offset", 0);
        eventService.save(event);
        return "redirect:/events";
    }

    @RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
    public String deleteEvent(@RequestParam(value = "id") long id) {
        Event event = eventService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event id"));
        eventService.delete(event);
        return "redirect:/events";
    }
}
