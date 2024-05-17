package com.example.backend.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Data
public class PostDto {
    private Long postId;
    private UserDto user; // Include user information in the post DTO
    private int numLikes;
    private int numComments;
    private String content;
    private Date created;
    private String username;
}
