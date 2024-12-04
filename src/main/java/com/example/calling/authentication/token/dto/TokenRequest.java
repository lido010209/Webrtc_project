package com.example.calling.authentication.token.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequest {
    private String username;
    private String password;
}
