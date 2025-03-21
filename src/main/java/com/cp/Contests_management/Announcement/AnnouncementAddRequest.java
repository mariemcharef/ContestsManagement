package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.Competition.Competition;
import lombok.Data;

@Data

public class AnnouncementAddRequest {

    private String content;
    private Competition competition;
}
