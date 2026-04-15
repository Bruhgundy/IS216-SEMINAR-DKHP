package com.reg.controllers;

import com.reg.dtos.ApiResponse;
import com.reg.dtos.RegistrationRequest;
import com.reg.dtos.RegistrationResponse;
import com.reg.services.Solution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solution")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class SolutionController {

    private final Solution solution;

    @PostMapping
    public ApiResponse<RegistrationResponse> register(@RequestBody RegistrationRequest req) {
        try {
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
