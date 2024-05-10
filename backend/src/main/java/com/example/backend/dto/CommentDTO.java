package com.example.backend.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CommentDTO {
    private Long commentId;
    private String content;
    private int numLikes;
    private boolean likedByCurrentUser;
    private Date dateCreated;
}
