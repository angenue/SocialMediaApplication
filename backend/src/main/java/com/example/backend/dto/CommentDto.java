package com.example.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    private Long commentId;
    private String content;
    private int numLikes;
    private String created;
    private int numReplies;
    private String profilePic; // profile picture of the user who posted the comment
    private Long postId; // ID of the post that the comment belongs to
    private Long userId; // ID of the user who posted the comment
    private String username; // username of the user who posted the comment
}