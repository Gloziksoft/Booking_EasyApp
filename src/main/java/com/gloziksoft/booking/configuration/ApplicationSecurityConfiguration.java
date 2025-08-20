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
                // Rezervácie prístupné pre USER a ADMIN
                .requestMatchers(
                        "/reservations",
                        "/reservations/create/**",
                        "/reservations/edit/**",
                        "/reservations/delete/**"
                ).hasAnyRole("USER", "ADMIN")

                // Ponuky prístupné iba pre ADMIN
                .requestMatchers(
                        "/offers/create",
                        "/offers/create/**",
                        "/offers/*/edit",
                        "/offers/*/edit/**",
                        "/offers/*/delete"
                ).hasRole("ADMIN")

                // Verejné cesty
                .requestMatchers(
                        "/styles/**", "/scripts/**", "/images/**", "/fonts/**",
                        "/", "/about-us", "/account/register", "/account/login", "/account/forgot-password",
                        "/reservations/detail/**",
                        "/offers", "/offers/{id}", "/offers/{id}/reserve",
                        "/api/reservations/**"
                ).permitAll()

                // Všetko ostatné vyžaduje autentifikáciu
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/account/login")
                .loginProcessingUrl("/account/login")
                .defaultSuccessUrl("/reservations", true)
                .usernameParameter("email")
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/account/logout")
                .logoutSuccessUrl("/")
                .permitAll()

                .and()
                .anonymous() // Toto zabezpečí, že Thymeleaf pozná anonymného používateľa
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