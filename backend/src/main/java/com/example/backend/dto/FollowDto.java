package com.example.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FollowDto {
    private Long id;
    private UserDto follower;
    private UserDto followed;
    private Date created;
}

