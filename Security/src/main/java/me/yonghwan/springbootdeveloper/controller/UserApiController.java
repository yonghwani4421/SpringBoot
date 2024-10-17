package me.yonghwan.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.yonghwan.springbootdeveloper.dto.AddUserRequest;
import me.yonghwan.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-17
 * @PARAM
 * @VERSION 1.0
 */
@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request){
        userService.save(request);
        return "redirect:/login";

    }
}
