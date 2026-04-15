package com.university.registration.service;

import com.university.registration.dto.RegistrationRequest;
import com.university.registration.dto.RegistrationResponse;
import com.university.registration.exception.*;
import com.university.registration.model.*;
import com.university.registration.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final CourseRepository       courseRepository;
    private final StudentRepository      studentRepository;
    private final RegistrationRepository registrationRepository;
    private final CourseService          courseService;

    @Transactional
    public RegistrationResponse register(RegistrationRequest req) {
        Student student = findStudent(req.getStudentId());
        Course  course  = courseService.findOrThrow(req.getCourseId());

        guardDuplicate(student.getId(), course.getId());
        guardScheduleConflict(student, course);

        if (!course.hasSeats())
            throw new NoSeatsAvailableException(course.getId(), course.getName());

        course.claimSeat();
        courseRepository.save(course);

        return save(student, course);
    }

    @Transactional
    public RegistrationResponse registerPessimistic(RegistrationRequest req) {
        Student student = findStudent(req.getStudentId());
        Course  course  = courseService.findByIdForUpdate(req.getCourseId());

        guardDuplicate(student.getId(), course.getId());
        guardScheduleConflict(student, course);

        if (!course.hasSeats())
            throw new NoSeatsAvailableException(course.getId(), course.getName());

        course.claimSeat();
        courseRepository.save(course);

        return save(student, course);
    }

    @Transactional
    public RegistrationResponse registerAtomic(RegistrationRequest req) {
        Student student = findStudent(req.getStudentId());
        Course course = courseService.findOrThrow(req.getCourseId());

        guardDuplicate(student.getId(), course.getId());
        guardScheduleConflict(student, course);

        int updated = courseRepository.decrementSeatAtomic(course.getId());
        if (updated == 0)
            throw new NoSeatsAvailableException(course.getId(), course.getName());

        return save(student, course);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public RegistrationResponse registerSerializable(RegistrationRequest req) {
        Student student = findStudent(req.getStudentId());
        Course  course  = courseService.findOrThrow(req.getCourseId());

        guardDuplicate(student.getId(), course.getId());
        guardScheduleConflict(student, course);

        if (!course.hasSeats())
            throw new NoSeatsAvailableException(course.getId(), course.getName());

        course.claimSeat();
        courseRepository.save(course);

        return save(student, course);
    }

    @Transactional(readOnly = true)
    public List<RegistrationResponse> getRegistrationsForStudent(Long studentId) {
        findStudent(studentId);
        return registrationRepository.findByStudentIdWithCourse(studentId)
                .stream().map(RegistrationResponse::from).toList();
    }

    @Transactional
    public void cancel(Long registrationId) {
        Registration reg = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration", registrationId));

        if (reg.getStatus() == Registration.RegistrationStatus.CANCELLED)
            throw new IllegalStateException("Registration is already cancelled.");

        reg.setStatus(Registration.RegistrationStatus.CANCELLED);
        reg.getCourse().releaseSeat();
        courseRepository.save(reg.getCourse());
        registrationRepository.save(reg);
    }

    private Student findStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id));
    }

    private void guardDuplicate(Long studentId, Long courseId) {
        if (registrationRepository.existsByStudentIdAndCourseId(studentId, courseId))
            throw new AlreadyRegisteredException(studentId, courseId);
    }

    private void guardScheduleConflict(Student student, Course newCourse) {
        List<Registration> existing = registrationRepository
                .findByStudentIdWithCourse(student.getId());

        for (Registration reg : existing) {
            if (reg.getStatus() == Registration.RegistrationStatus.CANCELLED) continue;
            Course enrolled = reg.getCourse();
            if (!enrolled.getDayOfWeek().equals(newCourse.getDayOfWeek())) continue;

            int s1 = enrolled.getStartPeriod(),  e1 = s1 + enrolled.getPeriodCount();
            int s2 = newCourse.getStartPeriod(), e2 = s2 + newCourse.getPeriodCount();
            if (s1 < e2 && s2 < e1)
                throw new ScheduleConflictException(enrolled.getName());
        }
    }

    private RegistrationResponse save(Student student, Course course) {
        Registration reg = Registration.builder()
                .student(student)
                .course(course)
                .status(Registration.RegistrationStatus.CONFIRMED)
                .build();
        return RegistrationResponse.from(registrationRepository.save(reg));
    }
}
