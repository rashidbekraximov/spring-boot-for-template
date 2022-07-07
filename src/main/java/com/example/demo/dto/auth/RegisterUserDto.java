package com.example.demo.dto.auth;

import com.example.demo.dto.GenericDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto extends GenericDto {

    @NotNull(message = "Phone number should not be null!")
    @Size(min = 8, max = 12)
    private String phoneNumber;

    @NotNull(message = "First name should not be null!")
    @Size(min = 3, max = 30)
    private String firstName;

    @NotNull(message = "Last name should not be null!")
    @Size(min = 3, max = 30)
    private String lastName;

    @NotNull(message = "Birthday should not be null!")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "date format:yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull(message = "Email should not be null!")
    private String email;

    private String roles;

    @NotNull(message = "Password should not be null!")
    private String password;

    @NotNull(message = "Pre password should not be null!")
    private String prePassword;

    private boolean enabled;
}
