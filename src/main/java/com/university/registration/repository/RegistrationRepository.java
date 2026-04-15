package com.university.registration.repository;

import com.university.registration.model.Registration;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    Optional<Registration> findByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("""
        SELECT r FROM Registration r
        JOIN FETCH r.course
        WHERE r.student.id = :studentId
        ORDER BY r.registeredAt DESC
        """)
    List<Registration> findByStudentIdWithCourse(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(r) FROM Registration r WHERE r.course.id = :courseId AND r.status = 'CONFIRMED'")
    long countConfirmedByCourse(@Param("courseId") Long courseId);
}
