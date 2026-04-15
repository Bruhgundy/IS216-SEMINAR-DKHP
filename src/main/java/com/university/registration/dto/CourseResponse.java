package com.university.registration.dto;

import com.university.registration.model.Course;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CourseResponse {
    public Long    id;
    public String  name;
    public Integer availableSeats;
    public Integer dayOfWeek;
    public Integer startPeriod;
    public Integer periodCount;
    public Long    version;
    public LocalDateTime updatedAt;

    public static CourseResponse from(Course c) {
        var r = new CourseResponse();
        r.id             = c.getId();
        r.name           = c.getName();
        r.availableSeats = c.getAvailableSeats();
        r.dayOfWeek      = c.getDayOfWeek();
        r.startPeriod    = c.getStartPeriod();
        r.periodCount    = c.getPeriodCount();
        r.version        = c.getVersion();
        r.updatedAt      = c.getUpdatedAt();
        return r;
    }
}
