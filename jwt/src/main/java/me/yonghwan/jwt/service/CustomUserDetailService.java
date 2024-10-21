package me.yonghwan.jwt.service;

import lombok.RequiredArgsConstructor;
import me.yonghwan.jwt.domain.User;
import me.yonghwan.jwt.dto.CustomUserDetails;
import me.yonghwan.jwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null){
            return new CustomUserDetails(user);
        }
        return null;
    }
}
