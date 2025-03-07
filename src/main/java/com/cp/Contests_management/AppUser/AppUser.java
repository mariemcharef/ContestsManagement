package com.cp.Contests_management.AppUser;

import com.cp.Contests_management.Announcement.Announcement;
import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Submission.Submission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppUser {//User is a key word (for the database)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    @ManyToMany(mappedBy = "AppUsers")
    private List<Participant> participants;//list of groups that one user is joining

    @OneToMany(mappedBy = "appUser")
    private List<Competition> competitions;

}
