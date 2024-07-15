package com.joel.blog.dto;

import com.joel.blog.model.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long userId;

    @NotEmpty
    @NotBlank
    @Size(min = 3, message = "Username must be of minimum 3 characters !")
    private String userName;

    @Email(message = "Email address is not valid !")
    private String userEmail;

    @NotEmpty
    @NotBlank
    @Size(min = 8, max = 32, message = "Password length must be between 8 to 32 characters !")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Minimum eight characters, at least one letter and one number")
    private String userPassword;

    @NotEmpty
    @NotBlank
    private String userAbout;

    private Set<RoleDto> roles;
}
