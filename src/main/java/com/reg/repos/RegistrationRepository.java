package com.reg.repos;

import com.reg.models.Registration;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    @Modifying
    @Query("UPDATE Registration r SET r.status = :status WHERE r.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") Registration.RegistrationStatus status);

    @Query("""
        SELECT r FROM Registration r
        JOIN FETCH r.course
        WHERE r.student.id = :studentId
        ORDER BY r.registeredAt DESC
        """)
    List<Registration> findByStudentIdWithCourse(@Param("studentId") Long studentId);

    @Query("""
        SELECT r FROM Registration r
        JOIN FETCH r.course c
        WHERE r.student.id = :studentId
          AND r.status = 'CONFIRMED'
          AND c.dayOfWeek = :dayOfWeek
        """)
    List<Registration> findActiveByStudentAndDay(@Param("studentId") Long studentId, @Param("dayOfWeek") Integer dayOfWeek);
}
