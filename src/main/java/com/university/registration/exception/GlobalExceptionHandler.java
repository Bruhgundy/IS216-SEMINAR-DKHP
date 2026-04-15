package com.university.registration.exception;

import com.university.registration.dto.ApiResponse;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.*;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── Domain exceptions ─────────────────────────────────────────────────────

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNotFound(ResourceNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    @ExceptionHandler({NoSeatsAvailableException.class,
                        AlreadyRegisteredException.class,
                        ScheduleConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleConflict(RuntimeException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // ── Optimistic-lock collisions (THE key exception for Thundering Herd) ────

    @ExceptionHandler({
        OptimisticLockException.class,
        OptimisticLockingFailureException.class,
        ObjectOptimisticLockingFailureException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleOptimisticLock(Exception ex) {
        log.warn("OPTIMISTIC_LOCK collision detected: {}", ex.getMessage());
        return ApiResponse.error(
            "OPTIMISTIC_LOCK: Concurrent modification detected. " +
            "Please retry. (" + ex.getMessage() + ")");
    }

    // ── DB constraint violations ──────────────────────────────────────────────

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation: {}", ex.getMessage());
        String msg = ex.getMessage() != null && ex.getMessage().contains("UQ_STUDENT_COURSE")
            ? "Student is already registered for this course."
            : "Database constraint violation.";
        return ApiResponse.error(msg);
    }

    // ── Validation errors ─────────────────────────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
            .collect(Collectors.joining("; "));
        return ApiResponse.error("Validation failed: " + errors);
    }

    // ── Catch-all ─────────────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleGeneric(Exception ex) {
        log.error("Unhandled exception", ex);
        return ApiResponse.error("Internal server error: " + ex.getMessage());
    }
}
