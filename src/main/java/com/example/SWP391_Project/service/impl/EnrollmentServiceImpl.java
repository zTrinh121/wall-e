package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.model.Course;
import com.example.SWP391_Project.model.Enrollment;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.repository.CourseRepository;
import com.example.SWP391_Project.repository.EnrollmentRepository;
import com.example.SWP391_Project.repository.StudentRepository;
import com.example.SWP391_Project.repository.UserRepository;
import com.example.SWP391_Project.service.EnrollmentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    @Transactional
    public Enrollment enrollStudentInCourse(EnrollmentDto enrollmentDto, int parentId, HttpSession session) {
        User user = userRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + parentId + " does not exist."));

        String role = user.getRole().getDescription().name();


        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) {
                throw new IllegalArgumentException("No studentId found in session.");
            }        
        
        Integer courseId = (Integer) session.getAttribute("courseId");
        if (courseId == null) {
            throw new IllegalArgumentException("No courseId found in session.");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " does not exist."));
        Optional<User> optionalStudent = studentRepository.findById(studentId);
        User student = optionalStudent.orElseThrow(() -> new IllegalArgumentException("Student with ID " + studentId + " does not exist."));


        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrollDate(new Date())
                .build();
        System.out.println("Enrolement trong serviceimpl" + enrollment);
        return enrollmentRepository.save(enrollment);
    }
}
