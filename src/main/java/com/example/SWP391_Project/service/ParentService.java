package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.response.ParentNotificationResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParentService {
    // Register class
    void enrollStudentInCourse(EnrollmentDto enrollmentDto, int parentId);

    // View student progress, info (result, slot, course, attendance)
    List<Result> getStudentResults(int parentId);

    List<Slot> getStudentSlots(int parentId);

    List<Course> getStudentCourses(int parentId);

//    List<Attendance> getStudentAttendances(int parentId);

    List<User> getStudentsByParentId(int parentId);

    ParentNotificationResponse getAllNotifications(int parentId);

    ViewSystemNotification updateViewSystemNotification(int notificationId, User parent);

    IndividualNotification updateIndividualNotification(int notificationId);

}
