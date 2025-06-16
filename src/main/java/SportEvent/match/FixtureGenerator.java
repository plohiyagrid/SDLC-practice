package SportEvent.match;

import SportEvent.event.Event;
import SportEvent.registration.TeamRegistration;
import SportEvent.venues.Venue;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class FixtureGenerator {
    private static final int DEFAULT_MATCH_DURATION_MINUTES = 180;

    public List<Match> generateFixtures(Event event, List<Venue> venues) {
        List<Match> fixtures = new ArrayList<>();
        List<TeamRegistration> teams = event.getRegistrations().stream()
                .filter(r -> r instanceof TeamRegistration)
                .map(r -> (TeamRegistration) r)
                .toList();

        if (teams.size() < 2) {
            throw new RuntimeException("Not enough teams to generate fixtures");
        }

        LocalDateTime startTime = event.getStartDate();
        Random random = new Random();

        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                TeamRegistration team1 = teams.get(i);
                TeamRegistration team2 = teams.get(j);

                // Randomly select a venue
                Venue venue = venues.get(random.nextInt(venues.size()));
                while (!venue.isAvailable(startTime)) {
                    venue = venues.get(random.nextInt(venues.size()));
                }

                Match match = new Match(team1, team2, venue, startTime);
                fixtures.add(match);
                venue.book(startTime);

                // Next match starts after current match duration
                startTime = startTime.plusMinutes(DEFAULT_MATCH_DURATION_MINUTES);
            }
        }

        return fixtures;
    }
}