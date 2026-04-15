package com.university.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a student attempts to register for a course that has no remaining seats.
 * <p>
 * This is the exception that gets <em>amplified</em> under the Thundering Herd problem:
 * hundreds of concurrent requests all race to claim the last seat, and all but
 * one will either see this exception (seats=0 at read time) or an
 * {@link jakarta.persistence.OptimisticLockException} (seats=0 after a racing write).
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class NoSeatsAvailableException extends RuntimeException {
    private final Long courseId;

    public NoSeatsAvailableException(Long courseId, String courseName) {
        super("No seats available for course '" + courseName + "' (id=" + courseId + ")");
        this.courseId = courseId;
    }

    public Long getCourseId() { return courseId; }
}
