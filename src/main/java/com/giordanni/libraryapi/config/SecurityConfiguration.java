package com.giordanni.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // CSRF desabilitado porque a API é stateless e usa autenticação via JWT; não dependemos de cookies, então não há risco de ataques CSRF.
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll(); // permitir acesso à página de login personalizada para todos os usuários
                }) // forma básica de login com formulário padrão
                .httpBasic(Customizer.withDefaults()) // autenticação HTTP básica
                .authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().authenticated(); // todas as requisições exigem autenticação
                })
                .build();
    }
}
