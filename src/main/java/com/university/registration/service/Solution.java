package com.university.registration.service;

/**
 * Solution interface for handling course registration under high concurrency.
 * Implement this interface to create your own strategy for the Thundering Herd Problem.
 * 
 * Your implementation will be called automatically when users select your solution.
 */
public interface Solution {
    
    /**
     * @return Your solution name (e.g., "My AI Strategy")
     */
    String getName();
    
    /**
     * Handle course registration.
     * @param studentId The student ID
     * @param courseId The course ID to register for
     * @return true if registration succeeded, false otherwise
     */
    boolean register(Long studentId, Long courseId);
}
