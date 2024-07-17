package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.model.Enrollment;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface EnrollmentService {
    Enrollment enrollStudentInCourse(EnrollmentDto enrollmentDto, int parentId, int studentId, HttpSession session);
}
