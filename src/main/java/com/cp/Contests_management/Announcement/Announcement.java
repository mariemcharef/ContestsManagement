package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.AppUser.AppUser;
import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name="competition_id",nullable = false)
    private Competition competition;

    public Announcement(String content,Competition competition) {
        this.content = content;
        this.competition = competition;
    }
}