package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.model.Course;
import com.example.SWP391_Project.model.Feedback;
import com.example.SWP391_Project.model.Slot;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.repository.CourseRepository;
import com.example.SWP391_Project.repository.FeedbackRepository;
import com.example.SWP391_Project.repository.SlotRepository;
import com.example.SWP391_Project.repository.StudentRepository;
import com.example.SWP391_Project.service.StudentService;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SlotRepository slotRepository;

    @Override
    public User getStudentById(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    @Transactional
    public List<Map<String, Object>> getCoursesByStudentId(int studentId) {
        String query = "SELECT c.C01_COURSE_NAME as courseName, c.C01_COURSE_CODE as courseCode, " +
                "c.C01_COURSE_DESC as description, c.C01_COURSE_START_DATE as startDate, c.C01_COURSE_END_DATE as endDate, " +
                "c.C01_AMOUNT_OF_STUDENTS as amountOfStudents, center.C03_CENTER_NAME as centerName, " +
                "center.C03_CENTER_ID as centerId, teacher.C14_USER_NAME as teacherName, " +
                "teacher.C14_USER_ID as teacherId " +
                "FROM t15_enrollment e " +
                "JOIN t01_course c ON e.C15_COURSE_ID = c.C01_COURSE_ID " +
                "JOIN t03_center center ON c.C01_CENTER_ID = center.C03_CENTER_ID " +
                "JOIN t14_user teacher ON c.C01_TEACHER_ID = teacher.C14_USER_ID " +
                "WHERE e.C15_STUDENT_ID = ?";
        return jdbcTemplate.queryForList(query, studentId);
    }

    @Override
    public Course getCourseById(int courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    @Override
    @Transactional
    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Transactional
    @Override
    public List<Map<String, Object>> getStudentSchedule(int studentId) {
        String query = "SELECT c.C01_COURSE_ID as courseId, c.C01_COURSE_CODE as courseCode, " +
                "c.c01_course_desc as courseDesc, c.c01_course_name as courseName, " +
                "c.C01_COURSE_START_DATE as startTime, c.C01_COURSE_END_DATE as endTime, " +
                "c.C01_AMOUNT_OF_STUDENTS as amountOfStudents, center.C03_CENTER_NAME as centerName, " +
                "teacher.C14_USER_NAME as teacherName, teacher.C14_USER_ID as teacherId, " +
                "e.C15_STUDENT_ID as studentId " +
                "FROM t15_enrollment e " +
                "JOIN t01_course c ON e.C15_COURSE_ID = c.C01_COURSE_ID " +
                "JOIN t03_center center ON c.C01_CENTER_ID = center.C03_CENTER_ID " +
                "JOIN t14_user teacher ON c.C01_TEACHER_ID = teacher.C14_USER_ID " +
                "WHERE e.C15_STUDENT_ID = :studentId";

        System.out.println("Query: " + query);
        System.out.println("Student ID: " + studentId);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("studentId", studentId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> schedule = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> scheduleMap = new HashMap<>();
            scheduleMap.put("courseId", result[0]);
            scheduleMap.put("courseCode", result[1]);
            scheduleMap.put("courseDesc", result[2]);
            scheduleMap.put("courseName", result[3]);
            scheduleMap.put("startTime", result[4]);
            scheduleMap.put("endTime", result[5]);
            scheduleMap.put("amountOfStudents", result[6]);
            scheduleMap.put("centerName", result[7]);
            scheduleMap.put("teacherName", result[8]);
            scheduleMap.put("teacherId", result[9]);
            scheduleMap.put("studentId", result[10]);

            schedule.add(scheduleMap);
        }

        System.out.println("Schedule: " + schedule);

        return schedule;
    }

    // Lấy ra bảng điểm của học sinh đó

    @Transactional
    @Override
    public List<Map<String, Object>> getStudentGrades(int studentId) {
        String query = "SELECT g.C10_RESULT_ID as resultId, g.C10_RESULT_TYPE as resultType, g.C10_RESULT_VAL as resultValue, " +
                "c.C01_COURSE_NAME as courseName, c.C01_COURSE_CODE as courseCode " +
                "FROM t10_result g " +
                "JOIN t01_course c ON g.C10_COURSE_ID = c.C01_COURSE_ID " +
                "WHERE g.C10_STUDENT_ID = :studentId";

        System.out.println("Query: " + query);
        System.out.println("Student ID: " + studentId);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("studentId", studentId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> grades = new ArrayList<>();

//        List<Object[]> resultList = nativeQuery.getResultList();
//        List<Map<String, Object>> grades = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> gradeMap = new HashMap<>();
            gradeMap.put("resultId", result[0]);
            gradeMap.put("resultType", result[1]);
            gradeMap.put("resultValue", result[2]);
            gradeMap.put("courseName", result[3]);
            gradeMap.put("courseCode", result[4]);

            grades.add(gradeMap);
        }

        return grades;
    }


    @Transactional
    @Override
    public List<Map<String, Object>> getFeedbackByUserCode(String userCode) {
        String query = "SELECT f.C06_FEEDBACK_DESC as feedbackDesc " +
                "FROM t06_feedback f " +
                "JOIN t14_user u ON f.C06_USER_ID = u.C14_USER_ID " +
                "WHERE u.C14_USER_CODE = :userCode";

        System.out.println("Query: " + query);
        System.out.println("Student ID: " + userCode);

//        Query nativeQuery = entityManager.createNativeQuery(query);
//        nativeQuery.setParameter("studentId", userCode);

//        List<Object[]> resultList = nativeQuery.getResultList();
//        List<Map<String, Object>> grades = new ArrayList<>();

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("userCode", userCode);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> feedbacks = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> feedbackMap = new HashMap<>();
            feedbackMap.put("feedbackDesc", result[0]);

            feedbacks.add(feedbackMap);
        }

        return feedbacks;
    }


    // danh sách học sinh của lớp đó

    @Transactional
    @Override
    public List<Map<String, Object>> getStudentsByCourseId(int courseId) {
        String query = "SELECT s.C14_USER_NAME as studentName, " +
                "s.C14_USER_PHONE as studentPhone, " +
                "CASE WHEN s.C14_PARENT_ID IS NOT NULL THEN 'Linked' ELSE 'Not Linked' END as parentStatus, " +
                "c.C01_COURSE_ID as courseId " +
                "FROM t14_user s " +
                "JOIN t15_enrollment e ON s.C14_USER_ID = e.C15_STUDENT_ID " +
                "JOIN t01_course c ON e.C15_COURSE_ID = c.C01_COURSE_ID " +
                "WHERE e.C15_COURSE_ID = :courseId";;

        System.out.println("Query: " + query);
        System.out.println("CourseId: " + courseId);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("courseId", courseId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> students = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> studentMap = new HashMap<>();
            studentMap.put("studentName", result[0]);
            studentMap.put("studentPhone", result[1]);
            studentMap.put("parentStatus", result[2]);

            students.add(studentMap);
        }

        return students;
    }

    @Transactional
    @Override
    public List<Map<String, Object>> getPrivateNotificationsByUserCode(String userCode) {
        String query = "SELECT p.C19_TITLE as title, p.C19_CONTENT as content, p.C19_CREATED_AT as createdAt, p.C19_UPDATED_AT as updatedAt " +
                "FROM t19_private_notifications p " +
                "JOIN t14_user u ON p.C19_SEND_TO_USER = u.C14_USER_ID " +
                "WHERE u.C14_USER_CODE = :userCode";


        System.out.println("Query: " + query);
        System.out.println("userCode: " + userCode);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("userCode", userCode);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> notifications = new ArrayList<>();

//        System.out.println("Query: " + query);
//        System.out.println("CourseId: " + courseId);



//        List<Object[]> resultList = nativeQuery.getResultList();
//        List<Map<String, Object>> students = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> notificationMap = new HashMap<>();
            notificationMap.put("title", result[0]);
            notificationMap.put("content", result[1]);
            notificationMap.put("createdAt", result[2]);
            notificationMap.put("updatedAt", result[3]);

            notifications.add(notificationMap);
        }

        return notifications;
    }

    @Transactional
    @Override
    public List<Map<String, Object>> getPublicNotificationsByUserIdAndCenterId(int userId, int centerId) {
        String query = "SELECT n.C20_TITLE as title, n.C20_CONTENT as content, n.C20_CREATED_AT as createdAt, n.C20_UPDATED_AT as updatedAt " +
                "FROM t20_public_notifications n " +
                "JOIN t16_user_center uc ON n.C20_CENTER_ID = uc.C16_CENTER_ID " +
                "WHERE uc.C16_USER_ID = :userId AND n.C20_CENTER_ID = :centerId";


        System.out.println("Query: " + query);
        System.out.println("UserId: " + userId);

        System.out.println("Query: " + query);
        System.out.println("CenterId: " + centerId);

//        Query nativeQuery = entityManager.createNativeQuery(query);
//        nativeQuery.setParameter("courseId", userCode);

//        List<Object[]> resultList = nativeQuery.getResultList();
//        List<Map<String, Object>> notifications = new ArrayList<>();



        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("userId", userId);
        nativeQuery.setParameter("centerId", centerId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> notifications = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> notificationMap = new HashMap<>();
            notificationMap.put("title", result[0]);
            notificationMap.put("content", result[1]);
            notificationMap.put("createdAt", result[2]);
            notificationMap.put("updatedAt", result[3]);

            notifications.add(notificationMap);
        }

        return notifications;
    }

    @Transactional
    @Override
    public List<Map<String, Object>> getStudentAttendance(int studentId) {
        String query = "SELECT a.C09_ATTENDANCE_ID as attendanceId, a.C09_ATTENDANCE_STATUS as attendanceStatus, " +
                "a.C09_SLOT_ID as slotId, c.C01_COURSE_NAME as courseName, c.C01_COURSE_CODE as courseCode, " +
                "s.C02_SLOT_DATE as slotDate, s.C02_SLOT_START_TIME as slotStartTime, " +
                "s.C02_SLOT_END_TIME as slotEndTime, s.C02_ROOM_ID as roomId " +
                "FROM t09_attendance a " +
                "JOIN t02_slot s ON a.C09_SLOT_ID = s.C02_SLOT_ID " +
                "JOIN t01_course c ON s.C02_COURSE_ID = c.C01_COURSE_ID " +
                "WHERE a.C09_STUDENT_ID = :studentId";

        System.out.println("Query: " + query);
        System.out.println("Student ID: " + studentId);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("studentId", studentId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> attendanceList = new ArrayList<>();
        for (Object[] result : resultList) {
            Map<String, Object> attendanceMap = new HashMap<>();
            attendanceMap.put("attendanceId", result[0]);
            attendanceMap.put("attendanceStatus", result[1]);
            attendanceMap.put("slotId", result[2]);
            attendanceMap.put("courseName", result[3]);
            attendanceMap.put("courseCode", result[4]);

            attendanceMap.put("slotDate", result[5]);
            attendanceMap.put("slotStartTime", result[6]);
            attendanceMap.put("slotEndTime", result[7]);
            attendanceMap.put("roomId", result[8]);

            attendanceList.add(attendanceMap);
        }

        return attendanceList;
    }

    @Transactional
    @Override
    public List<Map<String, Object>> getSlotsByStudentId(int studentId) {
        String query = "SELECT s.C02_SLOT_DATE as slotDate, s.C02_SLOT_START_TIME as slotStartTime, s.C02_SLOT_END_TIME as slotEndTime, " +
                "c.C01_COURSE_NAME as courseName, r.C18_ROOM_NAME as roomName " +
                "FROM t02_slot s " +
                "JOIN t17_student_slot ss ON s.C02_SLOT_ID = ss.C17_SLOT_ID " +
                "JOIN t14_user u ON ss.C17_STUDENT_ID = u.C14_USER_ID " +
                "JOIN t01_course c ON s.C02_COURSE_ID = c.C01_COURSE_ID " +
                "JOIN t18_room r ON s.C02_ROOM_ID = r.C18_ROOM_ID " +
                "WHERE u.C14_USER_ID = :studentId";

        System.out.println("Query: " + query);
        System.out.println("CourseId: " + studentId);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("studentId", studentId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> slots = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> slotMap = new HashMap<>();
            slotMap.put("slotDate", result[0]);
            slotMap.put("slotStartTime", result[1]);
            slotMap.put("slotEndTime", result[2]);
            slotMap.put("courseName", result[3]);
            slotMap.put("roomName", result[4]);

            slots.add(slotMap);
        }

        return slots;
    }




}
