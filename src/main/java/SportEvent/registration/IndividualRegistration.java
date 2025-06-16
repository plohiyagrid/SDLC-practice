package SportEvent.registration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("INDIVIDUAL")
public class IndividualRegistration extends Registration {
    protected IndividualRegistration() {
        super();
    }

    public IndividualRegistration(String participantName, String contactName, String contactEmail, String contactPhone) {
        super(participantName, contactName, contactEmail, contactPhone);
    }
}
