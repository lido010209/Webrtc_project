package com.example.calling.config;

import com.example.calling.authentication.token.TokenHandler;
import com.example.calling.authentication.token.TokenUtils;
import com.example.calling.authentication.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers(HttpMethod.POST, "/users").permitAll();
                    auth.requestMatchers("/token/**", "/static/**").permitAll();
                    auth.anyRequest().authenticated();
//                    auth.anyRequest().permitAll();
                })
                .addFilterBefore(
                        new TokenHandler(tokenUtils, userService),
                        AuthorizationFilter.class
                );
        return http.build();
    }
}
