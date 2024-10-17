package me.yonghwan.springbootdeveloper.repository;

import me.yonghwan.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-17
 * @PARAM
 * @VERSION 1.0
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
