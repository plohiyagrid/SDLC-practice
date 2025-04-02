package SportEvent.registration;

import java.util.ArrayList;
import java.util.List;

public class TeamRegistration extends Registration {
    private String teamName;
    private List<String> playerNames;

    public TeamRegistration(String teamName, String contactName, String contactEmail, String contactPhone) {
        super(contactName, contactEmail, contactPhone);
        this.teamName = teamName;
        this.playerNames = new ArrayList<>();
    }

    public void addPlayer(String playerName) {
        playerNames.add(playerName);
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamSize() {
        return playerNames.size();
    }
}
