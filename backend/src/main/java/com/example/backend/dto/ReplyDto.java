package com.example.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyDto {
    private Long replyId;
    private String content;
    private int numLikes;
    private String created;
    private String profilePic; // profile picture of the user who posted the reply
    private Long userId; // ID of the user who posted the reply
    private String username; // username of the user who posted the reply
    private Long parentCommentId; // ID of the comment that this reply is a reply to
    private String repliedToUsername; // username of the user that this reply is a reply to
}