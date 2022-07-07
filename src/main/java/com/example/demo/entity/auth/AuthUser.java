package com.example.demo.entity.auth;

import com.example.demo.entity.base.Auditable;
import com.example.demo.enums.AuthRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Table(name = "auth_user", indexes = {
        @Index(name = "auth_user_username_key",
                columnList = "username", unique = true
        )
}, schema = "auth")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser extends Auditable implements UserDetails {

    @Column(name = "username", nullable = false, length = 50)
    private String phoneNumber;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "date format:yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Enumerated(value = EnumType.STRING)
    private AuthRole roles;

    @Column(name = "password")
    private String password;

    @Transient
    private String prePassword;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled;

    @Builder(builderMethodName = "childBuilder")
    public AuthUser( String phoneNumber, String firstName, String lastName, Date date, String email, AuthRole roles, String password, String prePassword, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.prePassword = prePassword;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.roles == null) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(AuthRole.ADMIN.name());
            return Collections.singleton(simpleGrantedAuthority);
        }
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(this.roles.name());
        return Collections.singleton(simpleGrantedAuthority);
    }

    @Override
    public String getUsername() {
        return this.getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}