package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.dto.FeedbackDto;
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

    // -------------------------- NOTIFICATION ------------------------------
    ParentNotificationResponse getAllNotifications(int parentId);

    ViewSystemNotification updateViewSystemNotification(int notificationId, User parent);

    IndividualNotification updateIndividualNotification(int notificationId);

    Boolean checkHasSeenSystemNotification(int systemNotificationId,
                                           int parentId);

    // ------------------------- FEEDBACK -----------------------------
    Feedback createFeedbackToTeacher(User actor, FeedbackDto feedbackDto);

    Feedback updateFeedbackToTeacher(int id, FeedbackDto feedbackDto);

    Feedback createFeedbackToCourse(User actor, FeedbackDto feedbackDto);

    Feedback updateFeedbackToCourse(int id, FeedbackDto feedbackDto);

    // --------------------- VIEW FEEDBACKS --------------------------
    // xem feedbacks mà teacher gửi đến con của mình
    List<Feedback> parentFeedbackViewer(int parentId);

    // xem những feedback gửi đến teacher mà chính parent này tạo ra
    List<Feedback> viewFeedbackToTeacher(int parentId);

    // xem những feedback gửi đến course mà chính parent này tạo ra
    List<Feedback> viewFeedbackToCourse(int parentId);

    // xem tất cả các feedback mà parent này tạo ra <2 loại feedback gộp chung>
    List<Feedback> getAllFeedbacks(int parentId);

    // ---------------------------------------------------------------
    // đăng kí khóa học
    void enrollTheNewCourse(int studentId, int courseId);

}
