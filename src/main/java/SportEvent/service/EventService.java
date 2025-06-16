package SportEvent.service;

import SportEvent.event.Event;
import SportEvent.event.EventStatus;
import SportEvent.repository.EventRepository;
import SportEvent.repository.VenueRepository;
import SportEvent.venues.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    public Event createEvent(Event event) {
        event.setStatus(EventStatus.REGISTRATION_OPEN);
        return eventRepository.save(event);
    }

    public Event startEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setStatus(EventStatus.EVENT_ONGOING);
        return eventRepository.save(event);
    }

    public Event completeEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setStatus(EventStatus.EVENT_COMPLETED);
        return eventRepository.save(event);
    }

    public Event assignVenue(Long eventId, Long venueId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        if (!venue.isAvailable(event.getStartDate())) {
            throw new RuntimeException("Venue is not available for the event date");
        }

        event.setVenue(venue);
        venue.book(event.getStartDate());
        venueRepository.save(venue);
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
} 