package SportEvent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private Environment env;

    @GetMapping("/config")
    public Map<String, Object> getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("activeProfiles", env.getActiveProfiles());
        config.put("databaseName", env.getProperty("DB_NAME"));
        config.put("databasePort", env.getProperty("DB_PORT"));
        config.put("serverPort", env.getProperty("server.port"));
        return config;
    }

    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("message", "Sport Event Manager is running!");
        return status;
    }
} 