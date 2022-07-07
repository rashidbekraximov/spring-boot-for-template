package com.example.demo.mappers.auth;

import com.example.demo.dto.auth.AuthUserDto;
import com.example.demo.dto.auth.RegisterUserDto;
import com.example.demo.entity.auth.AuthUser;
import com.example.demo.mappers.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;


@org.mapstruct.Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface AuthMapper extends Mapper {
    AuthUserDto toDto(AuthUser user);

    AuthUser fromCreateDto(RegisterUserDto registerUserDto);

    List<RegisterUserDto> toDto(List<AuthUser> authUsers);
}