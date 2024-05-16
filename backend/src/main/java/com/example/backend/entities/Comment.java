package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String content;

    private int numLikes;
    private boolean likedByCurrentUser; //allows user to like and unlike

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    // User who posted the comment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Comment that this comment is a reply to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    // User that this comment is a reply to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replied_to_user_id")
    private User repliedTo;

}
