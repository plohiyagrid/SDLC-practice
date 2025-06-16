package SportEvent.registration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("TEAM")
public class TeamRegistration extends Registration {
    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    private Integer teamSize;

    @ElementCollection
    @CollectionTable(name = "team_player_names", 
                    joinColumns = @JoinColumn(name = "team_registration_id"))
    @Column(name = "player_name")
    private List<String> playerNames = new ArrayList<>();

    protected TeamRegistration() {
        super();
    }

    public TeamRegistration(String teamName, String contactName, String contactEmail, String contactPhone) {
        super(teamName, contactName, contactEmail, contactPhone);
        this.teamName = teamName;
        this.teamSize = 0;
    }

    public void addPlayer(String playerName) {
        if (playerNames.size() < teamSize) {
            playerNames.add(playerName);
            System.out.println("✅ Added player: " + playerName);
        } else {
            System.out.println("❌ Team is full!");
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(Integer teamSize) {
        this.teamSize = teamSize;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }
}
