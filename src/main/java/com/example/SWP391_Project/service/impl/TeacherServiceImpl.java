package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.EnrollmentRepository;
import com.example.SWP391_Project.repository.PrivateNotificationRepository;
import com.example.SWP391_Project.repository.ResultRepository;
import com.example.SWP391_Project.service.TeacherService;
import com.example.SWP391_Project.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private PrivateNotificationRepository privateNotificationRepository;

    @Override
    public List<String> getCourseNamesByTeacherId(Long teacherId) {
        return teacherRepository.findCourseNamesByTeacherId(teacherId);
    }

// List ra các thông tin học sinh trng lớp học đó
    @Override
    public List<User> getStudentsByCourseId(Long courseId) {
        return teacherRepository.findStudentsByCourseId(courseId);
    }

// Tìm kiếm hjc sinh trng 1 lớp học theo tên
    @Override
    public List<User> searchStudentsByCourseIdAndName(Long courseId, String studentName) {
        return enrollmentRepository.findStudentsByCourseIdAndName(courseId, studentName);
    }
// Thời khóa biểu của từng lớp học

    //Lấy ra tất cả
//    @Override
//    public List<Schedule> getScheduleByCourseId(Long courseId) {
//        return teacherRepository.findScheduleByCourseId(courseId);
//    }

    // Lấy ra 3 thông tin
    @Override
    public List<Object[]> getScheduleByCourseId(Long courseId) {
        return teacherRepository.findScheduleByCourseId(courseId);
    }


    // CRUD điểm
    @Override
    public List<Object[]> getResultsByCourseIdAndStudentId(Long courseId, Long studentId) {
        return teacherRepository.findResultsByCourseIdAndStudentId(courseId, studentId);
    }

    @Override
    public Result createResult(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public Result updateResult(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public void deleteResult(Long resultId) {
        resultRepository.deleteById(resultId);
    }

// Lấy ra toàn bộ thời khóa biểu của giáo viên đó
@Override
public List<Object[]> getScheduleByTeacherId(Long teacherId) {
    return teacherRepository.findScheduleByTeacherId(teacherId);
}

    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó theo trung tâm
    @Override
    public List<Object[]> getScheduleByTeacherIdAndCenterId(Long teacherId, Long centerId) {
        return teacherRepository.findScheduleByTeacherIdAndCenterId(teacherId, centerId);
    }

    // Lấy ra 3 loại thông báo
    @Override
    public List<PrivateNotification> getAllPrivateNotifications() {
        return teacherRepository.findAllPrivateNotifications();
    }

    @Override
    public List<PublicNotification> getAllPublicNotifications() {
        return teacherRepository.findAllPublicNotifications();
    }

    @Override
    public List<SystemNotification> getAllSystemNotifications() {
        return teacherRepository.findAllSystemNotifications();
    }
// in ra cả 3
    @Override
    public NotificationResponse getAllNotifications() {
        List<PrivateNotification> privateNotifications = getAllPrivateNotifications();
        List<PublicNotification> publicNotifications = getAllPublicNotifications();
        List<SystemNotification> systemNotifications = getAllSystemNotifications();
        return new NotificationResponse(privateNotifications, publicNotifications, systemNotifications);
    }

    // tạo thông báo private
    @Override
    public void addPrivateNotification(PrivateNotification notification) {
        privateNotificationRepository.save(notification); // Lưu private notification vào database
    }
}