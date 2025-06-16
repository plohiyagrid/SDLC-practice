package SportEvent.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import javax.sql.DataSource;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConfigurationTest {

    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Test
    void testDatabaseConfiguration() {
        assertNotNull(dataSource, "DataSource should not be null");
        
        String dbUser = env.getProperty("spring.datasource.username");
        String dbName = env.getProperty("DB_NAME");
        String dbPort = env.getProperty("DB_PORT");
        
        assertNotNull(dbUser, "Database username should be configured");
        assertNotNull(dbName, "Database name should be configured");
        assertNotNull(dbPort, "Database port should be configured");
        
        System.out.println("✅ Database Configuration:");
        System.out.println("Username: " + dbUser);
        System.out.println("Database: " + dbName);
        System.out.println("Port: " + dbPort);
    }

    @Test
    void testActiveProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        assertTrue(activeProfiles.length > 0, "At least one profile should be active");
        System.out.println("✅ Active Profile: " + String.join(", ", activeProfiles));
    }
} 