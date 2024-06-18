package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.model.Course;
import com.example.SWP391_Project.model.Enrollment;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.repository.CourseRepository;
import com.example.SWP391_Project.repository.EnrollmentRepository;
import com.example.SWP391_Project.repository.UserRepository;
import com.example.SWP391_Project.service.EnrollmentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    @Transactional
    public Enrollment enrollStudentInCourse(EnrollmentDto enrollmentDto, int parentId, HttpSession session) {
        // Fetch the parent user from the userRepository

        User user = userRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + parentId + " does not exist."));
        String role =user.getRole().getDescription().name();

        if(role.equals("PARENT")){
            User student = userRepository.findStudentsByParentId(parentId);

            if (student != null) {
                Integer courseId = (Integer) session.getAttribute("courseId");
                if (courseId == null) {
                    throw new IllegalArgumentException("No courseId found in session.");
                }

                Course course = courseRepository.findById(courseId)
                        .orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " does not exist."));

                Enrollment enrollment = Enrollment.builder()
                        .student(student)
                        .course(course)
                        .build();
                System.out.println("Enrolement trong serviceimpl" + enrollment);
                return enrollmentRepository.save(enrollment);
            } else {
                throw new IllegalArgumentException("No student found with parent ID " + parentId);
            }
        } else if(role.equals("STUDENT")){
            Integer courseId = (Integer) session.getAttribute("courseId");
            if (courseId == null) {
                throw new IllegalArgumentException("No courseId found in session.");
            }

            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " does not exist."));

            Enrollment enrollment = Enrollment.builder()
                    .student(user)
                    .course(course)
                    .build();
            System.out.println("Enrolement trong serviceimpl" + enrollment);
            return enrollmentRepository.save(enrollment); // Save and return the enrollment
        }
        else throw new IllegalArgumentException("No role found");
    }

}
