package SportEvent.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CRICKET")
public class CricketEvent extends Event {
    protected CricketEvent() {
        super();
    }

    public CricketEvent(String name, LocalDateTime startDate) {
        super(name, startDate);
    }

    @Override
    public void setupEvent() {
        // Cricket specific setup logic
        System.out.println("Setting up Cricket Event: " + getName());
        System.out.println("- Preparing cricket pitch");
        System.out.println("- Setting up boundary ropes");
        System.out.println("- Arranging cricket equipment");
    }
}
