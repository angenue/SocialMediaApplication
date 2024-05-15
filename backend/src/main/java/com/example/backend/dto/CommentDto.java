package com.example.backend.dto;

import lombok.*;

import java.util.Date;

@Data
public class CommentDto {
    private Long commentId;
    private String content;
    private int numLikes;
    private boolean likedByCurrentUser;
    private Date created;
    private UserDto user; // Include user information in the comment DTO
}
