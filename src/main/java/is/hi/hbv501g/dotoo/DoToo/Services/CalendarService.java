package is.hi.hbv501g.dotoo.DoToo.Services;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;

import java.util.List;
import java.util.Optional;

public interface CalendarService {

    Event save(Event event);
    void delete(Event event);
    List<Event> findAll();
    Optional<Event> findById(long id);
}
