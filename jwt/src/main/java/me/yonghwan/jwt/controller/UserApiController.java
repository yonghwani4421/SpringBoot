package me.yonghwan.jwt.controller;

import lombok.RequiredArgsConstructor;
import me.yonghwan.jwt.domain.User;
import me.yonghwan.jwt.dto.AddUserRequest;
import me.yonghwan.jwt.dto.AddUserResponse;
import me.yonghwan.jwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    @PostMapping("/join")
    public ResponseEntity<AddUserResponse> join(@RequestBody AddUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }


}
