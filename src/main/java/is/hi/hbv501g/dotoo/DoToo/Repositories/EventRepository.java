package is.hi.hbv501g.dotoo.DoToo.Repositories;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event save(Event event);
    void delete(Event event);
    List<Event> findAll();
    Optional<Event> findById(long id);
}
