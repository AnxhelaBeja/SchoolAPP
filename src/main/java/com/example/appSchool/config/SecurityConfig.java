package com.example.appSchool.config;

import com.example.appSchool.filter.JwtAuthenticationFilter;
import com.example.appSchool.model.Rolee;
import com.example.appSchool.service.User1DetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration

public class SecurityConfig {
    private User1DetailsServiceImp user1DetailsServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(User1DetailsServiceImp user1DetailsServiceImp, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.user1DetailsServiceImp = user1DetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req ->req.requestMatchers("/login/**" ,"/register/**", "/reset-password/**", "/static/**")
                                .permitAll()
                                .requestMatchers("/api/v1/Professor/**", "/api/v1/api/v1/StudentAssignment/**").hasAuthority(Rolee.PROFESSOR.name())
                                .anyRequest()
                                .authenticated())
                .userDetailsService(user1DetailsServiceImp)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
   public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
           return configuration.getAuthenticationManager();
    }
}
