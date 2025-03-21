package com.cp.Contests_management.Announcement;

public class AnnouncementNotFoundException extends RuntimeException {
    public AnnouncementNotFoundException(String announcementNotFound) {
        super(announcementNotFound);
    }
}
