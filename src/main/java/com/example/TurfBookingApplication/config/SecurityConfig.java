package com.example.TurfBookingApplication.config;


import com.example.TurfBookingApplication.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return   http
                .cors(Customizer.withDefaults())
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/owners/register", "/owners/login").permitAll() // Permit access to the registration and login endpoints
                        .requestMatchers("/owners/**").authenticated() // Require authentication for other /owners endpoints
                )
                .formLogin((formLogin) -> formLogin
                        .loginPage("/owners/login") // Customize login page if needed
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .build();
    }
}

