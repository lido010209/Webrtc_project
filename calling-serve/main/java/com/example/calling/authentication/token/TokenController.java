package com.example.calling.authentication.token;

import com.example.calling.authentication.token.dto.TokenRequest;
import com.example.calling.authentication.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;
    @PostMapping
    public TokenResponse token(@RequestBody TokenRequest dto){
        return tokenService.token(dto);
    }
    @GetMapping("oauth")
    public TokenResponse tokenOAuth2(){
        return tokenService.tokenOAuth2();
    }
}
