package SportEvent.event;

import SportEvent.match.Match;
import SportEvent.registration.Registration;
import SportEvent.venues.Venue;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "event_type")
public abstract class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status = EventStatus.REGISTRATION_OPEN;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Registration> registrations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private List<Match> fixtures = new ArrayList<>();

    protected Event() {
    }

    protected Event(String name, LocalDateTime startDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = startDate.plusDays(1); // Default to one day event
    }

    public void addFixture(Match match) {
        this.fixtures.add(match);
    }

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

    public abstract void setupEvent();

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}