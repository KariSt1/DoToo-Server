package is.hi.hbv501g.dotoo.DoToo.Controllers;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.EventService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.WeekFields;
import java.util.Calendar;
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
        LocalDate date = LocalDate.now();
        // Nýtt
        LocalDate viewedDate = (LocalDate) session.getAttribute("date");
        if(viewedDate == null) {
            viewedDate = date;
        }
        //
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        model.addAttribute("date", date);
        //model.addAttribute("weekStart", date.with(weekFields.dayOfWeek(), 1L));
        //model.addAttribute("weekEnd", date.with(weekFields.dayOfWeek(), 7L));
        String view = (String) session.getAttribute("view");
        if(view != null) {
            model.addAttribute("view", view);
            session.setAttribute("view", view);
        } else {
            view = "week";
            model.addAttribute("view", "week");
            session.setAttribute("view", "week");
        }

        Integer offset = (Integer) session.getAttribute("offset");
        if(offset == null) {
            offset = 0;
        }

        if(view.equals("day")) {
            if(offset > 0) {
                viewedDate = viewedDate.plusDays(1);
            } else if(offset < 0) {
                viewedDate = viewedDate.minusDays(1);
            }
            model.addAttribute("events", eventService.findByDay(viewedDate.getYear(), viewedDate.getMonthValue(), viewedDate.getDayOfMonth(), sessionUser));
        } else if(view.equals("week")) {
            if(offset > 0) {
                viewedDate = viewedDate.plusWeeks(1);
            } else if(offset < 0) {
                viewedDate = viewedDate.minusWeeks(1);
            }
            model.addAttribute("events", eventService.findByWeek(viewedDate.getYear(), viewedDate.get(weekFields.weekOfWeekBasedYear()), sessionUser));
            model.addAttribute("weekStart", viewedDate.with(weekFields.dayOfWeek(), 1L));
            model.addAttribute("weekEnd", viewedDate.with(weekFields.dayOfWeek(), 7L));
        } else if(view.equals("month")) {
            if(offset > 0) {
                viewedDate = viewedDate.plusMonths(1);
            } else if(offset < 0) {
                viewedDate = viewedDate.minusMonths(1);
            }
            model.addAttribute("events", eventService.findByMonth(viewedDate.getYear(), viewedDate.getMonthValue(), sessionUser));
        }

        /*
        Integer offset = (Integer) session.getAttribute("offset");
        if(offset == null) {
            offset = 0;
        }
        LocalDate newDate = null;
        if (view.equals("day")) {
            //LocalDate newDate;
            if (offset < 0) {
                newDate = date.minus(Period.ofDays(Math.abs(offset)));
            } else {
                newDate = date.plus(Period.ofDays(offset));
            }
            model.addAttribute("events", eventService.findByDay(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth(), sessionUser));
            //model.addAttribute("date", newDate);
            //session.setAttribute("date", newDate);
        } else if (view.equals("week")) {
            //LocalDate newDate;
            if (offset < 0) {
                newDate = date.minus(Period.ofWeeks(Math.abs(offset)));
            } else {
                newDate = date.plus(Period.ofWeeks(offset));
            }
            model.addAttribute("events", eventService.findByWeek(newDate.getYear(), newDate.get(weekFields.weekOfWeekBasedYear()), sessionUser));
            model.addAttribute("weekStart", newDate.with(weekFields.dayOfWeek(), 1L));
            model.addAttribute("weekEnd", newDate.with(weekFields.dayOfWeek(), 7L));

        } else if (view.equals("month")) {
            //LocalDate newDate;
            if (offset < 0) {
                newDate = date.minus(Period.ofMonths(Math.abs(offset)));
            } else {
                newDate = date.plus(Period.ofMonths(offset));
            }
            model.addAttribute("events", eventService.findByMonth(newDate.getYear(), newDate.getMonthValue(), sessionUser));
            //model.addAttribute("date", newDate);
            //session.setAttribute("date", newDate);
        }

        // Not implemented in interface for now
        else if (view.equals("year")) {
            //LocalDate newDate;
            if (offset < 0) {
                newDate = date.minus(Period.ofMonths(offset));
            } else {
                newDate = date.plus(Period.ofMonths(offset));
            }
            model.addAttribute("events", eventService.findByYear(newDate.getYear(), sessionUser));
            model.addAttribute("view", "year");
            //model.addAttribute("year", newDate.getYear());
            session.setAttribute("view", "year");
            session.setAttribute("date", newDate);
        }
        if(newDate != null) {
            model.addAttribute("date", newDate);
            session.setAttribute("date", newDate);
        }
        session.setAttribute("offset", offset);
        model.addAttribute("offset", offset);

         */
        session.setAttribute("date", viewedDate);
        model.addAttribute("date", viewedDate);
        model.addAttribute("loggedinuser", sessionUser);

        /**
        model.addAttribute("events", eventService.findByWeek(date.getYear(), date.get(weekFields.weekOfWeekBasedYear()), sessionUser));
        /**
        Calendar now = Calendar.getInstance();
        LocalDate currentDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        now.setTimeZone(TimeZone.getTimeZone("GMT"));
        //model.addAttribute("day", now.get(Calendar.DAY_OF_MONTH));
        model.addAttribute("date", currentDate);
        //model.addAttribute("week", now.get(Calendar.WEEK_OF_YEAR));
        model.addAttribute("weekStart", currentDate.with(weekFields.dayOfWeek(), 1L));
        model.addAttribute("weekEnd", currentDate.with(weekFields.dayOfWeek(), 7L));
        //model.addAttribute("month", now.get(Calendar.MONTH));
        //model.addAttribute("month", currentDate);
        model.addAttribute("year", now.get(Calendar.YEAR));
        model.addAttribute("loggedinuser", sessionUser);
        model.addAttribute("view", "week");
        model.addAttribute("events", eventService.findByWeek(now.get(Calendar.YEAR), now.get(Calendar.WEEK_OF_YEAR), sessionUser));
        */
        //session.setAttribute("offset", 0);

        return "EventPage";
    }

    /**
     * Handles changes of view
     * Shows list of events of chosen day, month or year
     *
     * @param view    day, month or year
     * @param nav     prev or next - goes back or forward in time
     * @param model
     * @param session
     * @return EventPage
     */
    @RequestMapping("/changeview")
    public String changeCalendarView(@RequestParam(value = "view", required = false) String view, @RequestParam(value = "nav", required = false) String nav, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");

        //int offset = Integer.valueOf(session.getAttribute("offset").toString());
        int offset = 0;

        if (sessionUser == null) {
            return "redirect:/login";
        }
        if (view == null) {
            view = session.getAttribute("view").toString();
        }/* else {
            offset = 0;
        }*/


        if (nav == null) offset = 0;
        else if (nav.equals("next")) offset = 1;
        else if (nav.equals("prev")) offset = -1;
        //else offset = 0;

        /*
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        model.addAttribute("date", now);

        if (view.equals("day")) {
            LocalDate newDate;
            if (offset < 0) {
                newDate = now.minus(Period.ofDays(Math.abs(offset)));
            } else {
                newDate = now.plus(Period.ofDays(offset));
            }
            model.addAttribute("events", eventService.findByDay(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth(), sessionUser));
            model.addAttribute("view", "day");
            model.addAttribute("date", newDate);
            session.setAttribute("view", "day");
            session.setAttribute("date", newDate);
        } else if (view.equals("week")) {
            LocalDate newDate;
            if (offset < 0) {
                newDate = now.minus(Period.ofWeeks(Math.abs(offset)));
            } else {
                newDate = now.plus(Period.ofWeeks(offset));
            }
            model.addAttribute("events", eventService.findByWeek(newDate.getYear(), newDate.get(weekFields.weekOfWeekBasedYear()), sessionUser));
            model.addAttribute("view", "week");
            model.addAttribute("weekStart", newDate.with(weekFields.dayOfWeek(), 1L));
            model.addAttribute("weekEnd", newDate.with(weekFields.dayOfWeek(), 7L));
            session.setAttribute("view", "week");

        } else if (view.equals("month")) {
            LocalDate newDate;
            if (offset < 0) {
                newDate = now.minus(Period.ofMonths(Math.abs(offset)));
            } else {
                newDate = now.plus(Period.ofMonths(offset));
            }
            model.addAttribute("events", eventService.findByMonth(newDate.getYear(), newDate.getMonthValue(), sessionUser));
            model.addAttribute("view", "month");
            model.addAttribute("date", newDate);
            session.setAttribute("view", "month");
            session.setAttribute("date", newDate);
        }

        // Not implemented in interface for now
        else if (view.equals("year")) {
            LocalDate newDate;
            if (offset < 0) {
                newDate = now.minus(Period.ofMonths(offset));
            } else {
                newDate = now.plus(Period.ofMonths(offset));
            }
            model.addAttribute("events", eventService.findByYear(newDate.getYear(), sessionUser));
            model.addAttribute("view", "year");
            //model.addAttribute("year", newDate.getYear());
            session.setAttribute("view", "year");
            session.setAttribute("date", newDate);
        } */
        session.setAttribute("view", view);

        session.setAttribute("offset", offset);
        //model.addAttribute("offset", offset);
        //model.addAttribute("loggedinuser", sessionUser);
        return "redirect:/calendar";
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
        return "redirect:/calendar";
    }

    @RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
    public String deleteEvent(@RequestParam(value = "id") long id) {
        Event event = eventService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event id"));
        eventService.delete(event);
        return "redirect:/calendar";
    }
}
