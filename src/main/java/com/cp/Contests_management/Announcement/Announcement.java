package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.Competition.Competition;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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
    @Column(length = 10000,nullable = false)
    private String content;

    @Column(nullable = false,updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate=LocalDateTime.now();
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="competition_id",nullable = false)
    private Competition competition;


}