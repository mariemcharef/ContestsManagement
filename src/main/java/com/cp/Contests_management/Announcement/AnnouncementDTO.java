package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.Competition.CompetitionDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementDTO {
    private Integer id;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;

}
