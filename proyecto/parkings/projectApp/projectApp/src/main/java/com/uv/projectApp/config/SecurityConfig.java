package com.uv.projectApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.uv.projectApp.security.RemoteJwtAuthFilter;

@Configuration
public class SecurityConfig {

    private final RemoteJwtAuthFilter jwtAuthFilter;

    public SecurityConfig(RemoteJwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println(">>> SecurityFilterChain configurado");
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/evento/**").hasAuthority("ROLE_APARCAMIENTOS")
                .requestMatchers("/api/aparcamiento/*/status**").permitAll() // Permitir acceso público a status
                .requestMatchers("/api/aparcamiento/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/aparcamientos").permitAll() // <-- AÑADIDO: listado público
                .anyRequest().permitAll()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
