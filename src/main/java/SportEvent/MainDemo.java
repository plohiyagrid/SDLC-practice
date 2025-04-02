// File: SportEvent/MainDemo.java
package SportEvent;

import SportEvent.event.Event;
import SportEvent.event.EventFactory;
import SportEvent.event.EventStatus;
import SportEvent.match.TournamentHandler;
import SportEvent.registration.IndividualRegistration;
import SportEvent.registration.TeamRegistration;
import SportEvent.venues.Venue;
import SportEvent.venues.VenueAssignmentHandler;

import java.util.Date;

public class MainDemo {
    public static void main(String[] args) {
        // Initialize the EventManager
        EventManager eventManager = new EventManager();

        // Create venues
        Venue venue1 = new Venue(1, "Central Stadium", "Downtown", 5000);
        Venue venue2 = new Venue(2, "East Ground", "Eastside", 3000);
        eventManager.addVenue(venue1);
        eventManager.addVenue(venue2);

        // Create events
        Event cricketEvent = EventFactory.createCricketEvent(1, "T20 Cricket Tournament", 20, 11, new Date());
        Event badmintonEvent = EventFactory.createBadmintonEvent(2, "Badminton Championship", true, new Date());
        eventManager.addEvent(cricketEvent);
        eventManager.addEvent(badmintonEvent);

        TournamentHandler cricketTournamentHandler = new TournamentHandler(cricketEvent);

        // Register teams for cricket
        TeamRegistration team1 = new TeamRegistration("Eagles", "John Doe", "john@example.com", "123-456-7890");
        team1.addPlayer("Player 1");
        team1.addPlayer("Player 2");
        cricketEvent.registerParticipant(team1);

        TeamRegistration team2 = new TeamRegistration("Lions", "Jane Smith", "jane@example.com", "123-456-7891");
        team2.addPlayer("Player A");
        team2.addPlayer("Player B");
        cricketEvent.registerParticipant(team2);

        TeamRegistration team3 = new TeamRegistration("Lions", "Jane Smith", "jane@example.com", "123-456-7891");
        team2.addPlayer("Player p");
        team2.addPlayer("Player q");
        cricketEvent.registerParticipant(team3);

        TeamRegistration team4 = new TeamRegistration("Lions", "Jane Smith", "jane@example.com", "123-456-7891");
        team2.addPlayer("Player r");
        team2.addPlayer("Player s");
        cricketEvent.registerParticipant(team4);

        // Close registration for cricket event
        cricketEvent.updateEventStatus(EventStatus.REGISTRATION_CLOSED);

        // Generate fixtures for cricket event
        cricketEvent.generateFixtures(eventManager.getVenues());

        // Display the generated fixtures for the cricket event
        cricketEvent.displayFixtures();

        cricketTournamentHandler.completeMatch(1 , team2);
        cricketTournamentHandler.completeMatch(2, team3);

        cricketEvent.displayFixtures();



    }
}
