package com.reg.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// no seats left
@ResponseStatus(HttpStatus.CONFLICT)
public class NoSeatsAvailableException extends RuntimeException {
    private final Long courseId;

    public NoSeatsAvailableException(Long courseId, String courseName) {
        super("No seats available for course '" + courseName + "' (id=" + courseId + ")");
        this.courseId = courseId;
    }

    public Long getCourseId() { return courseId; }
}
