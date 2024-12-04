package com.example.calling.authentication.user.dto;

import com.example.calling.authentication.user.document.Authority;
import com.example.calling.authentication.user.document.UserDocument;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Data
@Builder
public class UserResponse implements UserDetails, OAuth2User {
    private String username;
    private String password;
    private String name;
    private final Set<String> stringAuthorities = new HashSet<>();
    public static UserResponse dto (UserDocument user){
        UserResponse response= UserResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        for (String role: user.getAuthorityRoles()){
            response.stringAuthorities.add(role);
        }
        return response;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("username", username);
        attributes.put("password", password);
        attributes.put("name", name);
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stringAuthorities.stream().map(SimpleGrantedAuthority::new).toList();
    }
}
