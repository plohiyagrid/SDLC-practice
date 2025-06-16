package SportEvent.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("BADMINTON")
public class BadmintonEvent extends Event {
    protected BadmintonEvent() {
        super();
    }

    public BadmintonEvent(String name, LocalDateTime startDate) {
        super(name, startDate);
    }

    @Override
    public void setupEvent() {
        // Badminton specific setup logic
        System.out.println("Setting up Badminton Event: " + getName());
        System.out.println("- Setting up badminton courts");
        System.out.println("- Installing nets");
        System.out.println("- Arranging shuttlecocks and rackets");
    }
}