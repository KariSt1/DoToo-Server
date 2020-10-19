package is.hi.hbv501g.dotoo.DoToo.Services.Implementations;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import is.hi.hbv501g.dotoo.DoToo.Repositories.EventRepository;
import is.hi.hbv501g.dotoo.DoToo.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImplementation implements EventService {
    EventRepository eventRepository;

    @Autowired
    public EventServiceImplementation(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }
    public List<Event> findAll() {
        return eventRepository.findAll();
    }
    public Optional<Event> findById(long id) {
        return eventRepository.findById(id);
    }
}
