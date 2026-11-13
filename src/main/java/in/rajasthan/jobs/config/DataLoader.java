package in.rajasthan.jobs.config;

import in.rajasthan.jobs.model.User;
import in.rajasthan.jobs.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner seedAdmin(UserRepository users) {
        return args -> {
            if (users.findByEmail("admin@rajasthan.gov.in").isEmpty()) {
                User admin = new User("Platform Admin","admin@rajasthan.gov.in",
                    new BCryptPasswordEncoder().encode("Admin@123"),"ADMIN");
                users.save(admin);
            }
        };
    }
}
