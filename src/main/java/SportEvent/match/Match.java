package SportEvent.match;

import SportEvent.registration.TeamRegistration;
import SportEvent.venues.Venue;

import java.util.Date;

public class Match {
    private int matchId;  // Unique match identifier
    private TeamRegistration team1;
    private TeamRegistration team2;
    private Venue venue;
    private Date matchDate;
    private String matchStatus;  // Match status ("Ongoing", "Completed", "Drawn")
    private TeamRegistration winner;  // Winner (null if not decided)

    // Constructor
    public Match(int matchId, TeamRegistration team1, TeamRegistration team2, Venue venue, Date matchDate) {
        this.matchId = matchId;
        this.team1 = team1;
        this.team2 = team2;
        this.venue = venue;
        this.matchDate = matchDate;
        this.matchStatus = "Ongoing";  // Default status
        this.winner = null;
    }

    // Getter for matchId
    public int getMatchId() {
        return matchId;
    }

    // Getter for matchStatus
    public String getMatchStatus() {
        return matchStatus;
    }

    // Setter for match status
    public void setMatchStatus(String status) {
        this.matchStatus = status;
    }

    // Setter for winner
    public void setWinner(TeamRegistration winner) {
        if (winner != null && (winner.equals(team1) || winner.equals(team2))) {
            this.winner = winner;
            this.matchStatus = "Completed";  // Automatically mark the match as completed
        } else {
            System.out.println("Invalid winner selection!");
        }
    }

    // Setter for draw
    public void setDraw() {
        this.matchStatus = "Drawn";  // Mark the match as Drawn
    }

    // Display match details
    public void displayMatchDetails() {
        System.out.println("Match ID: " + matchId);
        System.out.println("Teams: " + team1.getTeamName() + " vs " + team2.getTeamName());
        System.out.println("Venue: " + venue.getName());
        System.out.println("Date: " + matchDate);
        System.out.println("Match Status: " + matchStatus);
        if (winner != null) {
            System.out.println("Winner: " + winner.getTeamName());
        }
        System.out.println("-----------------------------------");
    }
}
