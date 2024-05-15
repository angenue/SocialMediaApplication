package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne //many posts can belong to one user
    @JoinColumn(name = "userId")
    @JsonIgnore //avoids infinite recursion in bidirectional relationship
    private User user;

    private int numLikes;
    private boolean likedByCurrentUser; //allows user to like and unlike
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    //lazy fetch means the comments will only be fetched when explicitly accessed
    //cascade type all means all operations performed in post will also be performed in comments
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "commentId")
    private List<Comment> comments; //posts have a list of comments
}
