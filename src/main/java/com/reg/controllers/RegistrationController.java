package com.reg.controllers;

import com.reg.dtos.*;
import com.reg.services.RegistrationService;
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
