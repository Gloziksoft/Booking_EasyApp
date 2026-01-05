package com.booking.app.configuration;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
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
    @Order(0)
    public SecurityFilterChain actuatorSecurity(HttpSecurity http) throws Exception {
        http
                .securityMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .requiresChannel(channel -> channel.anyRequest().requiresInsecure())
                .requestCache(request -> request.disable())
                .securityContext(securityContext -> securityContext.disable())
                .sessionManagement(session -> session.disable())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
    /**
     * ============================
     * APPLICATION SECURITY
     * ============================
     * Tvoja existujúca aplikačná logika.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth

                        // Rezervácie: USER, ADMIN
                        .requestMatchers(
                                "/reservations",
                                "/reservations/create/**",
                                "/reservations/edit/**",
                                "/reservations/delete/**"
                        ).hasAnyRole("USER", "ADMIN")

                        // Ponuky: ADMIN
                        .requestMatchers(
                                "/offers/create",
                                "/offers/create/**",
                                "/offers/*/edit",
                                "/offers/*/delete"
                        ).hasRole("ADMIN")

                        // Reset hesla
                        .requestMatchers(HttpMethod.GET, "/account/reset-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/account/reset-password").permitAll()

                        // Verejné cesty
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

                        // Všetko ostatné vyžaduje login
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
                )
                .build();
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
