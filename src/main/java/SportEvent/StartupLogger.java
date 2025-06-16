package SportEvent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
@EnableConfigurationProperties(StartupLogger.DateFormatProperties.class)
public class StartupLogger {

    private static final Logger logger = LoggerFactory.getLogger(StartupLogger.class);

    // ✅ SpEL Example: Read dash-separated tags
    @Value("#{'${tags:default-tag}'.split('-')}")
    private String[] tags;

    @Bean
    public CommandLineRunner startupInfo(Environment environment, DateFormatProperties dateProps) {
        return args -> {
            logSystemInfo(dateProps);
            logActiveProfiles(environment);
            logJvmInfo();
            logTags();
            logDateFormats(dateProps);
        };
    }

    @Bean
    public CommandLineRunner logEnvironment(ApplicationContext ctx, Environment env) {
        return args -> {
            System.out.println("\n======================================================================================================== Beans Started ========================================================================================================");
            Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
            System.out.println("\n======================================================================================================== Beans Ended ========================================================================================================");

            System.out.println("\n======================================================================================================== Environment Properties Log Started ========================================================================================================");
            for (Iterator<?> it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext(); ) {
                PropertySource<?> ps = (PropertySource<?>) it.next();
                if (ps.getSource() instanceof Map<?, ?>) {
                    ((Map<?, ?>) ps.getSource()).forEach((k, v) -> System.out.println(k + "=" + v));
                }
            }
            System.out.println("\n======================================================================================================== Environment Properties Log Ended ========================================================================================================");
        };
    }

    private void logSystemInfo(DateFormatProperties props) {
        logger.info("======================================================================================================== Application Startup Log Information Started ========================================================================================================");
        String pattern = props.getFormats().getOrDefault("iso", "dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter dynamicFormatter = DateTimeFormatter.ofPattern(pattern);

        logger.info("Startup Time: {}", LocalDateTime.now().format(dynamicFormatter));
        logger.info("Operating System: {} {}", System.getProperty("os.name"), System.getProperty("os.version"));
        logger.info("Java Version: {}", System.getProperty("java.version"));
        logger.info("Available Processors: {}", Runtime.getRuntime().availableProcessors());
        logger.info("Total Memory: {} MB", Runtime.getRuntime().totalMemory() / (1024 * 1024));
        logger.info("Max Memory: {} MB", Runtime.getRuntime().maxMemory() / (1024 * 1024));
    }

    private void logActiveProfiles(Environment environment) {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            logger.warn("🚨 No active profile set. Using default configuration.");
        } else {
            logger.info("✅ Active Spring Profiles: {}", Arrays.toString(activeProfiles));
        }
    }

    private void logJvmInfo() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        logger.info("JVM Uptime: {} ms", runtimeBean.getUptime());
        logger.info("JVM Input Arguments: {}", runtimeBean.getInputArguments());
    }

    private void logTags() {
        logger.info("📌 Tags from properties (SpEL): {}", Arrays.toString(tags));
    }

    private void logDateFormats(DateFormatProperties props) {
        logger.info("🕒 Configured Date Formats: {}", props.getFormats());
        logger.info("======================================================================================================== End of Startup Information ========================================================================================================");
    }


    @ConfigurationProperties(prefix = "date")
    public static class DateFormatProperties {
        private Map<String, String> formats = new HashMap<>();
        public Map<String, String> getFormats() { return formats; }
        public void setFormats(Map<String, String> formats) { this.formats = formats; }
    }
}
