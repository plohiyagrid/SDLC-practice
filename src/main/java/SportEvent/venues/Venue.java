package SportEvent.venues;

import SportEvent.event.Event;
import SportEvent.match.Match;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venues")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Boolean isAvailable = true;

    @OneToMany(mappedBy = "venue")
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "venue")
    private List<Match> matches = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "venue_booked_dates")
    private List<LocalDateTime> bookedDates = new ArrayList<>();

    protected Venue() {
    }

    public Venue(String name, String location, Integer capacity) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    public boolean isAvailable(LocalDateTime date) {
        return isAvailable && !bookedDates.contains(date);
    }

    public void book(LocalDateTime date) {
        if (isAvailable(date)) {
            bookedDates.add(date);
        } else {
            System.out.println("Venue " + name + " is already booked on " + date);
        }
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public List<LocalDateTime> getBookedDates() {
        return bookedDates;
    }
}