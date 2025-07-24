package com.gloziksoft.booking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationSecurityConfiguration {

    // Konfigurácia pravidiel zabezpečenia HTTP požiadaviek a autentifikácie
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers(
                        "/reservations",
                        "/reservations/create",
                        "/reservations/create/**",
                        "/reservations/detail/**",
                        "/reservations/edit/**",
                        "/reservations/delete/**"
                ).hasAnyRole("USER", "ADMIN")

                .requestMatchers(
                        // Verejné statické zdroje a stránky
                        "/styles/**", "/scripts/**", "/images/**", "/fonts/**",
                        "/", "/about-us", "/account/register", "/account/login",
                        "/offers", "/offers/**",
                        "/api/reservations/**"
                ).permitAll()

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
                .build();
    }

    // BCrypt hashovanie hesiel
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
