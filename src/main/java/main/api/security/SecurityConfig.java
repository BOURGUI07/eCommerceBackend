/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

/**
 *
 * @author hp
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    public SecurityConfig(JWTRequestFilter filter) {
        this.filter = filter;
    }
    private JWTRequestFilter filter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(filter, AuthorizationFilter.class);
        http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/products","/auth/register","/auth/login","/auth/verify").permitAll()
                .anyRequest().authenticated()  // Permit access to all requests
            )
            .csrf(csrf -> csrf.disable());  // Disable CSRF protection

        return http.build();
    }
}
