package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.dto.MaterialDto;
import com.example.SWP391_Project.exception.ResourceNotFoundException;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.response.CloudinaryResponse;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.service.TeacherService;
import com.example.SWP391_Project.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private IndividualNotificationRepository individualNotificationRepository;

    @Autowired
    private CenterNotificationRepository centerNotificationRepository;

    @Autowired
    private SystemNotificationRepository systemNotificationRepository;

    @Autowired
    private ViewCenterNotificationRepository viewCenterNotificationRepository;

    @Autowired
    private ViewSystemNotificationRepository viewSystemNotificationRepository;

    @Override
    public List<Map<String, Object>> getCourseNamesByTeacherId(Long teacherId) {
        List<Object[]> results = teacherRepository.findCourseNamesByTeacherId(teacherId);
        List<Map<String, Object>> courseInfos = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> courseInfo = new HashMap<>();
            courseInfo.put("courseName", result[0]);
            courseInfo.put("courseId", result[1]);
            courseInfo.put("courseCode", result[2]);
            courseInfo.put("courseDescription", result[3]);
            courseInfo.put("courseStartDate", result[4]);
            courseInfo.put("courseEndDate", result[5]);
            courseInfo.put("amountOfStudents", result[6]);
            courseInfo.put("roomName", result[7]);
            courseInfo.put("centerName", result[8]);
            courseInfos.add(courseInfo);
        }
        return courseInfos;
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
    public List<Map<String, Object>> getScheduleByCourseId(Long courseId) {
        List<Object[]> results = teacherRepository.findScheduleByCourseId(courseId);
        List<Map<String, Object>> schedules = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> schedule = new HashMap<>();
            schedule.put("slotDate", result[0]);
            schedule.put("slotStartTime", result[1]);
            schedule.put("slotEndTime", result[2]);
            schedule.put("dayName", result[3]);
            schedule.put("roomName", result[4]);
            schedules.add(schedule);
        }
        return schedules;
    }







    // CRUD điểm
    @Override
    public List<Map<String, Object>> getResultsByCourseIdAndStudentId(Long courseId, Long studentId) {
        List<Object[]> results = teacherRepository.findResultsByCourseIdAndStudentId(courseId, studentId);
        List<Map<String, Object>> resultMaps = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("courseId", result[0]);
            resultMap.put("studentId", result[1]);
            resultMap.put("type", result[2]);
            resultMap.put("value", result[3]);
            resultMaps.add(resultMap);
        }
        return resultMaps;
    }


//nnnn
@Override
public Result updateResult(Long resultId, Map<String, Object> updates) {
    // Tìm Result từ cơ sở dữ liệu
    Result result = resultRepository.findResultById(resultId);
    if (result == null) {
        throw new ResourceNotFoundException("Result not found for this id :: " + resultId);
    }

    // Cập nhật các trường cần thiết
    if (updates.containsKey("type")) {
        result.setType((int) updates.get("type"));
    }
    if (updates.containsKey("value")) {
        result.setValue((int) updates.get("value"));
    }

    // Lưu kết quả đã cập nhật vào cơ sở dữ liệu
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
    public List<Map<String, Object>> getScheduleByTeacherId(Long teacherId) {
        List<Object[]> results = teacherRepository.findScheduleByTeacherId(teacherId);
        List<Map<String, Object>> schedules = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> schedule = new HashMap<>();
            schedule.put("slotDate", result[0]);
            schedule.put("slotStartTime", result[1]);
            schedule.put("slotEndTime", result[2]);
            schedule.put("courseName", result[3]);
            schedule.put("roomName", result[4]);
            schedules.add(schedule);
        }
        return schedules;
    }


    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó theo trung tâm
    @Override
    public List<Object[]> getScheduleByTeacherIdAndCenterId(Long teacherId, Long centerId) {
        return teacherRepository.findScheduleByTeacherIdAndCenterId(teacherId, centerId);
    }


