package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/home", "/register", "/about", "/contact",
                                                                "/css/**", "/js/**", "/images/**", "/h2-console/**")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/blog/**").hasAnyRole("USER", "ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/home", true) // Chuyển hướng sau khi login thành
                                                                                  // công
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout") // URL logout
                                                .logoutSuccessUrl("/login?logout") // Redirect tới trang login sau khi
                                                                                   // logout thành công
                                                .permitAll())
                                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")) // Cho phép truy cập H2
                                                                                              // Console
                                .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Cho phép nhúng
                                                                                                     // iframe cho H2

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

}
