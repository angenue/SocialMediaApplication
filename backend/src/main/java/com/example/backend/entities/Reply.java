package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;
    private String content;

    private int numLikes = 0;
    @ManyToMany
    private Set<User> likedUsers = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    // User who posted the reply
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Comment that this reply is a reply to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;


    // User that this reply is a reply to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replied_to_user_id")
    private User repliedTo;
}