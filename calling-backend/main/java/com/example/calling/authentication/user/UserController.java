package com.example.calling.authentication.user;

import com.example.calling.authentication.component.UserComponent;
import com.example.calling.authentication.user.dto.UserDto;
import com.example.calling.authentication.user.dto.UserRequest;
import com.example.calling.authentication.user.dto.UserResponse;
import com.example.calling.authentication.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    @PostMapping
    public UserResponse newUser (@RequestBody UserRequest dto){
        return userService.newUser(dto);
    }
    @GetMapping
    public UserDto userLogin(){
        return userService.userLogin();
    }
    @GetMapping("{friendId}")
    public UserDto friend(@PathVariable("friendId") String friendId){
        return userService.friend(friendId);
    }
    @GetMapping("all-friends")
    public List<UserDto> allFriends(){
        return userService.allFriends();
    }
    @PutMapping("pw")
    public UserDto changePw(@RequestBody UserRequest dto){
        return userService.changePw(dto);
    }

}
