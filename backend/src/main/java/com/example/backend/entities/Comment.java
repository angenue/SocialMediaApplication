package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String content;

    private int numLikes = 0;
    private int numReplies = 0;
    @ManyToMany
    private Set<User> likedUsers = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // User who posted the comment
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Replies to this comment
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();
}