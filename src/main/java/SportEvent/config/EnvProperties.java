package SportEvent.config;

public class EnvProperties {
    private final String dbUser;
    private final String dbPassword;
    private final String dbName;
    private final String dbPort;

    public EnvProperties(String dbUser, String dbPassword, String dbName, String dbPort) {
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbName = dbName;
        this.dbPort = dbPort;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbPort() {
        return dbPort;
    }
} 