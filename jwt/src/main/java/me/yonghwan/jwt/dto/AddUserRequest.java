package me.yonghwan.jwt.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.yonghwan.jwt.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String password;
    private String role;
}
