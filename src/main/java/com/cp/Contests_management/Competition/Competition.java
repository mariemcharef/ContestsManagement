package com.cp.Contests_management.Competition;

import com.cp.Contests_management.Announcement.Announcement;
import com.cp.Contests_management.AppUser.AppUser;
import com.cp.Contests_management.ParticipantCompetition.ParticipantCompetition;
import com.cp.Contests_management.Problem.Problem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            nullable = false ,
            unique = true
    )
    private String name;
    @Column(nullable = false)
    private float duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private float penalty=20;

    @OneToMany(mappedBy = "competition")
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "competition")
    private List<Problem> problems;

    @ManyToOne
    @JoinColumn(name="AppUser_id",nullable = false)
    private AppUser appUser;

    @OneToMany(mappedBy = "competition")
    private List<ParticipantCompetition> participantCompetitions;
}
