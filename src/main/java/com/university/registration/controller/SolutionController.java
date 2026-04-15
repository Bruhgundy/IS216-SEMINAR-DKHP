package com.university.registration.controller;

import com.university.registration.dto.ApiResponse;
import com.university.registration.dto.RegistrationRequest;
import com.university.registration.dto.RegistrationResponse;
import com.university.registration.service.Solution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solution")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class SolutionController {

    private final ApplicationContext applicationContext;

    @PostMapping
    public ApiResponse<RegistrationResponse> register(@RequestBody RegistrationRequest req) {
        try {
            Solution solution = applicationContext.getBean(Solution.class);
            boolean success = solution.register(req.getStudentId(), req.getCourseId());
            
            if (success) {
                return ApiResponse.ok("Registered via solution: " + solution.getName(), null);
            } else {
                return ApiResponse.error("Registration failed via solution: " + solution.getName());
            }
        } catch (Exception e) {
            log.error("Solution error: {}", e.getMessage());
            return ApiResponse.error("Solution error: " + e.getMessage());
        }
    }
}
