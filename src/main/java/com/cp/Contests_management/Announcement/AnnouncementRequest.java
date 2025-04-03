package com.cp.Contests_management.Announcement;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data

public class AnnouncementRequest {

    @NotEmpty(message = "content cannot be empty")
    private String content;

}
