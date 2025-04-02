package SportEvent.match;

import SportEvent.event.EventStatus;
import SportEvent.registration.TeamRegistration;
import SportEvent.event.Event;
import java.util.Optional;

public class TournamentHandler {
    private Event event;  // TournamentHandler works with a specific event

    // Constructor that accepts a specific event
    public TournamentHandler(Event event) {
        this.event = event;
    }

    // Complete a match with a winner
    public void completeMatch(int matchId, TeamRegistration winner) {
        if (event.getStatus() != EventStatus.EVENT_ONGOING) {
            System.out.println("The event is not ongoing. Cannot complete matches.");
            return;
        }

        Match match = getMatchById(matchId);
        if (match != null) {
            match.setWinner(winner);  // Mark the match as completed and set the winner
            System.out.println("Match " + matchId + " completed: " + winner.getTeamName() + " won.");
        } else {
            System.out.println("Match not found.");
        }
    }

    // Mark a match as a draw
    public void drawMatch(int matchId) {
        if (event.getStatus() != EventStatus.EVENT_ONGOING) {
            System.out.println("The event is not ongoing. Cannot draw matches.");
            return;
        }

        Match match = getMatchById(matchId);
        if (match != null) {
            match.setDraw();  // Mark the match as drawn
            System.out.println("Match " + matchId + " ended in a draw.");
        } else {
            System.out.println("Match not found.");
        }
    }

    // Helper method to get a match by its ID
    private Match getMatchById(int matchId) {
        return event.getFixtures().stream()
                .filter(match -> match.getMatchId() == matchId)
                .findFirst()
                .orElse(null);  // Return null if match not found
    }

    // Display all matches in the event
    public void displayAllMatches() {
        event.getFixtures().forEach(Match::displayMatchDetails);
    }
}
