package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDTO {
    private Long userId;

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Username is required")
    private String username;

    private String firstName;
    private String lastName;

    @NotNull(message = "Password is required")
    private String password;
    private String bio;
    private String profilePicture;

}

