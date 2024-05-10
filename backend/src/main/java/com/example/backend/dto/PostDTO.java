package com.example.backend.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PostDTO {
    private Long postId;
    private String content;
    private int numLikes;
    private boolean likedByCurrentUser;
    private List<CommentDTO> comments;
}
