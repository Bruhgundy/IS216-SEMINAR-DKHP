package com.reg;

import com.reg.services.Solution;
import org.springframework.stereotype.Component;

@Component
public class TestSolution implements Solution {

    @Override
    public String getName() {
        return "Test Solution";
    }

    @Override
    public boolean register(Long studentId, Long courseId) {
        return false;
    }
}
