package com.reg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Min(0)
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @NotNull
    @Min(1) @Max(7)
    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @NotNull
    @Min(1)
    @Column(name = "start_period", nullable = false)
    private Integer startPeriod;

    @NotNull
    @Min(1)
    @Column(name = "period_count", nullable = false)
    private Integer periodCount;

    @Version
    @Column(name = "version_", nullable = false)
    private Long version;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public boolean hasSeats() {
        return availableSeats != null && availableSeats > 0;
    }

    public void claimSeat() {
        if (!hasSeats()) throw new IllegalStateException(
            "Course '" + name + "' (id=" + id + ") has no available seats.");
        availableSeats--;
    }

    public void releaseSeat() {
        availableSeats++;
    }

    public int lastPeriod() {
        return startPeriod + periodCount - 1;
    }
}
