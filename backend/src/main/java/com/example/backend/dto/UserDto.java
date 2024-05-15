package com.example.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String bio;
    private String profilePicture;
    private String password;


}

