package com.example.demo.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUserDto {
    private String username;
    private String password;
}
