package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.Competition.Competition;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String content;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="competition_id",nullable = false)
    private Competition competition;


}