package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class Follow {
    //generated value is used to generate the primary key (needs ID)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //join column is the foreign key in the follow table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower;
    //fetch = FetchType.LAZY means that the data will be loaded when it is needed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_id")
    private User followed;
    //timestamp of when the follow was created
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;


}
