package SportEvent.venues;

import SportEvent.EventManager;
import SportEvent.event.Event;
import SportEvent.event.EventStatus;

import java.util.Optional;

public class VenueAssignmentHandler {
    private EventManager eventManager;

    public VenueAssignmentHandler(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public boolean assignVenue(int eventId, Venue preferredVenue) {
        Optional<Event> eventOpt = eventManager.getEventById(eventId);

        if (eventOpt.isEmpty()) {
            System.out.println("Event with ID " + eventId + " not found.");
            return false;
        }

        Event event = eventOpt.get();

        if (event.getStatus() != EventStatus.REGISTRATION_CLOSED) {
            System.out.println("Venue can only be assigned after registration closes.");
            return false;
        }

        // Choose preferred venue if available, otherwise find an alternative
        Venue venue = (preferredVenue != null && preferredVenue.isAvailable(event.getEventDate()))
                ? preferredVenue
                : findAvailableVenue(event);

        if (venue == null) {
            System.out.println("No available venues for event: " + event.getName());
            return false;
        }

        event.selectVenue(venue);
        venue.book(event.getEventDate());
        event.updateEventStatus(EventStatus.EVENT_ONGOING);

        System.out.println("✅ Venue " + venue.getName() + " assigned to event: " + event.getName());
        return true;
    }

    private Venue findAvailableVenue(Event event) {
        for (Venue venue : eventManager.getVenues()) {
            if (venue.isAvailable(event.getEventDate())) {
                return venue;
            }
        }
        return null;
    }
}
