package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long userId;

    @Column(unique = true) //email needs to be unique
    @Email(message = "Please provide a valid email address") //email checker using hibernate validator
    private String email;

    @Column(unique = true) //username needs to be unique
    private String username;
    private String name;
    private String bio;
    private String profilePicture;
    private String password;

    //cascade all means that when a user is deleted, all associated posts will be deleted too
    //orphan removal ensures post is removed from the database
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> following = new ArrayList<>();
 //cascade all means that when a user is deleted, all associated posts will be deleted too
    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers = new ArrayList<>();

    //keep tracks of users who liked the post
    @ManyToMany(mappedBy = "likedUsers")
    private Set<Post> likedPosts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

}
