package SportEvent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Disable CSRF for H2 console
            .headers().frameOptions().disable()  // Disable X-Frame-Options for H2 console
            .and()
            .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()  // Allow H2 console access
                .antMatchers("/api/test/**").permitAll()    // Allow test endpoints
                .anyRequest().authenticated()
            .and()
            .httpBasic();  // Enable basic authentication
    }
} 