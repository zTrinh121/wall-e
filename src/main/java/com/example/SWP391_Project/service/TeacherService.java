package com.example.SWP391_Project.service;

import com.example.SWP391_Project.model.*;

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

     // Lấy ra cả 3 loại thông báo
      List<PrivateNotification> getAllPrivateNotifications();
    List<PublicNotification> getAllPublicNotifications();
    List<SystemNotification> getAllSystemNotifications();
    // In ra car 3
    NotificationResponse getAllNotifications();


    // tạo thông báo private
    void addPrivateNotification(PrivateNotification notification);
}