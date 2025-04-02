package SportEvent;

import SportEvent.event.Event;
import SportEvent.event.EventStatus;
import SportEvent.match.Match;
import SportEvent.venues.Venue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventManager {
    private List<Event> events;
    private List<Venue> venues;

    public EventManager() {
        this.events = new ArrayList<>();
        this.venues = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public boolean removeEvent(int eventId) {
        return events.removeIf(event -> event.getId() == eventId);
    }

    public EventStatus getEventStatus(int eventId) {
        Optional<Event> eventOpt = getEventById(eventId);
        return eventOpt.map(Event::getStatus).orElse(null);
    }

    public Optional<Event> getEventById(int eventId) {
        return events.stream().filter(event -> event.getId() == eventId).findFirst();
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public void addVenue(Venue venue) {
        venues.add(venue);
    }

    // Simplified and more informative version of displayEventDashboard
    public void displayEventDashboard(int eventId) {
        Optional<Event> eventOpt = getEventById(eventId);

        if (eventOpt.isEmpty()) {
            System.out.println("Event with ID " + eventId + " not found.");
            return;
        }

        Event event = eventOpt.get();
        System.out.println("Event: " + event.getName() + " - Status: " + event.getStatus());

        // If event is ongoing, display match status dashboard
        if (event.getStatus() == EventStatus.EVENT_ONGOING) {
            System.out.println("Match Status Dashboard:");
            if (event.getFixtures().isEmpty()) {
                System.out.println("No matches available for this event.");
            } else {
                for (Match match : event.getFixtures()) {
                    match.displayMatchDetails();  // Assumes displayMatchDetails() is implemented in Match
                }
            }
        } else {
            // If event is not ongoing, provide a summary or history of matches
            System.out.println("Event is not ongoing. Here is a summary of the matches:");
            if (event.getFixtures().isEmpty()) {
                System.out.println("No matches have been played yet.");
            } else {
                for (Match match : event.getFixtures()) {
                    match.displayMatchDetails();  // Show details of past matches if any
                }
            }
        }
    }
}
