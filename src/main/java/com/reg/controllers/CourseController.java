package com.reg.controllers;

import com.reg.dtos.*;
import com.reg.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ApiResponse<List<CourseResponse>> listAll() {
        return ApiResponse.ok(courseService.getAllCourses());
    }

    @GetMapping("/available")
    public ApiResponse<List<CourseResponse>> listAvailable() {
        return ApiResponse.ok(courseService.getAvailableCourses());
    }

    @GetMapping("/day/{day}")
    public ApiResponse<List<CourseResponse>> listByDay(@PathVariable Integer day) {
        return ApiResponse.ok(courseService.getCoursesByDay(day));
    }

    @GetMapping("/{id}")
    public ApiResponse<CourseResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(courseService.getCourseById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CourseResponse> create(@Valid @RequestBody CourseCreateRequest req) {
        return ApiResponse.ok("Course created.", courseService.createCourse(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<CourseResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CourseCreateRequest req) {
        return ApiResponse.ok("Course updated.", courseService.updateCourse(id, req));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }
}
