package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private CourseRepository courseRepository;

//    @Autowired
//    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;


    @Override
    public List<Result> getStudentResults(int parentId) {
        Optional<List<Result>> results = resultRepository.findAllResultsWithParentUserId(parentId);
        return results.orElse(Collections.emptyList());
    }

    @Override
    public List<Slot> getStudentSlots(int parentId) {
        Optional<List<Slot>> slots = slotRepository.findAllSlotsWithParentUserId(parentId);
        return slots.orElse(Collections.emptyList());
    }

    @Override
    public List<Course> getStudentCourses(int parentId) {
        Optional<List<Course>> courses = courseRepository.findAllCoursesWithParentUserId(parentId);
        return courses.orElse(Collections.emptyList());
    }

//    @Override
//    public List<Attendance> getStudentAttendances(int parentId) {
//        Optional<List<Attendance>> attendances = attendanceRepository.findAllAttendanceByParentId(parentId);
//        return attendances.orElse(Collections.emptyList());
//    }

    @Override
    public void enrollStudentInCourse(EnrollmentDto enrollmentDto, int parentId) {
        User parent = userRepository.findById(parentId).orElse(null);

        if (parent == null) {
            throw new IllegalArgumentException("Parent with ID " + parentId + " does not exist.");
        }

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
            throw new IllegalArgumentException("Student with ID " + studentId + " is not a child of the parent with ID " + parentId + ".");
        }
    }

    @Override
    public List<User> getStudentsByParentId(int parentId) {
        return userRepository.getStudentsByParentId(parentId);
    }
}
