package com.example.SWP391_Project.controller.student;

import com.example.SWP391_Project.dto.FeedbackDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.DuplicateSlotInfo;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.response.SlotResponse;
import com.example.SWP391_Project.service.StudentService;
import com.example.SWP391_Project.service.UserService;
import com.example.SWP391_Project.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/student-dashboard")
    public String studentDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        int userId = (int) session.getAttribute("userId");
        model.addAttribute("studentId", userId); // Đưa studentId vào model
        model.addAttribute("user", user);
        return "student-dashboard";
    }

    @GetMapping("/student-details")
    public String getStudentDetails(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        int studentId = user.getId();
        User student = studentService.getStudentById(studentId); // Sử dụng studentId để lấy thông tin sinh viên
        model.addAttribute("student", student);

        return "student-details";
    }

    // lấy ra các khó hjc mà thằng học sinh đó đang học
    @GetMapping("/{studentId}/timetable")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getStudentSchedule(@PathVariable int studentId) {
        List<Map<String, Object>> schedule = studentService.getStudentSchedule(studentId);
        return ResponseEntity.ok(schedule);
    }

    // lấy ra bảng điểm của học sinh đó
    @GetMapping("/{studentId}/grades")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getStudentGrades(@PathVariable int studentId) {
        List<Map<String, Object>> grades = studentService.getStudentGrades(studentId);
        return ResponseEntity.ok(grades);
    }


    // Lấy ra danh sách của lớp học đó
    @GetMapping("/course/{courseId}/students")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getStudentsByCourseId(@PathVariable int courseId) {
        List<Map<String, Object>> students = studentService.getStudentsByCourseId(courseId);
        return ResponseEntity.ok(students);
    }


    @GetMapping("/{studentId}/slots")
    @ResponseBody
    public List<Map<String, Object>> getSlotsByStudentId(@PathVariable int studentId) {
        return studentService.getSlotsByStudentId(studentId);
    }


