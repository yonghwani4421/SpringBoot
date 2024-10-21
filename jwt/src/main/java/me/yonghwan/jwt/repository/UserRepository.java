package me.yonghwan.jwt.repository;

import me.yonghwan.jwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    Boolean existsByEmail(String email);
}
