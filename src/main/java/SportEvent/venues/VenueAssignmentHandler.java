package SportEvent.venues;

import SportEvent.event.Event;
import SportEvent.event.EventStatus;
import SportEvent.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class VenueAssignmentHandler {
    @Autowired
    private VenueRepository venueRepository;

    public void assignVenueToEvent(Event event, Long venueId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        if (!venue.isAvailable(event.getStartDate())) {
            throw new RuntimeException("Venue is not available for the event date");
        }

        event.setVenue(venue);
        venue.book(event.getStartDate());
        venueRepository.save(venue);
    }

    public List<Venue> findAvailableVenues(LocalDateTime date) {
        List<Venue> venues = venueRepository.findByIsAvailable(true);
        venues.removeIf(venue -> !venue.isAvailable(date));
        return venues;
    }

    public void releaseVenue(Event event) {
        if (event.getVenue() != null) {
            Venue venue = event.getVenue();
            venue.setIsAvailable(true);
            venueRepository.save(venue);
            event.setVenue(null);
        }
    }
}
