package me.yonghwan.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.yonghwan.springbootdeveloper.domain.User;
import me.yonghwan.springbootdeveloper.dto.AddUserRequest;
import me.yonghwan.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-17
 * @PARAM
 * @VERSION 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public Long save(AddUserRequest dto){
        User user = User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build();
        return userRepository.save(user).getId();
    }
}
