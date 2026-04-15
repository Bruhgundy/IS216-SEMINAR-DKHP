package com.university.registration.repository;

import com.university.registration.model.Course;
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

    @Override
    Optional<Course> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Course c WHERE c.id = :id")
    Optional<Course> findByIdForUpdate(@Param("id") Long id);

    @Modifying
    @Query("""
        UPDATE Course c
        SET c.availableSeats = c.availableSeats - 1,
            c.version = c.version + 1
        WHERE c.id = :id AND c.availableSeats > 0
        """)
    int decrementSeatAtomic(@Param("id") Long id);

    @Query("SELECT c FROM Course c WHERE c.availableSeats > 0 ORDER BY c.availableSeats ASC")
    List<Course> findAllWithSeatsOrderedByScarcity();

    @Query("""
        SELECT c FROM Course c
        WHERE c.id <> :excludeId
          AND c.dayOfWeek = :dayOfWeek
          AND c.startPeriod < :endPeriod
          AND (:startPeriod) < (c.startPeriod + c.periodCount)
        """)
    List<Course> findConflictingCourses(
        @Param("excludeId")   Long    excludeId,
        @Param("dayOfWeek")   Integer dayOfWeek,
        @Param("startPeriod") Integer startPeriod,
        @Param("endPeriod")   int     endPeriod
    );
}
