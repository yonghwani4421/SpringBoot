package me.yonghwan.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-17
 * @PARAM
 * @VERSION 1.0
 */
@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("/articles")
    public String articles(){
        return "articles";
    }

}
