package is.hi.hbv501g.dotoo.DoToo.Services.Implementations;

import is.hi.hbv501g.dotoo.DoToo.Entities.Event;
import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Repositories.EventRepository;
import is.hi.hbv501g.dotoo.DoToo.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
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
        return eventRepository.findAll(Sort.by(Sort.Direction.ASC, "startDate"));

    }

    public Optional<Event> findById(long id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> findByUser(User user, Sort sort) {
        return eventRepository.findByUser(user, Sort.by(Sort.Direction.ASC, "startDate"));

    }

    @Override
    public List<Event> findByWeek(int year, int week, String category, User user) {
        List<Event> events = null;
        if(category.equals("All")) {
           events = findByUser(user, Sort.by(Sort.Direction.ASC, "startDate"));
        }
        else {
            events = findByUserAndCategory(user, category,Sort.by(Sort.Direction.ASC, "startDate"));
        }
        List<Event> eventsByWeek = new ArrayList<>();
        for (Event event : events) {
            Calendar ev = event.getStartDate();
            if (ev.get(Calendar.YEAR) == year && ev.get(Calendar.WEEK_OF_YEAR) == week )
                eventsByWeek.add(event);
        }
        return eventsByWeek;
    }

    @Override
    public List<Event> findByDay(int year, int month, int day, String category, User user) {
        List<Event> events = null;
        if(category.equals("All")) {
            events = findByUser(user, Sort.by(Sort.Direction.ASC, "startDate"));
        }
        else {
            events = findByUserAndCategory(user, category,Sort.by(Sort.Direction.ASC, "startDate"));
        }
        List<Event> eventsByDay = new ArrayList<>();
        for (Event event : events) {
            Calendar ev = event.getStartDate();
            if (ev.get(Calendar.YEAR) == year && ev.get(Calendar.MONTH) + 1 == month && ev.get(Calendar.DAY_OF_MONTH) == day )
                eventsByDay.add(event);
        }
        return eventsByDay;
    }

    @Override
    public List<Event> findByMonth(int year, int month, String category, User user) {
        List<Event> events = null;
        if(category.equals("All")) {
            events = findByUser(user, Sort.by(Sort.Direction.ASC, "startDate"));
        }
        else {
            events = findByUserAndCategory(user, category,Sort.by(Sort.Direction.ASC, "startDate"));
        }
        List<Event> eventsByMonth = new ArrayList<>();
        for (Event event : events) {
            Calendar ev = event.getStartDate();
            if (ev.get(Calendar.YEAR) == year && ev.get(Calendar.MONTH) + 1 == month )
                eventsByMonth.add(event); //Not sure why but we add to add 1 month for it to show up correctly
        }
        return eventsByMonth;
    }

    @Override
    public List<Event> findByYear(int year, User user) {
        List<Event> events = findByUser(user, Sort.by(Sort.Direction.ASC, "startDate"));
        List<Event> eventsByYear = new ArrayList<>();
        for (Event event : events) {
            Calendar ev = event.getStartDate();
            if (ev.get(Calendar.YEAR) == year) eventsByYear.add(event);
        }
        return eventsByYear;
    }

    @Override
    public List<Event> findByUserAndCategory(User user, String category, Sort sort) {
        List<Event> events = findByUser(user, Sort.by(Sort.Direction.ASC, "startDate"));
        List<Event> eventsByUserAndCategory = new ArrayList<>();
        for (Event event : events) {
            String cat = event.getCategory();
            if(cat.equals(category))
                eventsByUserAndCategory.add(event);
        }
        return eventsByUserAndCategory;
    }
}
