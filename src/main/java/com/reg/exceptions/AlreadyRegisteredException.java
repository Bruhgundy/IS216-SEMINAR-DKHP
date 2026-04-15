package com.reg.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyRegisteredException extends RuntimeException {
    public AlreadyRegisteredException(Long studentId, Long courseId) {
        super("Student " + studentId + " is already registered for course " + courseId);
    }
}
