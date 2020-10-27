package is.hi.hbv501g.dotoo.DoToo.Services;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface EventService {

    Event save(Event event);
    void delete(Event event);
    List<Event> findAll();
    Optional<Event> findById(long id);
    List<Event> findByDay(int year, int month, int day);
    List<Event> findByWeek(int year, int week);
    List<Event> findByMonth(int year, int month);
    List<Event> findByYear(int year);
}
