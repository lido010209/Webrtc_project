package com.example.calling.authentication.user.oauth2;

import com.example.calling.authentication.user.document.UserDocument;
import com.example.calling.authentication.user.dto.UserResponse;
import com.example.calling.authentication.user.repo.AuthorityRepo;
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
public class KakaoService extends DefaultOAuth2UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String password = oAuth2User.getAttributes().get("id").toString();
        String username = profile.get("nickname").toString();
        String email = profile.get("email").toString();
        String name = kakaoAccount.get("name").toString();
        UserDocument user;
        if (userRepo.existsByUsername(username)){
            user = UserDocument.builder()
                    .username(email)
                    .name(name)
                    .password(encoder.encode(password))
                    .avatar("/static/visual/user.png")
                    .build();
        } else{
            user = UserDocument.builder()
                    .username(username)
                    .name(name)
                    .password(encoder.encode(password))
                    .avatar("/static/visual/user.png")
                    .build();
        }
        user.getAuthorityRoles().add("ROLE_USER");
        userRepo.save(user);
        UserResponse response = UserResponse.dto(user);
        Map<String, Object> attributes = response.getAttributes();
        attributes.put("provider", "kakao");
        return new DefaultOAuth2User(
                response.getAuthorities(),
                attributes,
                "username"
        );
    }
}
