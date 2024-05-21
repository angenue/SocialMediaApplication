package com.example.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String username;
    private String name;
    private String bio;
    private String profilePicture;

    //for password change
    private String currentPassword;
    private String newPassword;


}

