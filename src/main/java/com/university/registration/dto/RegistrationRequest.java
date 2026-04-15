package com.university.registration.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationRequest {
    @NotNull
    public Long studentId;
    @NotNull
    public Long courseId;
}
