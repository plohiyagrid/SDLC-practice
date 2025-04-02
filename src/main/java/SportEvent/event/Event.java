package SportEvent.event;

import SportEvent.match.FixtureGenerator;
import SportEvent.match.Match;
import SportEvent.registration.Registration;
import SportEvent.registration.TeamRegistration;
import SportEvent.venues.Venue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Event {
    protected int id;
    protected String name;
    protected EventStatus status;
    protected List<Registration> registrations;
    protected List<Match> fixtures;
    protected Date eventDate;
    protected Venue selectedVenue;

    public Event(int id, String name) {
        this(id, name, new Date()); // Default to current date
    }

    public Event(int id, String name, Date eventDate) {
        this.id = id;
        this.name = name;
        this.eventDate = eventDate;
        this.status = EventStatus.REGISTRATION_OPEN;
        this.registrations = new ArrayList<>();
        this.fixtures = new ArrayList<>();
    }

    public void addFixture(Match match) {
        this.fixtures.add(match);
    }
    // Getter for fixtures (get the list of matches)
    public List<Match> getFixtures() {
        return fixtures;
    }

    public boolean registerParticipant(Registration registration) {
        if (status != EventStatus.REGISTRATION_OPEN) {
            System.out.println("Registration is closed.");
            return false;
        }

        registrations.add(registration);
        System.out.println("✅ Registered: " + registration.getContactEmail());
        return true;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void generateFixtures(List<Venue> venues) {
        if (status != EventStatus.REGISTRATION_CLOSED) {
            System.out.println("Cannot generate fixtures until registration is closed.");
            return;
        }

        fixtures = FixtureGenerator.generateFixtures(this, venues);
        status = EventStatus.EVENT_ONGOING;
        System.out.println("✅ Fixtures generated!");
    }

    public void displayFixtures() {
        if (fixtures.isEmpty()) {
            System.out.println("No fixtures available.");
            return;
        }

        for (Match match : fixtures) {
            match.displayMatchDetails();
        }
    }

    public abstract void setupEvent();

    public int getId() {
        return id;
    }

    public EventStatus getStatus() {
        return status;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void selectVenue(Venue venue) {
        this.selectedVenue = venue;
    }

    public String getName() {
        return name;
    }

    public void updateEventStatus(EventStatus eventStatus) {
        this.status = eventStatus;
    }
}