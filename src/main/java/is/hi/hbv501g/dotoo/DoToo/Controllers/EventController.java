package is.hi.hbv501g.dotoo.DoToo.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoList;
import is.hi.hbv501g.dotoo.DoToo.Entities.TodoListItem;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Services.EventService;
import is.hi.hbv501g.dotoo.DoToo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value ="/events", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEvents(@RequestParam String username,
                                 @RequestParam String password) {
        User userInfo = new User(username, password);
        User loggedInUser = userService.login(userInfo);
        if (loggedInUser != null) {
            System.out.println("Notandi til, nafn notanda: " + loggedInUser.getName());
            //session.setAttribute("loggedInUser", exists);
            return eventService.findByUser(loggedInUser);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login unsuccessful");
        }
    }

    @RequestMapping(value = "/events", method = RequestMethod.POST)
    @ResponseBody
    public Event postEvents(@RequestParam String username,
                                                @RequestParam String password,
                                                @RequestBody Event event) {
        User userInfo = new User(username, password);
        User loggedInUser = userService.login(userInfo);

        if (loggedInUser != null) {
            event.setUser(loggedInUser);
            eventService.save(event);

            return event;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login unsuccessful");
        }
    }

    @RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
    public ResponseEntity<String> deleteEvent(@Valid @RequestBody ObjectNode objectNode) {
        JsonNode id = objectNode.get("id");
        JsonNode username = objectNode.get("username");
        String sId = id.toString();
        long deletedId = Long.parseLong(sId);
        Event event = eventService.findById(deletedId).orElseThrow(() -> new IllegalArgumentException("Invalid event id"));
        eventService.delete(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
