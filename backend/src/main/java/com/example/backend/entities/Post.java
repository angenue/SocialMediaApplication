package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne //many posts can belong to one user
    @JoinColumn(name = "userId")
    @JsonIgnore //avoids infinite recursion in bidirectional relationship
    private User user;

    private int numLikes;
    @ManyToMany
    private Set<User> likedUsers;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    //lazy fetch means the comments will only be fetched when explicitly accessed
    //cascade type all means all operations performed in post will also be performed in comments
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "commentId")
    private List<Comment> comments; //posts have a list of comments

    private int numComments;

    @Override
    public int hashCode() {
        return Objects.hash(postId, content, created, numLikes);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", numLikes=" + numLikes +
                '}';
    }
}
