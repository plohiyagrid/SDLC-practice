package SportEvent.event;

import java.time.LocalDateTime;

public class EventFactory {

    public static CricketEvent createCricketEvent(String name, LocalDateTime startDate) {
        return new CricketEvent(name, startDate);
    }

    public static BadmintonEvent createBadmintonEvent(String name, LocalDateTime startDate) {
        return new BadmintonEvent(name, startDate);
    }
}
