package com.example.calling.authentication.token;

import com.example.calling.authentication.component.UserComponent;
import com.example.calling.authentication.token.dto.TokenRequest;
import com.example.calling.authentication.token.dto.TokenResponse;
import com.example.calling.authentication.user.document.UserDocument;
import com.example.calling.authentication.user.dto.UserResponse;
import com.example.calling.authentication.user.service.UserService;
import com.example.calling.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final UserComponent userComponent;

    public TokenResponse token(TokenRequest dto){
        UserResponse user = (UserResponse) userService.loadUserByUsername(dto.getUsername());
        if (!encoder.matches(dto.getPassword(), user.getPassword()))
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Wrong password!!!");
        TokenResponse response = TokenResponse.builder()
                .token(tokenUtils.generateToken(dto.getUsername())).build();
        return response;
    }

    public TokenResponse tokenOAuth2(){
        UserDocument user = userComponent.userLogin();
        String token = tokenUtils.generateToken(user.getUsername());
        TokenResponse response = TokenResponse.builder().token(token).build();
        return response;
    }

}
