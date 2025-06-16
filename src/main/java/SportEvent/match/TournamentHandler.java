package SportEvent.match;

import SportEvent.event.Event;
import SportEvent.registration.TeamRegistration;
import SportEvent.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TournamentHandler {
    @Autowired
    private MatchRepository matchRepository;

    public void completeMatch(Long matchId, String winnerTeamName) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));
        match.setWinner(winnerTeamName);
        matchRepository.save(match);
    }

    public void setMatchDraw(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));
        match.setDraw();
        matchRepository.save(match);
    }

    public List<Match> getMatchesByEvent(Event event) {
        return event.getFixtures();
    }

    public List<Match> getMatchesByStatus(MatchStatus status) {
        return matchRepository.findByStatus(status);
    }
}
