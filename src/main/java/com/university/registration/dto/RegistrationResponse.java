package com.university.registration.dto;

import com.university.registration.model.Registration;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RegistrationResponse {
    public Long   registrationId;
    public Long   studentId;
    public String studentName;
    public Long   courseId;
    public String courseName;
    public String status;
    public LocalDateTime registeredAt;

    public static RegistrationResponse from(Registration reg) {
        var r = new RegistrationResponse();
        r.registrationId = reg.getId();
        r.studentId      = reg.getStudent().getId();
        r.studentName    = reg.getStudent().getFullName();
        r.courseId       = reg.getCourse().getId();
        r.courseName     = reg.getCourse().getName();
        r.status         = reg.getStatus().name();
        r.registeredAt   = reg.getRegisteredAt();
        return r;
    }
}