//    @GetMapping("/search")
//    @ResponseBody  // Đảm bảo rằng dữ liệu trả về là JSON/XML
//    public List<Map<String, String>> search(@RequestParam String keyword) {
//        return studentService.search(keyword);
//    }

    @GetMapping("/searchh")
    @ResponseBody
    public List<Map<String, Object>> searchh(@RequestParam String keyword) {
        return studentService.searchh(keyword);
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Map<String, String>> search(@RequestParam String keyword) {
        return studentService.search(keyword);
    }

    @Autowired
    private UserService userService;

    @PostMapping("/send-verification-email")
    public String sendVerificationEmail(@RequestParam String email, HttpSession session, Model model) {
        // Generate verification code
        String verificationCode = generateVerificationCode();

        // Save verification code and email in session
        session.setAttribute("verificationCode", verificationCode);
        session.setAttribute("emailToVerify", email);

        // Send verification email
        sendEmail(email, verificationCode);

        model.addAttribute("message", "Verification email sent to " + email);
        return "verify-emaill"; // Tên của template HTML
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Tạo mã xác nhận 6 chữ số
        return String.valueOf(code);
    }

    private void sendEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Email Verification Code");
        message.setText("Your verification code is: " + code);
        mailSender.send(message);
    }

    @PostMapping("/verify-emaill")
    public String verifyEmaill(@RequestParam String code, HttpSession session, Model model) {
        String storedCode = (String) session.getAttribute("verificationCode");
        String emailToVerify = (String) session.getAttribute("emailToVerify");
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        System.out.println("ID mapping: " + id);

        if (storedCode != null && storedCode.equals(code)) {
            // Lấy người dùng có email nhận mã xác nhận
            User userToVerify = userService.findByEmail(emailToVerify);
            if (userToVerify != null) {
                // Cập nhật C14_PARENT_ID của người dùng có ID 1 thành ID của email vừa nhập
                userService.updateParentIdById(id, userToVerify.getId());
            }

            session.removeAttribute("verificationCode");
            session.removeAttribute("emailToVerify");

            return "redirect:/login";
        } else {
            model.addAttribute("error", "Invalid verification code");
            return "verify-emaill";
        }
    }

    // ---------------------- GET ALL MATERIALS ----------------------------
    @GetMapping("/allMaterials")
    public ResponseEntity<List<Material>> getAllMaterials() {
        List<Material> materials = studentService.getAllMaterials();
        if (materials.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(materials);
        }
    }


    // ------------------------- NOTIFICATION -----------------------------
    @GetMapping("notifications/all")
    public ResponseEntity<NotificationResponse> getAllNotifications(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("authid");
        if (studentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is not found in the session!");
        }
        NotificationResponse notificationResponse = studentService.getAllNotifications(studentId);
        return ResponseEntity.ok(notificationResponse);
    }

    @PatchMapping("/individualNotification/update/{notificationId}")
    @ResponseBody
    public IndividualNotification updateIndividualNotification(@PathVariable int notificationId, HttpSession session) {
        User student = (User) session.getAttribute("user");
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student is not found in the session!");
        }
        return studentService.updateIndividualNotification(notificationId, student);
    }

    @PostMapping("/viewCenterNotification/update/{notificationId}")
    @ResponseBody
    public ViewCenterNotification updateViewCenterNotification(@PathVariable int notificationId,
                                                               HttpSession session) {
        User student = (User) session.getAttribute("user");
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student is not found in the session!");
        }
        return studentService.updateViewCenterNotification(notificationId, student);
    }

    @PostMapping("/viewSystemNotification/update/{notificationId}")
    @ResponseBody
    public ViewSystemNotification updateViewSystemNotification(@PathVariable int notificationId,
                                                               HttpSession session) {
        User student = (User) session.getAttribute("user");
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student is not found in the session!");
        }
        try{
            return studentService.updateViewSystemNotification(notificationId, student);
        }catch (Exception e) {
            System.err.println("Error updating center notification: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while updating the notification");
        }

    }

    @GetMapping("/centerNotification/{centerNotificationId}/check")
    public ResponseEntity<Boolean> checkHasSeenCenterNotification(
            @PathVariable int centerNotificationId,
            HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("authid");
        if (studentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is not found in the session!");
        }
        Boolean hasSeen = studentService.checkHasSeenCenterNotification(centerNotificationId, studentId);
        return ResponseEntity.ok(hasSeen);
    }

    @GetMapping("/systemNotification/{systemNotificationId}/check")
    public ResponseEntity<Boolean> checkHasSeenSystemNotification(
            @PathVariable int systemNotificationId,
            HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("authid");
        if (studentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is not found in the session!");
        }
        Boolean hasSeen = studentService.checkHasSeenSystemNotification(systemNotificationId, studentId);
        return ResponseEntity.ok(hasSeen);
    }

    @GetMapping("/{studentId}/courses")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getStudentCourses(@PathVariable int studentId) {
        List<Map<String, Object>> courses = studentService.getStudentCourse(studentId);
        return ResponseEntity.ok(courses);
    }

    // ---------------------------- FEEDBACK TO TEACHER ------------------------------
    @PostMapping("/create-feedback-to-teacher")
    public ResponseEntity<Feedback> createFeedbackToTeacher(HttpSession session, @RequestBody FeedbackDto feedbackDto) {
        User actor = (User) session.getAttribute("user");
        if (actor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student is not found in the session!");
        }
        Feedback createdFeedback = studentService.createFeedbackToTeacher(actor, feedbackDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }

    @PutMapping("/update-feedback-to-teacher/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedbackToTeacher(@PathVariable("feedbackId") int id, @RequestBody FeedbackDto feedbackDto) {
        Feedback updatedFeedback = studentService.updateFeedbackToTeacher(id, feedbackDto);
        return ResponseEntity.ok(updatedFeedback);
    }
    // --------------------------------------------------------------------------

    // ---------------------------- FEEDBACK TO COURSE ------------------------------
    @PostMapping("/create-feedback-to-course")
    public ResponseEntity<Feedback> createFeedbackToCourse(HttpSession session, @RequestBody FeedbackDto feedbackDto) {
        User actor = (User) session.getAttribute("user");
        if (actor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student is not found in the session!");
        }
        Feedback createdFeedback = studentService.createFeedbackToCourse(actor, feedbackDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }

    @PutMapping("/update-feedback-to-course/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedbackToCourse(@PathVariable("feedbackId") int id, @RequestBody FeedbackDto feedbackDto) {
        Feedback updatedFeedback = studentService.updateFeedbackToCourse(id, feedbackDto);
        return ResponseEntity.ok(updatedFeedback);
    }
    // -----------------------------------------------------------------------

    // ----------------------- VIEW FEEDBACK -----------------------------
    // View ra những feedback mà teacher gửi đến
    @GetMapping("/fetch-teacher-feedback")
    public ResponseEntity<List<Feedback>> fetchTeacherFeedback(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("authid");
        if (studentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is not found in the session!");
        }

        List<Feedback> feedbacks = studentService.fetchTeacherFeedback(studentId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // View ra những feedback cho teacher mà student này đã tạo ra
    @GetMapping("/view-feedback-to-teacher")
    public ResponseEntity<List<Feedback>> viewFeedbackToTeacher(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("authid");
        if (studentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is not found in the session!");
        }

        List<Feedback> feedbacks = studentService.viewFeedbackToTeacher(studentId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // View ra những feedback cho course mà student này đã tạo ra
    @GetMapping("/view-feedback-to-course")
    public ResponseEntity<List<Feedback>> viewFeedbackToCourse(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("authid");
        if (studentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is not found in the session!");
        }

        List<Feedback> feedbacks = studentService.viewFeedbackToCourse(studentId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // View ra tất cả các feedbacks mà student này tạo ra <gồm 2 loại>
    @GetMapping("/view-all-feedbacks")
    public ResponseEntity<List<Feedback>> viewAllFeedbacks(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("authid");
        if (studentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is not found in the session!");
        }

        List<Feedback> feedbacks = studentService.getAllFeedbacks(studentId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }
    // ---------------------------------------------------------------------

    @GetMapping("/{studentId}/courses/{courseId}/attendance")
    @ResponseBody
    public List<Map<String, Object>> getAttendanceGraph(@PathVariable int studentId, @PathVariable int courseId) {
        return studentService.viewAttendanceGraph(studentId, courseId);
    }


    @PostMapping("/feedback")
    public ResponseEntity<Feedback> createFeedback(
            @RequestParam(value = "sendToUserId", required = false) Integer sendToUserId,
            @RequestParam(value = "sendToCourseId", required = false) Integer sendToCourseId,
            @RequestParam("description") String description,
            @RequestParam("rating") int rating,
            HttpSession session) {

        User actor = (User) session.getAttribute("user");
        if (actor == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User sendToUser = sendToUserId != null ? userService.findById(sendToUserId) : null;
        Course sendToCourse = sendToCourseId != null ? studentService.findCourseById(sendToCourseId) : null;

        Feedback feedback = Feedback.builder()
                .description(description)
                .actor(actor)
                .sendToUser(sendToUser)
                .sendToCourse(sendToCourse)
                .rating(rating)
                .createdAt(new Date())
                .build();

        Feedback createdFeedback = studentService.createFeedback(feedback);
        return ResponseEntity.ok(createdFeedback);
    }
    @GetMapping("/center/{centerId}/courses")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getCoursesByCenterId(@PathVariable int centerId) {
        List<Map<String, Object>> courses = studentService.getCoursesByCenterId(centerId);
        return ResponseEntity.ok(courses);
    }


    // ------------------------ STUDENT CHECK ATTENDANCE ---------------------
    @GetMapping("/checkOverviewAttendance/{courseId}")
    @ResponseBody
    public ResponseEntity<List<SlotResponse>> getSlotsByStudentIdAndCourseId(HttpSession session, @PathVariable int courseId) {
        Integer studentId = (Integer) session.getAttribute("authid");
        if (studentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is not found in the session!");
        }

        List<SlotResponse> slots = studentService.getSlotsByStudentIdAndCourseId(studentId, courseId);
        if (slots.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(slots, HttpStatus.OK);
    }

    @GetMapping("/toViewTheAttendance/{courseId}")
    @ResponseBody
    public List<Map<String, Object>> getAttendanceGraph(HttpSession session, @PathVariable int courseId) {
        Integer studentId = (Integer) session.getAttribute("authid");
        if (studentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is not found in the session!");
        }
        return studentService.viewAttendanceGraph(studentId, courseId);
    }
    // -----------------------------------------------------------------------

    // ------------------------ ENROLLMENT NEW COURSE ---------------------
    // thoát khóa học
    @DeleteMapping("/exitCourse/{courseId}")
    public ResponseEntity<String> deleteEnrollment(HttpSession session, @PathVariable int courseId) {
        Integer studentId = (Integer) session.getAttribute("authid");
        if (studentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is not found in the session!");
        }
        studentService.exitCurrentCourse(studentId, courseId);
        return new ResponseEntity<>("Successfully exited the course", HttpStatus.OK);
    }

    @GetMapping("/api/duplicate-weekdays/{studentId}/{courseId}")
    public ResponseEntity<Map<String, List<DuplicateSlotInfo>>> findDuplicateWeekdays(
            @PathVariable("studentId") int studentId,
            @PathVariable("courseId") int courseId
    ) {
        Map<String, List<DuplicateSlotInfo>> duplicateWeekdaysMap = studentService.findDuplicateWeekdays(studentId, courseId);

        if (duplicateWeekdaysMap.isEmpty()) {
            return ResponseEntity.notFound().build(); // or return ResponseEntity<Map<String, List<DuplicateSlotInfo>>>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(duplicateWeekdaysMap);
    }

    @PostMapping("/enroll-new-course")
    public ResponseEntity<String> enrollNewCourse(@RequestParam("studentId") int studentId,
                                                  @RequestParam("courseId") int courseId) {
        try {
            studentService.enrollTheNewCourse(studentId, courseId);
            return ResponseEntity.ok("Enrollment successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}


