package SportEvent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("SportEvent")
@EnableJpaRepositories("SportEvent.repository")
public class SportEventApplication {
    public static void main(String[] args) {
        SpringApplication.run(SportEventApplication.class, args);
        System.out.println("\n✅ Sport Event Manager Application Started!");
        System.out.println("Access H2 Console (if using local profile): http://localhost:8080/h2-console");
    }
} 