package me.yonghwan.jwt.service;

import lombok.RequiredArgsConstructor;
import me.yonghwan.jwt.domain.User;
import me.yonghwan.jwt.dto.AddUserRequest;
import me.yonghwan.jwt.dto.AddUserResponse;
import me.yonghwan.jwt.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AddUserResponse save(AddUserRequest request){

        Boolean isExist = userRepository.existsByEmail(request.getEmail());

        if (!isExist){
            User user = userRepository.save(User.builder().email(request.getEmail())
                    .password(bCryptPasswordEncoder.encode(request.getPassword()))
                    .role(request.getRole()).build());

            return new AddUserResponse(200, user);

        } else {
            return new AddUserResponse(500,null);
        }
    }
}
