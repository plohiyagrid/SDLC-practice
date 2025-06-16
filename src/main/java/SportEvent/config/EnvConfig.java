package SportEvent.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class EnvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
                .directory(".")  // Root directory where .env is located
                .ignoreIfMissing()
                .load();
    }

    @Bean
    public EnvProperties loadEnvVariables(Environment springEnv, Dotenv dotenv) {
        // Set system properties from .env file if not already set
        if (System.getProperty("DB_USER") == null) {
            System.setProperty("DB_USER", dotenv.get("DB_USER", "plohiya"));
        }
        if (System.getProperty("DB_PASSWORD") == null) {
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD", "Badu@2002"));
        }
        if (System.getProperty("DB_NAME") == null) {
            System.setProperty("DB_NAME", dotenv.get("DB_NAME", "myDomainDatabase"));
        }
        if (System.getProperty("DB_PORT") == null) {
            System.setProperty("DB_PORT", dotenv.get("DB_PORT", "5431"));
        }

        return new EnvProperties(
            System.getProperty("DB_USER"),
            System.getProperty("DB_PASSWORD"),
            System.getProperty("DB_NAME"),
            System.getProperty("DB_PORT")
        );
    }
} 