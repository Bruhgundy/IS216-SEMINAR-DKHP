package com.reg.services;

// implement this to create your own registration strategy
public interface Solution {
    String getName();
    boolean register(Long studentId, Long courseId);
}
