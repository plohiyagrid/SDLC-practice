package SportEvent.match;

import SportEvent.registration.TeamRegistration;
import SportEvent.venues.Venue;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "team1_id", nullable = false)
    private TeamRegistration team1;

    @ManyToOne
    @JoinColumn(name = "team2_id", nullable = false)
    private TeamRegistration team2;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status = MatchStatus.SCHEDULED;

    @Column
    private String winnerTeamName;

    public Match() {
    }

    public Match(TeamRegistration team1, TeamRegistration team2, Venue venue, LocalDateTime startTime) {
        this.team1 = team1;
        this.team2 = team2;
        this.venue = venue;
        this.startTime = startTime;
        this.status = MatchStatus.SCHEDULED;
        this.durationMinutes = 180; // default duration
    }

    public Long getId() {
        return id;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public void setWinner(String winnerTeamName) {
        this.winnerTeamName = winnerTeamName;
        this.status = MatchStatus.COMPLETED;
    }

    public void setDraw() {
        this.status = MatchStatus.DRAWN;
    }

    public void displayMatchDetails() {
        System.out.println("Match ID: " + id);
        System.out.println("Teams: " + team1.getTeamName() + " vs " + team2.getTeamName());
        System.out.println("Venue: " + venue.getName());
        System.out.println("Date: " + startTime);
        System.out.println("Match Status: " + status);
        if (winnerTeamName != null) {
            System.out.println("Winner: " + winnerTeamName);
        }
        System.out.println("-----------------------------------");
    }

    // Getters and setters
    public TeamRegistration getTeam1() {
        return team1;
    }

    public TeamRegistration getTeam2() {
        return team2;
    }

    public Venue getVenue() {
        return venue;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getWinnerTeamName() {
        return winnerTeamName;
    }
}
