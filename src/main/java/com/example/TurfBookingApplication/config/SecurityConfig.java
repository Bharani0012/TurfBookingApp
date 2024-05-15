package com.example.TurfBookingApplication.config;


import com.example.TurfBookingApplication.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/owners/register", "/api/owners/login").permitAll() // Permit access to the registration endpoint
                        .anyRequest().authenticated() // Require authentication for other endpoints
                )
                .formLogin((formLogin) -> formLogin
                        .loginPage("/api/owners/login") // Customize login page if needed
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                );
        return http.build();
    }

}
