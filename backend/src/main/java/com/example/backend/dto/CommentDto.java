package com.example.backend.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long commentId;
    private String content;
    private int numLikes;
    private String created;
    private int numReplies;
    private Long postId; // ID of the post that the comment belongs to
    private Long userId; // ID of the user who posted the comment
    private String username; // username of the user who posted the comment
    private String parentCommentId; // ID of the comment that this comment is a reply to
    private String repliedToUsername; // username of the user that this comment is a reply to
}