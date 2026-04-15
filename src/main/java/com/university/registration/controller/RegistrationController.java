package com.university.registration.controller;

import com.university.registration.dto.*;
import com.university.registration.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegistrationResponse> register(
            @Valid @RequestBody RegistrationRequest req) {
        return ApiResponse.ok("Registered (optimistic).",
                registrationService.register(req));
    }

    @PostMapping("/pessimistic")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegistrationResponse> registerPessimistic(
            @Valid @RequestBody RegistrationRequest req) {
        return ApiResponse.ok("Registered (pessimistic).",
                registrationService.registerPessimistic(req));
    }

    @PostMapping("/atomic")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegistrationResponse> registerAtomic(
            @Valid @RequestBody RegistrationRequest req) {
        return ApiResponse.ok("Registered (atomic).",
                registrationService.registerAtomic(req));
    }

    @PostMapping("/serializable")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegistrationResponse> registerSerializable(
            @Valid @RequestBody RegistrationRequest req) {
        return ApiResponse.ok("Registered (serializable).",
                registrationService.registerSerializable(req));
    }

    @GetMapping("/student/{studentId}")
    public ApiResponse<List<RegistrationResponse>> getForStudent(
            @PathVariable Long studentId) {
        return ApiResponse.ok(registrationService.getRegistrationsForStudent(studentId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id) {
        registrationService.cancel(id);
    }
}
