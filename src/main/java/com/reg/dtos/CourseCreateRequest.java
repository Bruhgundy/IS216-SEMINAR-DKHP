package com.reg.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CourseCreateRequest {
    @NotBlank @Size(max = 255)
    public String  name;
    @NotNull @Min(1)
    public Integer availableSeats;
    @NotNull @Min(1) @Max(7)
    public Integer dayOfWeek;
    @NotNull @Min(1)
    public Integer startPeriod;
    @NotNull @Min(1)
    public Integer periodCount;
}
