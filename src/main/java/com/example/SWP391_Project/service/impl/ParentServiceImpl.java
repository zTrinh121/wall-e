package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.dto.FeedbackDto;
import com.example.SWP391_Project.enums.PaymentStatus;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.response.ParentNotificationResponse;
import com.example.SWP391_Project.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private IndividualNotificationRepository individualNotificationRepository;

    @Autowired
    private SystemNotificationRepository systemNotificationRepository;

    @Autowired
    private ViewSystemNotificationRepository viewSystemNotificationRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserCenterRepository userCenterRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private StudentSlotRepository studentSlotRepository;

    @Override
    public List<Result> getStudentResults(int parentId) {
        Optional<List<Result>> results = resultRepository.findAllResultsWithParentUserId(parentId);
        return results.orElse(Collections.emptyList());
    }

    @Override
    public List<Slot> getStudentSlots(int parentId) {
        Optional<List<Slot>> slots = slotRepository.findAllSlotsWithParentUserId(parentId);
        return slots.orElse(Collections.emptyList());
    }

    @Override
    public List<Course> getStudentCourses(int parentId) {
        Optional<List<Course>> courses = courseRepository.findAllCoursesWithParentUserId(parentId);
        return courses.orElse(Collections.emptyList());
    }

//    @Override
//    public List<Attendance> getStudentAttendances(int parentId) {
//        Optional<List<Attendance>> attendances = attendanceRepository.findAllAttendanceByParentId(parentId);
//        return attendances.orElse(Collections.emptyList());
//    }

    @Override
    public void enrollStudentInCourse(EnrollmentDto enrollmentDto, int parentId) {
        User parent = userRepository.findById(parentId).orElse(null);

        if (parent == null) {
            throw new IllegalArgumentException("Parent with ID " + parentId + " does not exist.");
        }

        int studentId = enrollmentDto.getStudentId();
        User student = userRepository.findById(studentId).orElse(null);

        if (student != null && student.getParent() != null && student.getParent().getId() == parentId) {
            int courseId = enrollmentDto.getCourseId();
            Course course = courseRepository.findById(courseId).orElse(null);

            if (course != null) {
                Enrollment enrollment = Enrollment.builder()
                        .student(student)
                        .course(course)
                        .build();
                enrollmentRepository.save(enrollment);
            } else {
                throw new IllegalArgumentException("Course with ID " + courseId + " does not exist.");
            }
        } else {
            throw new IllegalArgumentException("Student with ID " + studentId + " is not a child of the parent with ID " + parentId + ".");
        }
    }

    @Override
    public List<User> getStudentsByParentId(int parentId) {
        return userRepository.getStudentsByParentId(parentId);
    }

    // ----------------------------- NOTIFICATION -------------------------------
    @Override
    public ParentNotificationResponse getAllNotifications(int parentId) {
        List<IndividualNotification> individualNotifications
                = individualNotificationRepository.findNotificationsByUserId(parentId);
        List<SystemNotification> systemNotifications
                = systemNotificationRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        List<NotificationResponse> notificationResponses = new ArrayList<>();
        return new ParentNotificationResponse(individualNotifications, systemNotifications);
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
    public ViewSystemNotification updateViewSystemNotification(int notificationId, User parent) {
        Optional<SystemNotification> notification = systemNotificationRepository.findById(notificationId);
        if (!notification.isPresent()) {
            throw new IllegalArgumentException("The system notification not found!!");
        }
        SystemNotification notification1 = notification.get();

        ViewSystemNotification viewSystemNotification = ViewSystemNotification.builder()
                .systemNotification(notification1)
                .hasSeenBy(parent)
                .seenTime(new Date())
                .build();
        return viewSystemNotificationRepository.save(viewSystemNotification);
    }

    @Override
    public Boolean checkHasSeenSystemNotification(int systemNotificationId, int parentId) {
        ViewSystemNotification hasView = viewSystemNotificationRepository
                .findBySystemNotificationIdAndUserId(systemNotificationId, parentId);
        if (hasView == null) {
            return false;
        }
        return true;
    }

    // ----------------------- FEEDBACK ------------------------
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

    // --------------------- VIEW FEEDBACKS --------------------------
    @Override
    public List<Feedback> parentFeedbackViewer(int parentId) {
        Optional<List<Feedback>> feedbacks
                = feedbackRepository.getFeedbacksTeacherSendToStudent(parentId);
        return feedbacks.orElse(Collections.emptyList());
    }

    @Override
    public List<Feedback> viewFeedbackToTeacher(int parentId) {
        Optional<List<Feedback>> feedbacks = feedbackRepository.viewFeedbacksToTeacher(parentId);
        return feedbacks.orElse(Collections.emptyList());
    }

    @Override
    public List<Feedback> viewFeedbackToCourse(int parentId) {
        Optional<List<Feedback>> feedbacks = feedbackRepository.viewFeedbacksToCourse(parentId);
        return feedbacks.orElse(Collections.emptyList());
    }

    @Override
    public List<Feedback> getAllFeedbacks(int parentId) {
        Optional<List<Feedback>> feedbacks = feedbackRepository.findByActor_Id(parentId);
        return feedbacks.orElse(Collections.emptyList());
    }
    // ---------------------------------------------------------------
    @Override
    public void enrollTheNewCourse(int studentId, int courseId) {
        Optional<User> studentOptional = userRepository.findById(studentId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        // lưu thông tin vào bảng enrollment
        if (studentOptional.isPresent() && courseOptional.isPresent()) {
            User student = studentOptional.get();
            Course course = courseOptional.get();

            Enrollment enrollment = Enrollment.builder()
                    .student(student)
                    .course(course)
                    .enrollDate(new Date())
                    .build();
            enrollmentRepository.save(enrollment);
        } else {
            // Xử lý khi không tìm thấy Student hoặc Course
            if (!studentOptional.isPresent()) {
                throw new IllegalArgumentException("Student with ID " + studentId + " not found");
            }
            if (!courseOptional.isPresent()) {
                throw new IllegalArgumentException("Course with ID " + courseId + " not found");
            }
        }

        // lưu thông tin vào bảng Bill
        Enrollment enrollment = enrollmentRepository
                .findByStudentIdAndCourseId(studentId, courseId);
        if (enrollment == null) {
            throw new RuntimeException("Enrollment not found for student ID " + studentId + " and course ID " + courseId);
        }

        Optional<PaymentMethod> optionalPaymentMethod = paymentMethodRepository.findById(2);
        if (!optionalPaymentMethod.isPresent()) {
            // Thêm log chi tiết hơn
            System.err.println("Payment method not found with ID 2");
            throw new RuntimeException("Payment method not found with ID 2");
        }
        PaymentMethod paymentMethod = optionalPaymentMethod.get();

        Bill bill = Bill.builder()
                .enrollment(enrollment)
                .createdAt(new Date())
                .status(PaymentStatus.Succeeded)
                .paymentMethod(paymentMethod)
                .build();
        billRepository.save(bill);

        // lưu thông tin vào bảng StudentSlot
        if (!studentOptional.isPresent()) {
            throw new RuntimeException("Student not found with id " + studentId);
        }
        User student = studentOptional.get();

        Optional<List<Slot>> slotsOptional = slotRepository.findByCourse_Id(courseId);
        if (!slotsOptional.isPresent() || slotsOptional.get().isEmpty()) {
            throw new RuntimeException("Slots not found for course with id " + courseId);
        }
        List<Slot> slots = slotsOptional.get();

        for (Slot slot : slots) {
            StudentSlot studentSlot = StudentSlot.builder()
                    .student(student)
                    .slot(slot)
                    .attendanceStatus(false) // Or any default value
                    .build();
            studentSlotRepository.save(studentSlot);
        }

        // lưu thông tin vào bảng UserCenter nếu cần
        Center center = courseRepository.findCenterByCourseId(courseId);
        if (center == null) {
            throw new IllegalArgumentException("Center not found for course with id " + courseId);
        }

        // Kiểm tra xem UserCenter đã tồn tại hay chưa
        boolean userCenterExists = existsByUserIdAndCenterId(studentId, center.getId());

        // Nếu UserCenter chưa tồn tại, thêm mới vào
        if (!userCenterExists) {
            UserCenter userCenter = UserCenter.builder()
                    .user(student)
                    .center(center)
                    .build();
            userCenterRepository.save(userCenter);
        }
    }

    public boolean existsByUserIdAndCenterId(int userId, int centerId) {
        UserCenter userCenter = userCenterRepository.findByUserIdAndCenterId(userId, centerId);
        return userCenter != null; // Trả về true nếu tìm thấy, false nếu không tìm thấy
    }
}
