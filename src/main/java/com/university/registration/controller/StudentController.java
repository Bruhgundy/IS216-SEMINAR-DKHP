package com.university.registration.controller;

import com.university.registration.dto.ApiResponse;
import com.university.registration.exception.ResourceNotFoundException;
import com.university.registration.model.Student;
import com.university.registration.repository.StudentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping
    public ApiResponse<List<Student>> listAll() {
        return ApiResponse.ok(studentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Student> getById(@PathVariable Long id) {
        return ApiResponse.ok(studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Student> create(@Valid @RequestBody Student student) {
        return ApiResponse.ok("Student created.", studentRepository.save(student));
    }
}
