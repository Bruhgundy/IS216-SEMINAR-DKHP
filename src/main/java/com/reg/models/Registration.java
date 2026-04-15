package com.reg.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "registrations",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_student_course",
        columnNames = {"student_id", "course_id"}
    )
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private RegistrationStatus status = RegistrationStatus.CONFIRMED;

    @Column(name = "registered_at", updatable = false)
    private java.time.LocalDateTime registeredAt;

    @PrePersist
    protected void onCreate() {
        registeredAt = java.time.LocalDateTime.now();
    }

    public enum RegistrationStatus {
        CONFIRMED, WAITLISTED, CANCELLED
    }
}
