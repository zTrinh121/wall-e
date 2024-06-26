package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.CenterPostDto;
import com.example.SWP391_Project.dto.FeedbackDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.NotificationResponse;

import java.util.List;
import java.util.Map;

public interface StudentService {
    User getStudentById(int studentId);

    List<Map<String, Object>> getCoursesByStudentId(int studentId);

    Course getCourseById(int courseId);

    List<Map<String, Object>> getStudentSchedule(int studentId);

    List<Map<String, Object>> getStudentGrades(int studentId);

    List<Map<String, Object>> getStudentsByCourseId(int courseId);

    List<Map<String, Object>> getSlotsByStudentId(int studentId);

    List<Map<String, String>> search(String keyword);

    List<Material> getAllMaterials();

    // --------------------- NOTIFICATION ------------------------

    NotificationResponse getAllNotifications(int studentId);

    ViewSystemNotification updateViewSystemNotification(int notificationId, User student);

    IndividualNotification updateIndividualNotification(int notificationId);

    ViewCenterNotification updateViewCenterNotification(int notificationId, User student);

    Boolean checkHasSeenCenterNotification(int centerNotificationId,
                                           int studentId);

    Boolean checkHasSeenSystemNotification(int systemNotificationId,
                                           int studentId);

    // ----------------------- FEEDBACK -----------------------------
    // xem những feedback mà teacher gửi đến
    List<Feedback> fetchTeacherFeedback(int studentId);

    // xem những feedback mà chính student này tạo ra
    List<Feedback> viewFeedbackToTeacher(int studentId);

    Feedback createFeedbackToTeacher(User actor, FeedbackDto feedbackDto);

    Feedback updateFeedbackToTeacher(int id, FeedbackDto feedbackDto);
}
