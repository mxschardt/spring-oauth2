package com.mxschardt.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Настройка авторизации запросов и аутентификация через oauth2.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    // Запросы к серверу ресурсов по Authorization Code Flow
                    //      автоматически требуют авторизации у сервера аутентификации.
//                   auth.requestMatchers("/secured").authenticated();
                    auth.anyRequest().permitAll();
                })
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

}