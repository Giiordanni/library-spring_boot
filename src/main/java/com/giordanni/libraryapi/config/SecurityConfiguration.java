package com.giordanni.libraryapi.config;

import com.giordanni.libraryapi.services.UserService;
import com.giordanni.libraryapi.seucurity.CustomDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // CSRF desabilitado porque a API é stateless e usa autenticação via JWT; não dependemos de cookies, então não há risco de ataques CSRF.

//                .formLogin(configurer -> {
//                    configurer.loginPage("/login").permitAll(); // permitir acesso à página de login personalizada para todos os usuários
//
//                }) // forma básica de login com formulário padrão
                .formLogin(Customizer.withDefaults()) // forma padrao de login com o google

                .httpBasic(Customizer.withDefaults()) // autenticação HTTP básica

                .authorizeHttpRequests(authorize -> {
//                    // Swagger liberado
//                    authorize.requestMatchers(
//                            "/v3/api-docs/**",
//                            "/swagger-ui/**",
//                            "/swagger-ui.html"
//                    ).permitAll();

                    authorize.requestMatchers( "/login").permitAll(); // permitir acesso à página de login para todos os usuários
                    authorize.requestMatchers(HttpMethod.POST,"/users/**").permitAll(); // permitir acesso aos endpoints de usuários para todos

//                    authorize.requestMatchers("/authors/**").hasAnyRole( "ADMIN"); // apenas usuários com papel ADMIN podem acessar endpoints de autores
//                    authorize.requestMatchers("/books/**").hasAnyRole("USER", "ADMIN"); // usuários com papel USER ou ADMIN podem acessar endpoints de livros

//                    authorize.requestMatchers(HttpMethod.POST, "/authors/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT, "/authors/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.DELETE, "/authors/**").hasRole("ADMIN");
//
//                    authorize.requestMatchers(HttpMethod.GET, "/authors/**").hasAnyRole("USER", "ADMIN");

                    authorize.anyRequest().authenticated(); // todas as requisições exigem autenticação
                    // nenhuma regra de requisição pode ser feita depois da anyRequest()
                })
                .oauth2Login(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService){ // PasswordEncoder encoder
//        UserDetails user1 = User.builder()
//                .username("user")
//                .password(encoder.encode("senha123"))
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("admin")
//                .password(encoder.encode("senha456"))
//                .roles("ADMIN")
//                .build();

//        return new InMemoryUserDetailsManager(user1, user2);

        return new CustomDetailsService(userService);
    }
}
