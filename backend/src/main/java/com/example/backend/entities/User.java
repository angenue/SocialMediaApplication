package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
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
}
