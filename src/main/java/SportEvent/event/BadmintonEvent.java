package SportEvent.event;

import SportEvent.registration.Registration;
import SportEvent.registration.IndividualRegistration;
import SportEvent.registration.TeamRegistration;

import java.util.Date;

public class BadmintonEvent extends Event {
    private boolean isDoubles;

    public BadmintonEvent(int id, String name) {
        super(id, name);
        this.isDoubles = false;
    }

    public BadmintonEvent(int id, String name, boolean isDoubles) {
        super(id, name);
        this.isDoubles = isDoubles;
    }

    public BadmintonEvent(int id, String name, Date eventDate) {
        super(id, name, eventDate);
        this.isDoubles = false;
    }

    public BadmintonEvent(int id, String name, boolean isDoubles, Date eventDate) {
        super(id, name, eventDate);
        this.isDoubles = isDoubles;
    }

    @Override
    public boolean registerParticipant(Registration registration) {
        if (!(registration instanceof IndividualRegistration || registration instanceof TeamRegistration)) {
            System.out.println("Badminton only allows individuals or doubles.");
            return false;
        }
        return super.registerParticipant(registration);
    }

    @Override
    public void setupEvent() {
        System.out.println("Setting up Badminton event. Format: " + (isDoubles ? "Doubles" : "Singles"));
    }
}