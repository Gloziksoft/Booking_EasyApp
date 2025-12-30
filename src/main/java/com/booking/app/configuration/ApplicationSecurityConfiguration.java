package com.booking.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationSecurityConfiguration {

    /**
     * ============================
     * ACTUATOR SECURITY
     * ============================
     * Musí byť PRVÝ filter chain.
     * Žiadny login, žiadne session, žiadne CSRF.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")   // ⬅⬅⬅ TOTO TAM MUSÍ BYŤ
                .authorizeHttpRequests(auth -> auth

                        // Rezervácie
                        .requestMatchers(
                                "/reservations",
                                "/reservations/create/**",
                                "/reservations/edit/**",
                                "/reservations/delete/**"
                        ).hasAnyRole("USER", "ADMIN")

                        // Ponuky
                        .requestMatchers(
                                "/offers/create",
                                "/offers/create/**",
                                "/offers/*/edit",
                                "/offers/*/delete"
                        ).hasRole("ADMIN")

                        // Verejné
                        .requestMatchers(
                                "/styles/**", "/scripts/**", "/images/**", "/fonts/**",
                                "/", "/about-us",
                                "/account/register",
                                "/account/login",
                                "/account/forgot-password",
                                "/reservations/detail/**",
                                "/send-test-email",
                                "/email/**",
                                "/offers", "/offers/{id}", "/offers/{id}/reserve",
                                "/api/reservations/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/account/login")
                        .loginProcessingUrl("/account/login")
                        .defaultSuccessUrl("/reservations", true)
                        .usernameParameter("email")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/account/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }


    /**
     * ============================
     * PASSWORD ENCODER
     * ============================
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
