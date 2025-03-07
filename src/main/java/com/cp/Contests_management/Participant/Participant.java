package com.cp.Contests_management.Participant;


import com.cp.Contests_management.AppUser.AppUser;
import com.cp.Contests_management.Clarification.Clarification;
import com.cp.Contests_management.ParticipantCompetition.ParticipantCompetition;
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
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer userCount=1;

    @PrePersist
    @PreUpdate
    private void validateuserCount() {
        if (userCount == null || (userCount != 1 && userCount != 2 && userCount != 3)) {
            throw new IllegalArgumentException("att must be 1, 2, or 3");
        }
    }
    @ManyToMany
    @JoinTable(
            name= "user_participant",
            joinColumns = {
                    @JoinColumn(name="participant_id")
             },
            inverseJoinColumns = {
                    @JoinColumn(name="user_id")
            }
    )
    private List<AppUser> AppUsers;
    //user can make 0 to many submissions
    @OneToMany(mappedBy = "participant")
    private List<Submission> submissions;


    @OneToMany(mappedBy = "participant")
    private List<Clarification> clarifications;

    @OneToMany(mappedBy = "participant")
    private List<ParticipantCompetition> participantCompetitions;

}
