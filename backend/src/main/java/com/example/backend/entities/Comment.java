package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String content;

    private int numLikes;
    @ManyToMany
    private Set<User> likedUsers;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private int numReplies;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // User who posted the comment
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Comment that this comment is a reply to
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    // User that this comment is a reply to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replied_to_user_id")
    private User repliedTo;

}
