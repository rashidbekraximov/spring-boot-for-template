package com.example.demo.repository.auth;

import com.example.demo.entity.auth.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findAuthUserByPhoneNumber(String name);

    boolean existsByPhoneNumber(String phoneNumber);
}
