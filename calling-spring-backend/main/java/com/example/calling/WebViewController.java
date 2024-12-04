package com.example.calling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/connect")
public class WebViewController {
    @GetMapping("login")
    public String login(){
        return "user/login";
    }
    @GetMapping("signup")
    public String register(){
        return "user/create";
    }
    @GetMapping("profile")
    public String profile(){
        return "user/profile";
    }
    @GetMapping("home")
    public String home(){
        return "home";
    }
}
