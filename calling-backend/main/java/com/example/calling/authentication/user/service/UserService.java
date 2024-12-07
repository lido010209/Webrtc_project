package com.example.calling.authentication.user.service;

import com.example.calling.authentication.component.UserComponent;
import com.example.calling.authentication.user.document.UserDocument;
import com.example.calling.authentication.user.dto.UserDto;
import com.example.calling.authentication.user.dto.UserRequest;
import com.example.calling.authentication.user.dto.UserResponse;
import com.example.calling.authentication.user.repo.UserRepo;
import com.example.calling.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final UserComponent userComponent;
    public UserResponse newUser(UserRequest dto){
        if (userRepo.existsByUsername(dto.getUsername()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Username already exists!!!");
        if (!dto.getPassword().equals(dto.getPwCheck()))
            throw new CustomException(HttpStatus.CONFLICT, "Password and confirm password do not match!!!");
        UserDocument user= UserDocument.builder()
                .username(dto.getUsername())
                .password(encoder.encode(dto.getPassword()))
                .avatar("@/assets/user/user.png")
                .name(dto.getName())
                .build();
        user.getAuthorityRoles().add("ROLE_USER");
        return UserResponse.dto(userRepo.save(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDocument user = userRepo.findByUsername(username).orElseThrow(
                ()-> new CustomException(HttpStatus.NOT_FOUND, "No exist username!!!")
        );
        return UserResponse.dto(user);
    }

    public UserDto userLogin(){
        return UserDto.dto(userComponent.userLogin());
    }

    public UserDto friend(String friendId){
        return UserDto.dto(userComponent.userById(friendId));
    }
    public List<UserDto> allFriends(){
        List<UserDto> list = new ArrayList<>();
        for (UserDocument user: userRepo.findAll()) {
            if (!user.getId().equals(userComponent.userLogin().getId())){
                list.add(UserDto.dto(user));
            }
        }
        return list;
    }

    public UserDto changePw (UserRequest dto){
        if (!dto.getPwCheck().equals(dto.getNewPw()))
            throw new CustomException(HttpStatus.CONFLICT, "New password and password confirm do not match!!!");
        UserDocument user = userComponent.userLogin();
        if (!encoder.matches(dto.getPassword(), user.getPassword()))
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Your current password is wrong!!!");
        user.setPassword(encoder.encode(dto.getNewPw()));
        return UserDto.dto(user);
    }
}
