package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.service.ParentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;


    @Override
    public List<Result> getStudentResults(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null && user.getParent() != null) {
            int parentId = user.getParent().getId();
            List<Result> results = resultRepository.findAllResultsWithParentUserId(parentId);
            return results != null ? results : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Slot> getStudentSlots(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null && user.getParent() != null) {
            int parentId = user.getParent().getId();
            List<Slot> slots = slotRepository.findAllSlotsWithParentUserId(parentId);
            return slots != null ? slots : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Course> getStudentCourses(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null && user.getParent() != null) {
            int parentId = user.getParent().getId();
            List<Course> courses = courseRepository.findAllCoursesWithParentUserId(parentId);
            return courses != null ? courses : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Attendance> getStudentAttendances(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null && user.getParent() != null) {
            int parentId = user.getParent().getId();
            List<Attendance> attendances = attendanceRepository.findAllAttendanceByParentId(parentId);
            return attendances != null ? attendances : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public void enrollStudentInCourse(EnrollmentDto enrollmentDto, HttpSession session) {
        User user = (User) session.getAttribute("user");

        int parentId = user.getParent().getId();
        int studentId = enrollmentDto.getStudentId();
        User student = userRepository.findById(studentId).orElse(null);

        if (student != null && student.getParent() != null && student.getParent().getId() == parentId) {
            int courseId = enrollmentDto.getCourseId();
            Course course = courseRepository.findById(courseId).orElse(null);

            if (course != null) {
                Enrollment enrollment = Enrollment.builder()
                        .student(student)
                        .course(course)
                        .build();
                enrollmentRepository.save(enrollment);
            } else {
                throw new IllegalArgumentException("Course with ID " + courseId + " does not exist.");
            }
        } else {
            throw new IllegalArgumentException("Student with ID " + studentId + " is not a child of the parent.");
        }
    }
}
