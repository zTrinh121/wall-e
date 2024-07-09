package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.dto.FeedbackDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.response.DuplicateSlotInfo;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.response.SlotResponse;
import com.example.SWP391_Project.service.StudentService;

import jakarta.persistence.*;

import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class

StudentServiceImpl implements StudentService {

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

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

    @Transactional
    @Override
    public List<Map<String, Object>> getStudentSchedule(int studentId) {
        String query = """
                SELECT c.C01_COURSE_ID as courseId, c.C01_COURSE_CODE as courseCode, 
                c.c01_course_desc as courseDesc, c.c01_course_name as courseName,
                c.C01_COURSE_START_DATE as startTime, c.C01_COURSE_END_DATE as endTime,
                c.C01_AMOUNT_OF_STUDENTS as amountOfStudents, center.C03_CENTER_NAME as centerName,
                teacher.C14_NAME as teacherName, teacher.C14_USER_ID as teacherId,
                e.C15_STUDENT_ID as studentId, s.C02_SLOT_ID as slotId, s.C02_SLOT_DATE as slotDate,
                s.C02_SLOT_START_TIME as slotStartTime, s.C02_SLOT_END_TIME as slotEndTime, s.C02_ROOM_ID as roomId
                FROM t15_enrollment e
                JOIN t01_course c ON e.C15_COURSE_ID = c.C01_COURSE_ID
                JOIN t03_center center ON c.C01_CENTER_ID = center.C03_CENTER_ID
                JOIN t14_user teacher ON c.C01_TEACHER_ID = teacher.C14_USER_ID
                JOIN t02_slot s ON c.C01_COURSE_ID = s.C02_COURSE_ID
                WHERE e.C15_STUDENT_ID = :studentId
                """;
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
            scheduleMap.put("slotId", result[11]);
            scheduleMap.put("slotDate", result[12]);
            scheduleMap.put("slotStartTime", result[13]);
            scheduleMap.put("slotEndTime", result[14]);
            scheduleMap.put("roomId", result[15]);
            schedule.add(scheduleMap);
        }

        return schedule;
    }


    // Lấy ra bảng điểm của học sinh đó

    @Transactional
    @Override
    public List<Map<String, Object>> getStudentGrades(int studentId) {
        String query = "SELECT g.C10_RESULT_ID as resultId, g.C10_RESULT_TYPE as resultType, g.C10_RESULT_VAL as resultValue, " +
                "c.C01_COURSE_NAME as courseName, c.C01_COURSE_CODE as courseCode, c.C01_COURSE_ID as courseId " +
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
            gradeMap.put("courseId", result[5]);

            grades.add(gradeMap);
        }

        return grades;
    }

    // danh sách học sinh của lớp đó

    @Transactional
    @Override
    public List<Map<String, Object>> getStudentsByCourseId(int courseId) {
        String query = "SELECT s.C14_NAME as studentName, " +
                "s.C14_USER_PHONE as studentPhone, " +
                "CASE WHEN s.C14_PARENT_ID IS NOT NULL THEN 'Linked' ELSE 'Not Linked' END as parentStatus, " +
                "c.C01_COURSE_ID as courseId " +
                "FROM t14_user s " +
                "JOIN t15_enrollment e ON s.C14_USER_ID = e.C15_STUDENT_ID " +
                "JOIN t01_course c ON e.C15_COURSE_ID = c.C01_COURSE_ID " +
                "WHERE e.C15_COURSE_ID = :courseId";
        ;

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
    public List<Map<String, Object>> getSlotsByStudentId(int studentId) {
        String query = "SELECT DISTINCT\n" +
                "    s.C02_SLOT_ID as slot,\n" +
                "    s.C02_SLOT_DATE as slotDate, \n" +
                "    s.C02_SLOT_START_TIME as slotStartTime, \n" +
                "    s.C02_SLOT_END_TIME as slotEndTime, \n" +
                "    c.C01_COURSE_NAME as courseName, \n" +
                "    r.C18_ROOM_NAME as roomName, \n" +
                "    ss.C17_ATTENDANCE_STATUS as attendanceStatus,\n" +
                "    u_teacher.C14_NAME as teacherName\n" +
                "FROM \n" +
                "    t02_slot s \n" +
                "    JOIN t17_student_slot ss ON s.C02_SLOT_ID = ss.C17_SLOT_ID \n" +
                "    JOIN t16_user_center uc ON ss.C17_STUDENT_ID = uc.C16_USER_ID \n" +
                "    JOIN t01_course c ON s.C02_COURSE_ID = c.C01_COURSE_ID \n" +
                "    JOIN t18_room r ON s.C02_ROOM_ID = r.C18_ROOM_ID \n" +
                "    JOIN t14_user u_teacher ON c.C01_TEACHER_ID = u_teacher.C14_USER_ID \n" +
                "WHERE \n" +
                "    uc.C16_USER_ID = :studentId\n" +
                "ORDER BY \n" +
                "    CASE WHEN courseName IS NULL THEN 1 ELSE 0 END, courseName, slotDate;";

        System.out.println("Query: " + query);
        System.out.println("StudentId: " + studentId);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("studentId", studentId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> slots = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> slotMap = new HashMap<>();
            slotMap.put("slotId", result[0]);
            slotMap.put("slotDate", result[1]);
            slotMap.put("slotStartTime", result[2]);
            slotMap.put("slotEndTime", result[3]);
            slotMap.put("courseName", result[4]);
            slotMap.put("roomName", result[5]);
            slotMap.put("attendanceStatus", result[6]);  // Giá trị boolean, thay đổi tùy thuộc vào kiểu dữ liệu của C09_ATTENDANCE_STATUS
            slotMap.put("teacherName", result[7]);

            slots.add(slotMap);
        }

        return slots;
    }

//       System.out.println("Query: " + query);
//        System.out.println("CourseId: " + keyword);

    @Transactional
    @Override
    public List<Map<String, Object>> searchh(String keyword) {
        String query = "SELECT c.C01_COURSE_ID as id, c.C01_COURSE_NAME as name, c.C01_COURSE_CODE as code, c.C01_COURSE_DESC as description, " +
                "c.C01_COURSE_START_DATE as startDate, c.C01_COURSE_END_DATE as endDate, c.C01_AMOUNT_OF_STUDENTS as amountOfStudents, " +
                "c.C01_COURSE_FEE as fee, c.C01_CENTER_ID as centerId, c.C01_TEACHER_ID as teacherId, c.C01_SUBJECT_NAME as subject " +
                "FROM t01_course c WHERE c.C01_COURSE_NAME LIKE :keyword " +
                "UNION " +
                "SELECT cn.C03_CENTER_ID as id, cn.C03_CENTER_NAME as name, '' as code, '' as description, " +
                "'' as startDate, '' as endDate, '' as amountOfStudents, '' as fee, '' as centerId, '' as teacherId, '' as subject " +
                "FROM t03_center cn WHERE cn.C03_CENTER_NAME LIKE :keyword";



        System.out.println("Query: " + query);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("keyword", "%" + keyword + "%");

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> results = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", result[0]);
            resultMap.put("name", result[1]);
            resultMap.put("code", result[2]);
            resultMap.put("description", result[3]);
            resultMap.put("startDate", result[4]);
            resultMap.put("endDate", result[5]);
            resultMap.put("amountOfStudents", result[6]);
            resultMap.put("fee", result[7]);
            resultMap.put("centerId", result[8]);
            resultMap.put("teacherId", result[9]);
            resultMap.put("subject", result[10]);
            results.add(resultMap);
        }

        return results;
    }




    @Transactional
    @Override
    public List<Map<String, String>> search(String keyword) {
        String query = "SELECT 'Course' as type, c.C01_COURSE_NAME as name FROM t01_course c WHERE c.C01_COURSE_NAME LIKE :keyword " +
                "UNION " +
                "SELECT 'Center' as type, cn.C03_CENTER_NAME as name FROM t03_center cn WHERE cn.C03_CENTER_NAME LIKE :keyword";

        System.out.println("Query: " + query);
        System.out.println("CourseId: " + keyword);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("keyword", "%" + keyword + "%");

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, String>> results = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("type", (String) result[0]);
            resultMap.put("name", (String) result[1]);
            results.add(resultMap);
        }

        return results;
    }


    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.findAll
                (Sort.by(Sort.Direction.DESC, "id"));
    }

    // --------------------- NOTIFICATION --------------------------
    @Override
    public NotificationResponse getAllNotifications(int studentId) {
        List<IndividualNotification> individualNotifications
                = individualNotificationRepository.findNotificationsByUserId(studentId);
        List<CenterNotification> centerNotifications
                = centerNotificationRepository.findCenterNotificationsByUserId(studentId);
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
    public ViewCenterNotification updateViewCenterNotification(int notificationId, User student) {
        Optional<CenterNotification> notification = centerNotificationRepository.findById(notificationId);
        if (!notification.isPresent()) {
            throw new IllegalArgumentException("The center notification not found!!");
        }
        CenterNotification notification1 = notification.get();

        ViewCenterNotification viewCenterNotification = ViewCenterNotification.builder()
                .centerNotification(notification1)
                .hasSeenBy(student)
                .seenTime(new Date())
                .build();
        return viewCenterNotificationRepository.save(viewCenterNotification);
    }

    @Override
    public ViewSystemNotification updateViewSystemNotification(int notificationId, User student) {
        Optional<SystemNotification> notification = systemNotificationRepository.findById(notificationId);
        if (!notification.isPresent()) {
            throw new IllegalArgumentException("The system notification not found!!");
        }
        SystemNotification notification1 = notification.get();

        ViewSystemNotification viewSystemNotification = ViewSystemNotification.builder()
                .systemNotification(notification1)
                .hasSeenBy(student)
                .seenTime(new Date())
                .build();
        return viewSystemNotificationRepository.save(viewSystemNotification);
    }

    @Override
    public Boolean checkHasSeenCenterNotification(int centerNotificationId, int studentId) {
        ViewCenterNotification hasView = viewCenterNotificationRepository
                .findByCenterNotificationIdAndUserId(centerNotificationId, studentId);
        if (hasView == null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean checkHasSeenSystemNotification(int systemNotificationId, int studentId) {
        ViewSystemNotification hasView = viewSystemNotificationRepository
                .findBySystemNotificationIdAndUserId(systemNotificationId, studentId);
        if (hasView == null) {
            return false;
        }
        return true;
    }

    // ----------------------- FEEDBACK TO TEACHER -----------------------------
    @Override
    public Feedback createFeedbackToTeacher(User actor, FeedbackDto feedbackDto) {
        Optional<User> viewer = userRepository.findById(feedbackDto.getSendToUser_Id());
        if (viewer.isEmpty()) {
            throw new IllegalArgumentException("Teacher not found when finding by id !");
        }
        User teacher = viewer.get();

        Feedback feedback = Feedback.builder()
                .description(feedbackDto.getDescription())
                .createdAt(new Date())
                .actor(actor)
                .sendToUser(teacher)
                .rating(feedbackDto.getRating())
                .build();
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateFeedbackToTeacher(int id, FeedbackDto feedbackDto) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The feedback hasn't been existed !"));

        feedback.setDescription(feedbackDto.getDescription());
        feedback.setUpdatedAt(new Date());
        feedback.setRating(feedbackDto.getRating());

        return feedbackRepository.save(feedback);
    }
    // -------------------------------------------------------------------

    @Transactional
    @Override
    public List<Map<String, Object>> getStudentCourse(int studentId) {
        String query = """
                SELECT c.C01_COURSE_ID as courseId, c.C01_COURSE_CODE as courseCode, 
                c.c01_course_desc as courseDesc, c.c01_course_name as courseName,
                c.C01_COURSE_START_DATE as startTime, c.C01_COURSE_END_DATE as endTime,
                c.C01_AMOUNT_OF_STUDENTS as amountOfStudents, center.C03_CENTER_NAME as centerName,
                teacher.C14_NAME as teacherName, teacher.C14_USER_ID as teacherId,
                e.C15_STUDENT_ID as studentId
                FROM t15_enrollment e
                JOIN t01_course c ON e.C15_COURSE_ID = c.C01_COURSE_ID
                JOIN t03_center center ON c.C01_CENTER_ID = center.C03_CENTER_ID
                JOIN t14_user teacher ON c.C01_TEACHER_ID = teacher.C14_USER_ID
                WHERE e.C15_STUDENT_ID = :studentId
                """;
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

        return schedule;
    }

    // ----------------------- FEEDBACK TO TEACHER -----------------------------
    @Override
    public Feedback createFeedbackToCourse(User actor, FeedbackDto feedbackDto) {
        Optional<Course> courseCheck = courseRepository.findById(feedbackDto.getSendToUser_Id());
        if (courseCheck.isEmpty()) {
            throw new IllegalArgumentException("Course not found when finding by id !");
        }
        Course course = courseCheck.get();

        Feedback feedback = Feedback.builder()
                .description(feedbackDto.getDescription())
                .createdAt(new Date())
                .actor(actor)
                .sendToCourse(course)
                .rating(feedbackDto.getRating())
                .build();
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateFeedbackToCourse(int id, FeedbackDto feedbackDto) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The feedback hasn't been existed !"));

        feedback.setDescription(feedbackDto.getDescription());
        feedback.setUpdatedAt(new Date());
        feedback.setRating(feedbackDto.getRating());

        return feedbackRepository.save(feedback);
    }
    // -------------------------------------------------------------------

    // ----------------------- VIEW FEEDBACK -----------------------------
    @Override
    public List<Feedback> fetchTeacherFeedback(int studentId) {
        Optional<List<Feedback>> feedbacks = feedbackRepository.findBySendToUser_Id(studentId);
        return feedbacks.orElse(Collections.emptyList());
    }

    @Override
    public List<Feedback> viewFeedbackToTeacher(int studentId) {
        Optional<List<Feedback>> feedbacks = feedbackRepository.viewFeedbacksToTeacher(studentId);
        return feedbacks.orElse(Collections.emptyList());
    }

    @Override
    public List<Feedback> viewFeedbackToCourse(int studentId) {
        Optional<List<Feedback>> feedbacks = feedbackRepository.viewFeedbacksToCourse(studentId);
        return feedbacks.orElse(Collections.emptyList());
    }

    @Override
    public List<Feedback> getAllFeedbacks(int studentId) {
        Optional<List<Feedback>> feedbacks = feedbackRepository.findByActor_Id(studentId);
        return feedbacks.orElse(Collections.emptyList());
    }
    // -------------------------------------------------------------------

    // ------------------------ STUDENT CHECK ATTENDANCE ---------------------
    @Override
    public List<SlotResponse> getSlotsByStudentIdAndCourseId(int studentId, int courseId) {
        return slotRepository.findSlotsByStudentIdAndCourseId(studentId, courseId);
    }

    @Transactional
    @Override
    public List<Map<String, Object>> viewAttendanceGraph(int studentId, int courseId) {
        String query = "SELECT " +
                "COUNT(*) AS total_slots, " +
                "SUM(CASE WHEN ss.c17_attendance_status = 0 THEN 1 ELSE 0 END) AS absent_slots, " +
                "SUM(CASE WHEN ss.c17_attendance_status = 1 THEN 1 ELSE 0 END) AS present_slots " +
                "FROM t02_slot s " +
                "JOIN t01_course c ON c.C01_COURSE_ID = s.C02_COURSE_ID " +
                "JOIN t17_student_slot ss ON ss.C17_SLOT_ID = s.C02_SLOT_ID " +
                "WHERE ss.C17_STUDENT_ID = :studentId AND c.C01_COURSE_ID = :courseId AND s.c02_slot_date <= NOW()";

        System.out.println("Query: " + query);
        System.out.println("Student ID: " + studentId);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("studentId", studentId);
        nativeQuery.setParameter("courseId", courseId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> attendanceResults = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> attendanceMap = new HashMap<>();
            attendanceMap.put("totalSlots", result[0]);
            attendanceMap.put("absentSlots", result[1]);
            attendanceMap.put("presentSlots", result[2]);

            attendanceResults.add(attendanceMap);
        }

        return attendanceResults;
    }
    // -----------------------------------------------------------------------

    // ------------------------ ENROLLMENT NEW COURSE ---------------------
    // thoát khóa học
    @Override
    public void exitCurrentCourse(int studentId, int courseId) {
        enrollmentRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    // KIỂM TRA SLOTS TRÙNG LỊCH
    // Phương thức để tìm các ngày trong tuần mà các ngày đó trùng nhau giữa List A và List B
//    @Override
//    public List<String> findDuplicateWeekdays(int studentId, int courseId) {
//        // Lấy danh sách các Slot của khoá học mới và của sinh viên hiện tại
//        List<Slot> slotsEnrollment = findSlotsByCourseId(courseId);
//        List<Slot> slotsInCurrent = findSlotsByStudentId(studentId);
//
//        Month monthToCheck = Month.DECEMBER;
//
//        // Lọc ra các ngày trong tháng đó từ List A và List B để kiểm tra
//        List<LocalDate> daysInMonthA = filterDaysInMonth(slotsEnrollment, monthToCheck);
//        List<LocalDate> daysInMonthB = filterDaysInMonth(slotsInCurrent, monthToCheck);
//
//        // Set để lưu các ngày trong tuần mà không bị trùng lặp
//        Set<DayOfWeek> uniqueWeekDays = new HashSet<>();
//
//        // Kiểm tra và lưu các ngày trùng nhau trong cùng một tuần của tháng đó
//        for (LocalDate dateA : daysInMonthA) {
//            for (LocalDate dateB : daysInMonthB) {
//                if (isSameWeek(dateA, dateB)) {
//                    // Kiểm tra các tiêu chí không được đè lên nhau
//                    if (!isOverlapSlots(dateA, slotsEnrollment, slotsInCurrent)) {
//                        uniqueWeekDays.add(dateA.getDayOfWeek());
//                    }
//                    break; // Chỉ cần thêm một lần duy nhất, không cần lặp lại
//                }
//            }
//        }
//
//        // Định dạng ngày trong tuần và thêm vào danh sách kết quả
//        List<String> result = new ArrayList<>();
//        for (DayOfWeek dayOfWeek : uniqueWeekDays) {
//            String dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
//            result.add(dayName);
//        }
//
//        return result;
//    }
//
//    // Hàm lấy danh sách các Slot của một khoá học dựa vào courseId
//    private List<Slot> findSlotsByCourseId(int courseId) {
//        Optional<List<Slot>> optionalSlots = slotRepository.findSlotByCourse_Id(courseId);
//        return optionalSlots.orElse(new ArrayList<>());
//    }
//
//    // Hàm lấy danh sách các Slot của một sinh viên dựa vào studentId
//    private List<Slot> findSlotsByStudentId(int studentId) {
//        Optional<List<Slot>> optionalSlots = slotRepository.findSlotByStudent_Id(studentId);
//        return optionalSlots.orElse(new ArrayList<>());
//    }
//
//    // Hàm lọc ra các ngày trong một tháng từ danh sách Slot cho trước
//    private List<LocalDate> filterDaysInMonth(List<Slot> slots, Month month) {
//        List<LocalDate> filteredDates = new ArrayList<>();
//        for (Slot slot : slots) {
//            LocalDate localDate = slot.getSlotDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            if (localDate.getMonth() == month) {
//                filteredDates.add(localDate);
//            }
//        }
//        return filteredDates;
//    }
//
//    // Hàm kiểm tra xem hai ngày có cùng tuần hay không
//    private boolean isSameWeek(LocalDate date1, LocalDate date2) {
//        // Sử dụng WeekFields để lấy số tuần trong năm và so sánh
//        return date1.get(WeekFields.ISO.weekOfWeekBasedYear()) == date2.get(WeekFields.ISO.weekOfWeekBasedYear());
//    }
//
//    // Hàm kiểm tra xem hai danh sách Slot có các tiêu chí đè lên nhau hay không
//    private boolean isOverlapSlots(LocalDate date, List<Slot> slots1, List<Slot> slots2) {
//        for (Slot slot1 : slots1) {
//            for (Slot slot2 : slots2) {
//                if (slot1.getSlotDate().equals(slot2.getSlotDate())) {
//                    if (isOverlappingTime(slot1.getSlotStartTime(), slot1.getSlotEndTime(),
//                            slot2.getSlotStartTime(), slot2.getSlotEndTime())) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    // Hàm kiểm tra xem hai khoảng thời gian (slotStartTime1, slotEndTime1) và (slotStartTime2, slotEndTime2) có đè lên nhau hay không
//    private boolean isOverlappingTime(Date startTime1, Date endTime1, Date startTime2, Date endTime2) {
//        return !(endTime1.before(startTime2) || endTime2.before(startTime1));
//    }

    // KIỂM TRA SLOTS TRÙNG LỊCH
    // Phương thức để tìm các ngày trong tuần mà các ngày đó trùng nhau giữa List A và List B
    @Override
    public Map<String, List<DuplicateSlotInfo>> findDuplicateWeekdays(int studentId, int courseId) {
        List<Slot> slotsEnrollment = findSlotsByCourseId(courseId);
        List<Slot> slotsInCurrent = findSlotsByStudentId(studentId);

        Month monthToCheck = Month.DECEMBER;

        List<LocalDate> daysInMonthA = filterDaysInMonth(slotsEnrollment, monthToCheck);
        List<LocalDate> daysInMonthB = filterDaysInMonth(slotsInCurrent, monthToCheck);

        Map<String, List<DuplicateSlotInfo>> duplicateWeekdaysMap = new HashMap<>();

        for (LocalDate dateA : daysInMonthA) {
            for (LocalDate dateB : daysInMonthB) {
                if (isSameWeek(dateA, dateB)) {
                    List<DuplicateSlotInfo> duplicateSlots = findDuplicateSlots(dateA, slotsEnrollment, slotsInCurrent);
                    if (!duplicateSlots.isEmpty()) {
                        String dayOfWeek = dateA.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
                        duplicateWeekdaysMap.put(dayOfWeek, duplicateSlots);
                    }
                    break; // Only add once per week, no need to iterate further
                }
            }
        }

        return duplicateWeekdaysMap;
    }

    private List<Slot> findSlotsByCourseId(int courseId) {
        Optional<List<Slot>> optionalSlots = slotRepository.findByCourse_Id(courseId);
        return optionalSlots.orElse(new ArrayList<>());
    }

    private List<Slot> findSlotsByStudentId(int studentId) {
        Optional<List<Slot>> optionalSlots = slotRepository.findSlotByStudent_Id(studentId);
        return optionalSlots.orElse(new ArrayList<>());
    }

    private List<LocalDate> filterDaysInMonth(List<Slot> slots, Month month) {
        List<LocalDate> filteredDates = new ArrayList<>();
        for (Slot slot : slots) {
            LocalDate localDate = slot.getSlotDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (localDate.getMonth() == month) {
                filteredDates.add(localDate);
            }
        }
        return filteredDates;
    }

    private boolean isSameWeek(LocalDate date1, LocalDate date2) {
        return date1.get(WeekFields.ISO.weekOfWeekBasedYear()) == date2.get(WeekFields.ISO.weekOfWeekBasedYear());
    }

    private List<DuplicateSlotInfo> findDuplicateSlots(LocalDate date, List<Slot> slots1, List<Slot> slots2) {
        List<DuplicateSlotInfo> duplicateSlots = new ArrayList<>();
        for (Slot slot1 : slots1) {
            for (Slot slot2 : slots2) {
                if (slot1.getSlotDate().equals(slot2.getSlotDate()) && !isOverlappingTime(slot1, slot2)) {
                    DuplicateSlotInfo info = new DuplicateSlotInfo();
                    info.setSlotDate(slot1.getSlotDate());
                    info.setSlotStartTime(slot1.getSlotStartTime());
                    info.setSlotEndTime(slot1.getSlotEndTime());
                    info.setCourseName(slot2.getCourse().getName());
                    duplicateSlots.add(info);
                }
            }
        }
        return duplicateSlots;
    }

    private boolean isOverlappingTime(Slot slot1, Slot slot2) {
        // Check if there is overlapping time
        return !(slot1.getSlotEndTime().before(slot2.getSlotStartTime()) || slot1.getSlotStartTime().after(slot2.getSlotEndTime()));
    }

    @Override
    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public Course findCourseById(int courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));
    }

    @Transactional
    @Override
    public List<Map<String, Object>> getCoursesByCenterId(int centerId) {
        String query = "SELECT c.C01_COURSE_ID as id, c.C01_COURSE_NAME as name, c.C01_COURSE_START_DATE as startDate, c.C01_COURSE_END_DATE as endDate " +
                "FROM t01_course c WHERE c.C01_CENTER_ID = :centerId";

        System.out.println("Query: " + query);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("centerId", centerId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> results = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", result[0]);
            resultMap.put("name", result[1]);
            resultMap.put("startDate", result[2]);
            resultMap.put("endDate", result[3]);
            results.add(resultMap);
        }

        return results;
    }

}

