package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne //many posts can belong to one user
    @JoinColumn(name = "userId")
    private User user;

    private int numLikes;
    private boolean likedByCurrentUser; //allows user to like and unlike
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @OneToMany
    @JoinColumn(name = "comment_id")
    private List<Comment> comments; //posts have a list of comments
}
