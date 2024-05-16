package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    private String firstName;
    private String lastName;
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
    private Set<Post> likedPosts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;


    //hashcode and tostring updated to prevent infinite recursion caused by circular references between user and post
    @Override
    public int hashCode() {
        return Objects.hash(userId, email, username, firstName, lastName, bio, profilePicture, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bio='" + bio + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }

}
