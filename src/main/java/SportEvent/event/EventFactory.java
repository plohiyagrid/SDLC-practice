package SportEvent.event;

import java.util.Date;

public class EventFactory {
    public static Event createCricketEvent(int id, String name, int overs, int teamSize) {
        return new CricketEvent(id, name, overs, teamSize);
    }

    public static Event createCricketEvent(int id, String name, int overs, int teamSize, Date eventDate) {
        return new CricketEvent(id, name, overs, teamSize, eventDate);
    }

    public static Event createBadmintonEvent(int id, String name, boolean isDoubles) {
        return new BadmintonEvent(id, name, isDoubles);
    }

    public static Event createBadmintonEvent(int id, String name, boolean isDoubles, Date eventDate) {
        return new BadmintonEvent(id, name, isDoubles, eventDate);
    }
}
