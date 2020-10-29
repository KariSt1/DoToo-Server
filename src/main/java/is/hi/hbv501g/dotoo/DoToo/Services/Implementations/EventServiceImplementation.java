package is.hi.hbv501g.dotoo.DoToo.Services.Implementations;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Repositories.EventRepository;
import is.hi.hbv501g.dotoo.DoToo.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public List<Event> findByUser(User user) {
        return eventRepository.findByUser(user);
    }

    @Override
    public List<Event> findByWeek(int year, int week, User user) {
        List<Event> events = findByUser(user);
        List<Event> eventsByWeek = new ArrayList<>();
        for(int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            Calendar ev = event.getStartDate().getInstance();
            if(ev.YEAR == year && ev.WEEK_OF_YEAR == week) eventsByWeek.add(event);
        }
        return eventsByWeek;
    }

    @Override
    public List<Event> findByDay(int year, int month, int day, User user) {
        List<Event> events = findByUser(user);
        List<Event> eventsByDay = new ArrayList<>();
        for(int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            Calendar ev = event.getStartDate().getInstance();
            if(ev.YEAR == year && ev.MONTH == month && ev.DAY_OF_MONTH == day) eventsByDay.add(event);
        }
        return eventsByDay;
    }

    @Override
    public List<Event> findByMonth(int year, int month, User user) {
        List<Event> events = findByUser(user);
        List<Event> eventsByMonth = new ArrayList<>();
        for(int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            Calendar ev = event.getStartDate().getInstance();
            if(ev.YEAR == year && ev.MONTH == month) eventsByMonth.add(event);
        }
        return eventsByMonth;
    }

    @Override
    public List<Event> findByYear(int year, User user) {
        List<Event> events = findByUser(user);
        List<Event> eventsByYear = new ArrayList<>();
        for(int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            Calendar ev = event.getStartDate().getInstance();
            if(ev.YEAR == year)  eventsByYear.add(event);
        }
        return eventsByYear;
    }
}
