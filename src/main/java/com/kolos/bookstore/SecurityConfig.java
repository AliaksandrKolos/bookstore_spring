package com.kolos.bookstore;

import com.kolos.bookstore.web.view.AuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthSuccessHandler authSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                                .maximumSessions(1).maxSessionsPreventsLogin(true))
                .authorizeHttpRequests(auth -> auth

                        //VIEW URL PERMIT ALL
                        .requestMatchers("/", "/css/**", "/images/**", "/favicon.ico", "/WEB-INF/jsp/**", "/error",
                                "/books/getAll", "/users/registration", "/cart", "/books/addCart",
                                "/changeLanguage", "/books/search_title").permitAll()

                        // REST URL PERMIT ALL
                        .requestMatchers(HttpMethod.GET, "/api/books/{id:\\d+}", "/api/books",
                                "/api/books/search_title", "/api/messages").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/registration", "/api/orders").permitAll()

                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(authSuccessHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
