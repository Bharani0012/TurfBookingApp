package com.example.TurfBookingApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/owners/register").permitAll() // Permit access to the registration endpoint
                        .anyRequest().authenticated() // Require authentication for other endpoints
                )
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login") // Customize login page if needed
                        .permitAll()
                )
                .logout((logout) -> logout
                        .permitAll()
                );
        return http.build();
    }
}