package com.example.calling.authentication.user.dto;

import com.example.calling.authentication.user.document.UserDocument;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {
    private String id;
    private String username;
    private String avatar;
    public static UserDto dto (UserDocument user){
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatar(user.getAvatar()).build();
        return userDto;
    }
}
