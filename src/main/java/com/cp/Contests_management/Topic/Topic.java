package com.cp.Contests_management.Topic;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            nullable = false,
            unique = true
    )
    private String name;
    @ManyToMany(mappedBy = "topics")
    private List<Problem> problems;
}
