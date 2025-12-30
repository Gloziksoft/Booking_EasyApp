package com.booking.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()

                .requestMatchers("/actuator/**").permitAll()


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

                // 🔥 Reset hesla - povoliť GET aj POST
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
                .anonymous()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}