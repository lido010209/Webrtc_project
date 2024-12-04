package com.example.calling.authentication.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String pwCheck;
    private String newPw;
}
