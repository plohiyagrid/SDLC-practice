package SportEvent.event;

import SportEvent.registration.Registration;
import SportEvent.registration.TeamRegistration;

import java.util.Date;

public class CricketEvent extends Event {
    private int overs;
    private int teamSize;

    public CricketEvent(int id, String name, int overs, int teamSize) {
        super(id, name);
        this.overs = overs;
        this.teamSize = teamSize;
    }

    public CricketEvent(int id, String name, int overs, int teamSize, Date eventDate) {
        super(id, name, eventDate);
        this.overs = overs;
        this.teamSize = teamSize;
    }

    @Override
    public boolean registerParticipant(Registration registration) {
        if (!(registration instanceof TeamRegistration)) {
            System.out.println("Only teams can register for Cricket.");
            return false;
        }
        return super.registerParticipant(registration);
    }

    @Override
    public void setupEvent() {
        System.out.println("Setting up Cricket event with " + overs + " overs and " + teamSize + " players per team.");
    }
}
