package is.hi.hbv501g.dotoo.DoToo.Services;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Event save(Event event);

    void delete(Event event);

    List<Event> findAll();

    Optional<Event> findById(long id);

    List<Event> findByUser(User user);

    List<Event> findByDay(int year, int month, int day, User user);

    List<Event> findByWeek(int year, int week,  User user);

    List<Event> findByMonth(int year, int month, User user);

    List<Event> findByYear(int year, User user);

}
