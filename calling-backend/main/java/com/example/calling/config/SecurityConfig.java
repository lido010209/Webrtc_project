package com.example.calling.config;

import com.example.calling.authentication.token.TokenHandler;
import com.example.calling.authentication.token.TokenOAuth2Handler;
import com.example.calling.authentication.token.TokenUtils;
import com.example.calling.authentication.user.oauth2.KakaoService;
import com.example.calling.authentication.user.oauth2.NaverService;
import com.example.calling.authentication.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final NaverService naverService;
    private final KakaoService kakaoService;
    private final TokenOAuth2Handler oAuth2Handler;
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers(HttpMethod.POST, "/users").permitAll();
                    auth.requestMatchers("/token/**", "/static/**", "/connect/**", "/ws/**", "/topic", "/app").permitAll();
                    auth.anyRequest().authenticated();
//                    auth.anyRequest().permitAll();
                })
                .addFilterBefore(
                        new TokenHandler(tokenUtils, userService),
                        AuthorizationFilter.class
                )
                .oauth2Login(oauth2-> oauth2
                        .userInfoEndpoint(userInfo->userInfo
                                .userService((oAuth2UserServiceSelector())))
                        .successHandler(oAuth2Handler)
                        .permitAll()
                )
        ;
        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserServiceSelector(){
        return userRequest -> {
            String registerId = userRequest.getClientRegistration().getRegistrationId();
            if ("naver".equals(registerId)){
                return naverService.loadUser(userRequest);
            } else if ("kakao".equals(registerId)){
                return kakaoService.loadUser(userRequest);
            } else {
                throw new OAuth2AuthenticationException("Unsupported provider"+ registerId);
            }
        };
    }
}
