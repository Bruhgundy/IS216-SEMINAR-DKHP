package com.reg.repos;

import com.reg.models.Course;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByDayOfWeek(Integer dayOfWeek);

    List<Course> findByAvailableSeatsGreaterThan(int minSeats);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Course c WHERE c.id = :id")
    Optional<Course> findByIdForUpdate(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Course c SET c.availableSeats = c.availableSeats - 1, c.version = c.version + 1 WHERE c.id = :id AND c.availableSeats > 0")
    int decrementSeatAtomic(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Course c SET c.availableSeats = c.availableSeats + 1, c.version = c.version + 1 WHERE c.id = :id")
    void incrementSeat(@Param("id") Long id);
}
