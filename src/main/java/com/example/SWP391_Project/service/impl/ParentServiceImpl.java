package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.response.ParentNotificationResponse;
import com.example.SWP391_Project.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private IndividualNotificationRepository individualNotificationRepository;

    @Autowired
    private SystemNotificationRepository systemNotificationRepository;

    @Autowired
    private ViewSystemNotificationRepository viewSystemNotificationRepository;


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

    // ----------------------------- NOTIFICATION -------------------------------
    @Override
    public ParentNotificationResponse getAllNotifications(int parentId) {
        List<IndividualNotification> individualNotifications
                = individualNotificationRepository.findNotificationsByUserId(parentId);
        List<SystemNotification> systemNotifications
                = systemNotificationRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        List<NotificationResponse> notificationResponses = new ArrayList<>();
        return new ParentNotificationResponse(individualNotifications, systemNotifications);
    }

    @Override
    public IndividualNotification updateIndividualNotification(int notificationId) {
        IndividualNotification notification = individualNotificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("The individual notification not found!"));
        notification.setHasSeen(true);
        notification.setSeenTime(new Date());
        return individualNotificationRepository.save(notification);
    }

    @Override
    public ViewSystemNotification updateViewSystemNotification(int notificationId, User parent) {
        Optional<SystemNotification> notification = systemNotificationRepository.findById(notificationId);
        if (!notification.isPresent()) {
            throw new IllegalArgumentException("The system notification not found!!");
        }
        SystemNotification notification1 = notification.get();

        ViewSystemNotification viewSystemNotification = ViewSystemNotification.builder()
                .systemNotification(notification1)
                .hasSeenBy(parent)
                .seenTime(new Date())
                .build();
        return viewSystemNotificationRepository.save(viewSystemNotification);
    }

    @Override
    public Boolean checkHasSeenSystemNotification(int systemNotificationId, int parentId) {
        ViewSystemNotification hasView = viewSystemNotificationRepository
                .findBySystemNotificationIdAndUserId(systemNotificationId, parentId);
        if (hasView == null) {
            return false;
        }
        return true;
    }
}
