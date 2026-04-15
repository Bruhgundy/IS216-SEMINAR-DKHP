package com.university.registration.service;

import com.university.registration.dto.CourseCreateRequest;
import com.university.registration.dto.CourseResponse;
import com.university.registration.exception.ResourceNotFoundException;
import com.university.registration.model.Course;
import com.university.registration.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream().map(CourseResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {
        return CourseResponse.from(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByDay(Integer dayOfWeek) {
        return courseRepository.findByDayOfWeek(dayOfWeek)
                .stream().map(CourseResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getAvailableCourses() {
        return courseRepository.findByAvailableSeatsGreaterThan(0)
                .stream().map(CourseResponse::from).toList();
    }

    @Transactional
    public CourseResponse createCourse(CourseCreateRequest req) {
        Course course = Course.builder()
                .name(req.getName())
                .availableSeats(req.getAvailableSeats())
                .dayOfWeek(req.getDayOfWeek())
                .startPeriod(req.getStartPeriod())
                .periodCount(req.getPeriodCount())
                .build();
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public CourseResponse updateCourse(Long id, CourseCreateRequest req) {
        Course course = findOrThrow(id);
        course.setName(req.getName());
        course.setAvailableSeats(req.getAvailableSeats());
        course.setDayOfWeek(req.getDayOfWeek());
        course.setStartPeriod(req.getStartPeriod());
        course.setPeriodCount(req.getPeriodCount());
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) throw new ResourceNotFoundException("Course", id);
        courseRepository.deleteById(id);
    }

    public Course findOrThrow(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));
    }

    public Course findByIdForUpdate(Long id) {
        return courseRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));
    }
}
