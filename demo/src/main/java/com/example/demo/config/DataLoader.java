package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner loadData(RoleRepository roleRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // Create roles if they don't exist
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ROLE_ADMIN");
                        return roleRepository.save(role);
                    });

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ROLE_USER");
                        return roleRepository.save(role);
                    });

            // Create admin user if not exists
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                // Ensure consistent password encoding
                // Use the autowired passwordEncoder
                String adminPassword = passwordEncoder.encode("admin123");
                System.out.println("Admin encoded password: " + adminPassword);
                admin.setPassword(adminPassword);
                admin.setEnabled(true);
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }

            // Create regular user if not exists
            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                // Ensure consistent password encoding
                // Use the autowired passwordEncoder
                String userPassword = passwordEncoder.encode("user123");
                System.out.println("User encoded password: " + userPassword);
                user.setPassword(userPassword);
                user.setEnabled(true);
                user.setRole("USER");
                userRepository.save(user);
            }
        };
    }
}
