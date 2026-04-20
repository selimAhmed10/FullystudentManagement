package org.example.fullystudentmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // @PreAuthorize ব্যবহার করতে
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // পাসওয়ার্ড encrypt করার জন্য
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // আমরা REST API ব্যবহার করছি, তাই CSRF বন্ধ
                .authorizeHttpRequests(auth -> auth
                        // স্ট্যাটিক ফাইল ও লগিন পেজ সবার জন্য অনুমতি
                        .requestMatchers("/", "/login.html", "/style.css", "/api.js",
                                "/admin.html", "/teacher.html", "/student.html",
                                "/api/auth/**").permitAll()
                        .anyRequest().authenticated() // বাকি সব endpoint এ authentication লাগবে
                )
                .httpBasic(); // HTTP Basic authentication ব্যবহার করব
        return http.build();
    }
}