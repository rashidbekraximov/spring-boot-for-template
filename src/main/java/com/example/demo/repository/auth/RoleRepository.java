package com.example.demo.repository.auth;

import com.example.demo.entity.auth.Role;
import com.example.demo.enums.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(AuthRole roleName);
}
