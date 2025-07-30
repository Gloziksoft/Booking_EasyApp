package com.gloziksoft.booking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a source of Spring bean definitions
public class ApplicationSecurityConfiguration {

    /**
     * Configures HTTP security, including authorization rules and login/logout behavior.
     *
     * @param http the HttpSecurity to modify
     * @return the configured SecurityFilterChain
     * @throws Exception in case of any security configuration errors
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                // Restrict access to reservation-related paths to users with USER or ADMIN roles
                .requestMatchers(
                        "/reservations",
                        "/reservations/create",
                        "/reservations/create/**",
                        "/reservations/detail/**",
                        "/reservations/edit/**",
                        "/reservations/delete/**"
                ).hasAnyRole("USER", "ADMIN")

                // Allow access to public resources and pages without authentication
                .requestMatchers(
                        "/styles/**", "/scripts/**", "/images/**", "/fonts/**",
                        "/", "/about-us", "/account/register", "/account/login",
                        "/offers", "/offers/**",
                        "/api/reservations/**"
                ).permitAll()

                // All other requests require authentication
                .anyRequest().authenticated()

                .and()
                .formLogin()
                // Custom login page configuration
                .loginPage("/account/login")
                .loginProcessingUrl("/account/login")
                .defaultSuccessUrl("/reservations", true)
                .usernameParameter("email") // Use "email" as the username field
                .permitAll()

                .and()
                .logout()
                // Custom logout behavior
                .logoutUrl("/account/logout")
                .logoutSuccessUrl("/")
                .permitAll()

                .and()
                .build();
    }

    /**
     * Defines the password encoder bean using BCrypt for secure password hashing.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
