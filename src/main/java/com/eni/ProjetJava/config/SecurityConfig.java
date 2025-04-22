package com.eni.ProjetJava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll() // autorise toutes les requêtes
                )
                .csrf(csrf -> csrf.disable()) // désactive la protection CSRF pour les formulaires simples
                .formLogin(login -> login.disable()); // désactive le formulaire de login

        return http.build();
    }
}