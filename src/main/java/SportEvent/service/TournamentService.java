package SportEvent.service;

import SportEvent.event.Event;
import SportEvent.match.Match;
import SportEvent.match.MatchStatus;
import SportEvent.registration.TeamRegistration;
import SportEvent.repository.EventRepository;
import SportEvent.repository.MatchRepository;
import SportEvent.repository.VenueRepository;
import SportEvent.venues.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TournamentService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    public Match createMatch(Long eventId, Long team1Id, Long team2Id, Long venueId, LocalDateTime startTime) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        TeamRegistration team1 = (TeamRegistration) event.getRegistrations().stream()
                .filter(r -> r.getId().equals(team1Id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Team 1 not found"));
        TeamRegistration team2 = (TeamRegistration) event.getRegistrations().stream()
                .filter(r -> r.getId().equals(team2Id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Team 2 not found"));
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        Match match = new Match(team1, team2, venue, startTime);
        event.addFixture(match);
        return matchRepository.save(match);
    }

    public Match completeMatch(Long matchId, String winnerTeamName) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));
        match.setWinner(winnerTeamName);
        return matchRepository.save(match);
    }

    public Match setMatchDraw(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));
        match.setDraw();
        return matchRepository.save(match);
    }

    public List<Match> getMatchesByEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return event.getFixtures();
    }

    public List<Match> getMatchesByVenue(Long venueId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));
        return venue.getMatches();
    }

    public List<Match> getMatchesByStatus(MatchStatus status) {
        return matchRepository.findByStatus(status);
    }
} 