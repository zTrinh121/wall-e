package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.MaterialDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.CloudinaryResponse;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.utils.FileUploadUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherService {
    List<String> getCourseNamesByTeacherId(Long teacherId);

    List<User> getStudentsByCourseId(Long courseId);

    List<User> searchStudentsByCourseIdAndName(Long courseId, String studentName);

  //  List<Schedule> getScheduleByCourseId(Long courseId); // lấy ra tất cả
    List<Object[]> getScheduleByCourseId(Long courseId);// lấy 3 thông tin


    // CRUD điểm
    List<Object[]> getResultsByCourseIdAndStudentId(Long courseId, Long studentId);
    Result createResult(Result result);
    Result updateResult(Result result);
    void deleteResult(Long resultId);

    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó
    List<Object[]> getScheduleByTeacherId(Long teacherId);

    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó theo trung tâm
     List<Object[]> getScheduleByTeacherIdAndCenterId(Long teacherId, Long centerId);

    void uploadPdfFile(final MultipartFile file, MaterialDto materialDto, User teacher);

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

}
