package SportEvent.registration;

public class IndividualRegistration extends Registration {
    private String playerName;
    private int age;

    public IndividualRegistration(String playerName, int age, String contactName, String contactEmail, String contactPhone) {
        super(contactName, contactEmail, contactPhone);
        this.playerName = playerName;
        this.age = age;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getAge() {
        return age;
    }
}
