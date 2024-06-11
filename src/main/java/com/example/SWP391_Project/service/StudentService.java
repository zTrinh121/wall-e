package com.example.SWP391_Project.service;

import com.example.SWP391_Project.model.Course;
import com.example.SWP391_Project.model.Feedback;
import com.example.SWP391_Project.model.User;

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

    List<Map<String, Object>> getPrivateNotificationsByUserCode(int userCode);

    List<Map<String, Object>> getPublicNotificationsByUserIdAndCenterId(int userId, int centerId);

}
