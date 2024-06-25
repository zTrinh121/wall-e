package com.example.SWP391_Project.service;

import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.NotificationResponse;

import java.util.List;
import java.util.Map;

public interface StudentService {
    User getStudentById(int studentId);

    List<Map<String, Object>> getCoursesByStudentId(int studentId);

    Course getCourseById(int courseId);

    Feedback createFeedback(Feedback feedback);

    List<Map<String, Object>> getStudentSchedule(int studentId);

    List<Map<String, Object>> getStudentGrades(int studentId);

    List<Map<String, Object>> getFeedbackByUserCode(String userCode);

    List<Map<String, Object>> getStudentsByCourseId(int courseId);

    List<Map<String, Object>> getStudentAttendance(int studentId);

    List<Map<String, Object>> getSlotsByStudentId(int studentId);

    List<Map<String, String>> search(String keyword);

    List<Material> getAllMaterials();

    NotificationResponse getAllNotifications(int studentId);

    ViewSystemNotification updateViewSystemNotification(int notificationId, User student);

    IndividualNotification updateIndividualNotification(int notificationId);

    ViewCenterNotification updateViewCenterNotification(int notificationId, User student);

    List<Map<String, Object>> getStudentCourse(int studentId);

}
