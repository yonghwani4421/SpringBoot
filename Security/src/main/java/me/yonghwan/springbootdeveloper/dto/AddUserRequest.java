package me.yonghwan.springbootdeveloper.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-17
 * @PARAM
 * @VERSION 1.0
 */
@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String password;
}
