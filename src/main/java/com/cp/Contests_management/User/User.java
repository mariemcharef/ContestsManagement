package com.cp.Contests_management.User;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name ="app_user")
public class User {//User is a key word (for the database)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(
            nullable = false,
            unique = true
    )
    private String name;
    @Column(
            nullable = false,
            unique = true
    )
    private String email;
    @Column(
            nullable = false
    )
    private String password;

    private int rating;

    @ManyToMany(mappedBy = "users")
    private List<Participant> participants;//list of groups that one user is joining

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Competition> competitions;

}
