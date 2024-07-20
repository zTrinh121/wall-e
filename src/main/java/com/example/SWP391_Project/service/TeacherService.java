package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.ApplyCenterDto;
import com.example.SWP391_Project.dto.FeedbackDto;
import com.example.SWP391_Project.dto.MaterialDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.CloudinaryResponse;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.utils.FileUploadUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


    public interface TeacherService {
        List<Map<String, Object>> getCourseNamesByTeacherId(Long teacherId);
        List<User> getStudentsByCourseId(Long courseId);
        List<User> searchStudentsByCourseIdAndName(Long courseId, String studentName);
        List<Map<String, Object>> getScheduleByCourseId(Long courseId);
        List<Map<String, Object>> getResultsByCourseIdAndStudentId(Long courseId, Long studentId);
        Result updateResult(Long resultId, Map<String, Object> updates);
        Result updateResult(Result result);
        void deleteResult(Long resultId);
        List<Map<String, Object>> getScheduleByTeacherId(Long teacherId);
        List<Object[]> getScheduleByTeacherIdAndCenterId(Long teacherId, Long centerId);
        void uploadPdfFile(MultipartFile file, String subjectName, String materialsName, User teacher);
        void updatePdfFile(int materialId, MultipartFile file, String subjectName, String materialsName, User teacher);
        void deletePdfFile(int materialId);
        List<Material> getAllMaterials();
        List<Material> getMaterialsByTeacherId(int teacherId);
        NotificationResponse getAllNotifications(int studentId);
        ViewSystemNotification updateViewSystemNotification(int notificationId, User teacher);
        IndividualNotification updateIndividualNotification(int notificationId, User user);
        ViewCenterNotification updateViewCenterNotification(int notificationId, User teacher);
        Boolean checkHasSeenCenterNotification(int centerNotificationId, int teacherId);
        Boolean checkHasSeenSystemNotification(int systemNotificationId, int teacherId);
        Feedback createFeedbackToStudent(User actor, FeedbackDto feedbackDto);
        Feedback updateFeedbackToStudent(int id, FeedbackDto feedbackDto);
        Result createResult(int courseId, int studentId, int type, int value);
        List<Feedback> fetchStudentFeedback(int teacherId);
        List<Feedback> viewFeedbackToStudent(int teacherId);
        List<Feedback> fetchFeedbackToCourses(int teacherId);
        List<Feedback> fetchFeedbackToCertainCourse(int courseId);
        ApplyCenter createApplyCenterForm(User teacher, ApplyCenterDto applyCenterDto);
        List<Map<String, Object>> getStudentSlotBySlotId(int slotId);
        void updateAttendanceStatusBySlotId(int studentId, int slotId);
        void updateAttendanceStatusBySlotIdAndStatus(int studentId, int slotId, boolean status);
        List<Map<String, Object>> getAttendanceByCourseIdAndDate(int courseId, Date date);
        void updateAttendanceStatus(int studentId, int slotId, boolean status);

        List<Map<String, Object>> getStudentsByCourseIdAndDate(int courseId, Date date);

}
