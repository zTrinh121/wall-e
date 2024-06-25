package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.MaterialDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.CloudinaryResponse;
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

// lấy 3 thông tin


    // CRUD điểm
    public List<Map<String, Object>> getResultsByCourseIdAndStudentId(Long courseId, Long studentId);

     Result updateResult(Long resultId, Map<String, Object> updates);

    Result updateResult(Result result);
    void deleteResult(Long resultId);

    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó
    public List<Map<String, Object>> getScheduleByTeacherId(Long teacherId);

    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó theo trung tâm
     List<Object[]> getScheduleByTeacherIdAndCenterId(Long teacherId, Long centerId);

//     // Lấy ra cả 3 loại thông báo
//      List<PrivateNotification> getAllPrivateNotifications();
//    List<PublicNotification> getAllPublicNotifications();
//    List<SystemNotification> getAllSystemNotifications();
//    // In ra car 3
//    NotificationResponse getAllNotifications();
//
//
//    // tạo thông báo private
//    void addPrivateNotification(PrivateNotification notification);

    void uploadPdfFile(final MultipartFile file, MaterialDto materialDto, User teacher);

}
