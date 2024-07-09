package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.FeedbackDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.DuplicateSlotInfo;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.response.SlotResponse;

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

    List<Map<String, Object>> searchh(String keyword);

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

    // ----------------------- FEEDBACK TO TEACHER -----------------------------
    Feedback createFeedbackToTeacher(User actor, FeedbackDto feedbackDto);
    Feedback updateFeedbackToTeacher(int id, FeedbackDto feedbackDto);

   // ------------------------------------------------------------------
    List<Map<String, Object>> getStudentCourse(int studentId);

    // ----------------------- FEEDBACK TO COURSE -----------------------------
    Feedback createFeedbackToCourse(User actor, FeedbackDto feedbackDto);
    Feedback updateFeedbackToCourse(int id, FeedbackDto feedbackDto);

    // ------------------------------------------------------------------

    // ----------------------- VIEW FEEDBACK -----------------------------
    // xem những feedback gửi đến tacher mà chính student này tạo ra
    List<Feedback> viewFeedbackToTeacher(int studentId);

    // xem những feedback gửi đến course mà chính student này tạo ra
    List<Feedback> viewFeedbackToCourse(int studentId);

    // xem tất cả các feedback mà student này tạo ra <2 loại feedback gộp chung>
    List<Feedback> getAllFeedbacks(int studentId);

    // xem những feedback mà teacher gửi đến
    List<Feedback> fetchTeacherFeedback(int studentId);
    // ------------------------------------------------------------------
     Feedback createFeedback(Feedback feedback);

    Course findCourseById(int courseId);

    List<Map<String, Object>> getCoursesByCenterId(int centerId);

    // ------------------------ STUDENT CHECK ATTENDANCE ---------------------
    List<SlotResponse> getSlotsByStudentIdAndCourseId(int studentId, int courseId);


    // -----------------------------------------------------------------------

    // ------------------------ ENROLLMENT NEW COURSE ---------------------
    // thoát khóa học
    void exitCurrentCourse(int studentId, int courseId);

    // kiểm tra trùng slot học của các khóa đang học
    Map<String, List<DuplicateSlotInfo>> findDuplicateWeekdays(int studentId, int courseId);

    // đăng kí khóa học
    void enrollTheNewCourse(int studentId, int courseId);


    List<Map<String, Object>> viewAttendanceGraph(int studentId, int courseId);


    // -----------------------------------------------------------------------


}
