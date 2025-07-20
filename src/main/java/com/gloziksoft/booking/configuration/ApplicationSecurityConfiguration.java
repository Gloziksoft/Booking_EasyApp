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

                // Pridanie záznamu (napr. rezervácia) – len ADMIN
                .requestMatchers("/reservations/create").hasRole("ADMIN")

                // Prístup k zoznamu, detailom, úprave a mazaniu rezervácií – USER aj ADMIN
                .requestMatchers(
                        "/reservations",
                        "/reservations/detail/**",
                        "/reservations/edit/**",
                        "/reservations/delete/**"
                ).hasAnyRole("USER", "ADMIN")

                // Verejné cesty
                .requestMatchers(
                        "/styles/**", "/scripts/**", "/images/**", "/fonts/**",
                        "/", "/about", "/account/register", "/account/login"
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
