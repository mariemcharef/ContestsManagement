package com.cp.Contests_management.Participant;


import com.cp.Contests_management.Clarification.Clarification;
import com.cp.Contests_management.ParticipantCompetition.ParticipantCompetition;
import com.cp.Contests_management.Submission.Submission;
import com.cp.Contests_management.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name= "user_participants",
            joinColumns = {
                    @JoinColumn(name="participant_id")
             },
            inverseJoinColumns = {
                    @JoinColumn(name="user_id")
            }
    )
    @Size(
            max = 3,
            min = 1,
            message = "A team can have at most 3 members and at least 1 member"
    )
    private List<User> users;

    @OneToMany(mappedBy = "participant")
    private List<ParticipantCompetition> participantCompetitions;

    //user can make 0 to many submissions
    @OneToMany(mappedBy = "participant")
    @JsonBackReference
    private List<Submission> submissions;

    @OneToMany(mappedBy = "participant")
    @JsonBackReference
    private List<Clarification> clarifications;

    public int getUserCount() {
        return users.size();
    }

}
