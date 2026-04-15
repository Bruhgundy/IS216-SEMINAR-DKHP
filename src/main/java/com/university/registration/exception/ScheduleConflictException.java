package com.university.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ScheduleConflictException extends RuntimeException {
    public ScheduleConflictException(String conflictingCourseName) {
        super("Schedule conflict with course: '" + conflictingCourseName + "'");
    }
}
