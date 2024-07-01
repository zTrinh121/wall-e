package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.ApplyCenterDto;
import com.example.SWP391_Project.dto.FeedbackDto;
import com.example.SWP391_Project.dto.MaterialDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.CloudinaryResponse;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.utils.FileUploadUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    List<Map<String, Object>> getCourseNamesByTeacherId(Long teacherId);

    List<User> getStudentsByCourseId(Long courseId);

    List<User> searchStudentsByCourseIdAndName(Long courseId, String studentName);

    //  List<Schedule> getScheduleByCourseId(Long courseId); // lấy ra tất cả
    public List<Map<String, Object>> getScheduleByCourseId(Long courseId);

    // CRUD điểm
    public List<Map<String, Object>> getResultsByCourseIdAndStudentId(Long courseId, Long studentId);

    Result updateResult(Long resultId, Map<String, Object> updates);

    Result updateResult(Result result);

    void deleteResult(Long resultId);

    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó
    public List<Map<String, Object>> getScheduleByTeacherId(Long teacherId);

    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó theo trung tâm
    List<Object[]> getScheduleByTeacherIdAndCenterId(Long teacherId, Long centerId);

    void uploadPdfFile(MultipartFile file, String subjectName, String materialsName, User teacher);

    List<Material> getAllMaterials();

    List<Material> getMaterialsByTeacherId(int teacherId);

    // ------------------- NOTIFICATION -------------------
    NotificationResponse getAllNotifications(int studentId);

    ViewSystemNotification updateViewSystemNotification(int notificationId, User teacher);

    IndividualNotification updateIndividualNotification(int notificationId);

    ViewCenterNotification updateViewCenterNotification(int notificationId, User teacher);

    Boolean checkHasSeenCenterNotification(int centerNotificationId,
                                           int teacherId);

    Boolean checkHasSeenSystemNotification(int systemNotificationId,
                                           int teacherId);

    // ------------------- FEEDBACK -------------------------
    Feedback createFeedbackToStudent(User actor, FeedbackDto feedbackDto);

    Feedback updateFeedbackToStudent(int id, FeedbackDto feedbackDto);

    Result createResult(int courseId, int studentId, int type, int value);
    // ---------------------------------------------------------

    // ------------------- VIEW FEEDBACKS -----------------------
    // xem những feedback mà student gửi đến teacher này
    List<Feedback> fetchStudentFeedback(int teacherId);

    // xem những feedback mà student gửi đến tất cả khóa học mà mình đang dạy
    List<Feedback> fetchFeedbackToCourses(int teacherId);

    // xem những feedback mà student gửi đến một khóa học cụ thể mà mình đang dạy
    List<Feedback> fetchFeedbackToCertainCourse(int courseId);

    // xem những feedback mà chính teacher này tạo ra
    List<Feedback> viewFeedbackToStudent(int teacherId);

    // -------------------- APPLY CENTER -----------------------------
    // tạo form để apply center
    ApplyCenter createApplyCenterForm(User teacher, ApplyCenterDto applyCenterDto);

}
