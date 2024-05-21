package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@NoArgsConstructor
public class UserDto {
    private Long userId;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    private String name;
    private String bio;
    private String profilePicture;

    @JsonIgnore
    @NotBlank(message = "Password is required")
    private String password;

    //for password change
    @JsonIgnore
    @NotBlank(message = "Current password is required")
    private String currentPassword;

    @JsonIgnore
    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "New password should have at least 8 characters")
    private String newPassword;

    @JsonIgnore
    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "New password should have at least 8 characters")
    private String newPasswordConfirmation;
}