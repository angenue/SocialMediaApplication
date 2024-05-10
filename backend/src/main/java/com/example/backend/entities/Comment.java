package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String content;

    private int numLikes;
    private boolean likedByCurrentUser; //allows user to like and unlike

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

}
