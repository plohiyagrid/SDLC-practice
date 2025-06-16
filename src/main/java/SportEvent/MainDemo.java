// File: SportEvent/MainDemo.java
package SportEvent;

import SportEvent.event.CricketEvent;
import SportEvent.event.Event;
import SportEvent.event.EventStatus;
import SportEvent.registration.TeamRegistration;
import SportEvent.venues.Venue;

import java.time.LocalDateTime;
import java.util.Arrays;

public class MainDemo {
    public static void main(String[] args) {
        // Create venues
        Venue venue1 = new Venue("Lords Cricket Ground", "London", 30000);
        Venue venue2 = new Venue("MCG", "Melbourne", 100000);

        // Create a cricket event
        CricketEvent cricketEvent = new CricketEvent("IPL 2024", LocalDateTime.now().plusDays(30));
        cricketEvent.setVenue(venue1);

        // Create team registrations
        TeamRegistration team1 = new TeamRegistration("Mumbai Indians", "Rohit Sharma", "rohit@mi.com", "+91-9999999999");
        team1.setTeamSize(11);
        team1.addPlayer("Rohit Sharma");
        team1.addPlayer("Hardik Pandya");

        TeamRegistration team2 = new TeamRegistration("Chennai Super Kings", "MS Dhoni", "dhoni@csk.com", "+91-8888888888");
        team2.setTeamSize(11);
        team2.addPlayer("MS Dhoni");
        team2.addPlayer("Ravindra Jadeja");

        // Register teams
        cricketEvent.registerParticipant(team1);
        cricketEvent.registerParticipant(team2);

        // Close registration and start event
        cricketEvent.setStatus(EventStatus.REGISTRATION_CLOSED);
        System.out.println("\nEvent Status: " + cricketEvent.getStatus());

        // Setup event
        cricketEvent.setupEvent();

        // Display registrations
        System.out.println("\nRegistered Teams:");
        cricketEvent.getRegistrations().forEach(registration -> {
            TeamRegistration team = (TeamRegistration) registration;
            System.out.println("- " + team.getTeamName() + " (Contact: " + team.getContactName() + ")");
        });
    }
}
