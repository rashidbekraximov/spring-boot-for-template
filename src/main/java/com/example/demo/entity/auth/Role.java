package com.example.demo.entity.auth;

import com.example.demo.entity.base.Auditable;
import com.example.demo.enums.AuthRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends Auditable implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    private AuthRole roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
