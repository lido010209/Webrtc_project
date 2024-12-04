package com.example.calling.authentication.component;

import com.example.calling.authentication.user.document.UserDocument;
import com.example.calling.authentication.user.repo.UserRepo;
import com.example.calling.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserComponent {
    private final UserRepo userRepo;
    public UserDocument userLogin(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDocument user = userRepo.findByUsername(username).orElseThrow(
                ()->new CustomException(HttpStatus.NOT_FOUND, "No exist user login!!")
        );
        return user;
    }
    public UserDocument userById(String id){
        UserDocument user = userRepo.findById(id).orElseThrow(
                ()-> new CustomException(HttpStatus.NOT_FOUND, "No exist this user!!")
        );
        return user;
    }
}
