package com.example.calling.authentication.user.oauth2;

import com.example.calling.authentication.user.document.UserDocument;
import com.example.calling.authentication.user.dto.UserResponse;
import com.example.calling.authentication.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NaverService extends DefaultOAuth2UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> map = oAuth2User.getAttribute("response");
        String email = map.get("email").toString();
        String username = map.get("nickname").toString();
        String name = map.get("name").toString();
        String password = map.get("id").toString();
        UserDocument user;
        if (userRepo.existsByUsername(username)){
            user = UserDocument.builder()
                    .username(email)
                    .name(name)
                    .password(encoder.encode(password))
                    .avatar("/static/visual/user.png").build();
        } else {
            user= UserDocument.builder()
                    .username(username)
                    .name(name)
                    .password(encoder.encode(password))
                    .avatar("/static/visual/user.png").build();
        }
        user.getAuthorityRoles().add("ROLE_USER");
        UserResponse response = UserResponse.dto(userRepo.save(user));
        Map<String, Object> attributes = response.getAttributes();
        attributes.put("provider", "naver");

        return new DefaultOAuth2User(
                response.getAuthorities(),
                attributes,
                "username"
        );
    }
}
