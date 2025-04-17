package com.cp.Contests_management.Competition;

import com.cp.Contests_management.Announcement.Announcement;
import com.cp.Contests_management.ParticipantCompetition.ParticipantCompetition;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Integer id;

    @Column(
            nullable = false ,
            unique = true
    )
    private String name;

    @Column(nullable = false)
    private float duration;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    private float penalty=20;

    @ManyToOne
    @JoinColumn(name="creator_id")
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "competition")
    @JsonBackReference
    private List<ParticipantCompetition> participantsCompetitions;

    @OneToMany(mappedBy = "competition")
    @JsonBackReference
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "competition")
    @JsonBackReference
    private List<Problem> problems;

    @PrePersist
    @PreUpdate
    public void setEndTime() {
        if (startTime != null && duration > 0) {
            this.endTime = startTime.plusMinutes((long) duration);
        }
    }


}
