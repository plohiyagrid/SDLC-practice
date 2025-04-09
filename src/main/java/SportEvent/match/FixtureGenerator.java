package SportEvent.match;

import SportEvent.event.Event;
import SportEvent.registration.Registration;
import SportEvent.registration.TeamRegistration;
import SportEvent.venues.Venue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FixtureGenerator {
    public static List<Match> generateFixtures(Event event, List<Venue> venues) {
        List<TeamRegistration> teams = new ArrayList<>();
        for (Registration reg : event.getRegistrations()) {
            if (reg instanceof TeamRegistration) {
                teams.add((TeamRegistration) reg);
            }
        }

        if (teams.size() < 2) {
            System.out.println("Not enough teams for fixtures!");
            return new ArrayList<>();
        }

        List<Match> fixtures = new ArrayList<>();
        Random random = new Random();
        int matchId = 1;

        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                if (!venues.isEmpty()) {
                    Venue venue = venues.get(random.nextInt(venues.size()));
                    Date matchDate = new Date(System.currentTimeMillis() + (matchId * 86400000L));
                    fixtures.add(new Match(matchId++, teams.get(i), teams.get(j), venue, matchDate));
                }
            }
        }

        return fixtures;
    }
}