//    // Lấy ra 3 loại thông báo
//    @Override
//    public List<PrivateNotification> getAllPrivateNotifications() {
//        return teacherRepository.findAllPrivateNotifications();
//    }
//
//    @Override
//    public List<PublicNotification> getAllPublicNotifications() {
//        return teacherRepository.findAllPublicNotifications();
//    }
//
//    @Override
//    public List<SystemNotification> getAllSystemNotifications() {
//        return teacherRepository.findAllSystemNotifications();
//    }
//// in ra cả 3
//    @Override
//    public NotificationResponse getAllNotifications() {
//        List<PrivateNotification> privateNotifications = getAllPrivateNotifications();
//        List<PublicNotification> publicNotifications = getAllPublicNotifications();
//        List<SystemNotification> systemNotifications = getAllSystemNotifications();
//        return new NotificationResponse(privateNotifications, publicNotifications, systemNotifications);
//    }
//
//    // tạo thông báo private
//    @Override
//    public void addPrivateNotification(PrivateNotification notification) {
//        privateNotificationRepository.save(notification); // Lưu private notification vào database
//    }


    @Transactional
    public void uploadPdfFile(MultipartFile file, String subjectName, String materialsName, User teacher) {
        FileUploadUtil.assertAllowedPDF(file);

        try {
            String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
            CloudinaryResponse response = cloudinaryService.uploadPdfFile(file, fileName);
            Material material = new Material();
            material.setMaterialsName(materialsName); // ten file FPD
            material.setSubjectName(subjectName);
            material.setTeacher(teacher);
            material.setPdfPath(response.getUrl());
            material.setCloudinaryPdfId(response.getPublicId());

            materialRepository.save(material);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload PDF file and save material: " + e.getMessage());
        }
    }

    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.findAll
                (Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<Material> getMaterialsByTeacherId(int teacherId) {
        Optional<List<Material>> materials = materialRepository.findByTeacher_Id(teacherId);
        return materials.orElse(Collections.emptyList());
    }

    @Override
    public NotificationResponse getAllNotifications(int teacherId) {
        List<IndividualNotification> individualNotifications
                = individualNotificationRepository.findNotificationsByUserId(teacherId);
        List<CenterNotification> centerNotifications
                = centerNotificationRepository.findCenterNotificationsByUserId(teacherId);
        List<SystemNotification> systemNotifications
                = systemNotificationRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        List<NotificationResponse> notificationResponses = new ArrayList<>();
        return new NotificationResponse(individualNotifications, centerNotifications, systemNotifications);
    }

    @Override
    public IndividualNotification updateIndividualNotification(int notificationId) {
        IndividualNotification notification = individualNotificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("The individual notification not found!"));
        notification.setHasSeen(true);
        notification.setSeenTime(new Date());
        return individualNotificationRepository.save(notification);
    }

    @Override
    public ViewCenterNotification updateViewCenterNotification(int notificationId, User teacher) {
        Optional<CenterNotification> notification = centerNotificationRepository.findById(notificationId);
        if (!notification.isPresent()) {
            throw new IllegalArgumentException("The center notification not found!!");
        }
        CenterNotification notification1 = notification.get();

        ViewCenterNotification viewCenterNotification = ViewCenterNotification.builder()
                .centerNotification(notification1)
                .hasSeenBy(teacher)
                .seenTime(new Date())
                .build();
        return viewCenterNotificationRepository.save(viewCenterNotification);
    }

    @Override
    public ViewSystemNotification updateViewSystemNotification(int notificationId, User teacher) {
        Optional<SystemNotification> notification = systemNotificationRepository.findById(notificationId);
        if (!notification.isPresent()) {
            throw new IllegalArgumentException("The system notification not found!!");
        }
        SystemNotification notification1 = notification.get();

        ViewSystemNotification viewSystemNotification = ViewSystemNotification.builder()
                .systemNotification(notification1)
                .hasSeenBy(teacher)
                .seenTime(new Date())
                .build();
        return viewSystemNotificationRepository.save(viewSystemNotification);
    }

    @Override
    public Boolean checkHasSeenCenterNotification(int centerNotificationId, int teacherId) {
        ViewCenterNotification hasView = viewCenterNotificationRepository
                .findByCenterNotificationIdAndUserId(centerNotificationId, teacherId);
        if (hasView == null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean checkHasSeenSystemNotification(int systemNotificationId, int teacherId) {
        ViewSystemNotification hasView = viewSystemNotificationRepository
                .findBySystemNotificationIdAndUserId(systemNotificationId, teacherId);
        if (hasView == null) {
            return false;
        }
        return true;
    }
}