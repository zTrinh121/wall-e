package com.example.SWP391_Project.controller.student;

import com.example.SWP391_Project.model.Course;
import com.example.SWP391_Project.model.Feedback;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

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


    // Lấy ra các khóa học mà thằng học sinh đó đang học( đang sai bỏ)
//    @GetMapping("/{studentId}/courses")
//    public ResponseEntity<List<Map<String, Object>>> getStudentCourses(@PathVariable int studentId) {
//        List<Map<String, Object>> courses = studentService.getCoursesByStudentId(studentId);
//        return ResponseEntity.ok(courses);
//    }

    // Tạo feedback cho giáo viên
    // Tạo feedback cho giáo viên
    @PostMapping("/{studentId}/courses/{courseId}/feedback")
    public ResponseEntity<Feedback> createFeedback(@PathVariable int studentId,
                                                   @PathVariable int courseId,
                                                   @RequestBody Feedback feedback) {
        Course course = studentService.getCourseById(courseId);
        if (course == null || !course.getStudents().stream().anyMatch(student -> student.getId() == studentId)) {
            return ResponseEntity.badRequest().build();
        }

        

        feedback.setCourse(course);
        Feedback savedFeedback = studentService.createFeedback(feedback);
        return ResponseEntity.ok(savedFeedback);
    }

    // lấy ra các khó hjc mà thằng học sinh đó đang học
    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<Map<String, Object>>> getStudentSchedule(@PathVariable int studentId) {
        List<Map<String, Object>> schedule = studentService.getStudentSchedule(studentId);
        return ResponseEntity.ok(schedule);
    }

    // lấy ra bảng điểm của học sinh đó
    @GetMapping("/{studentId}/grades")
    public ResponseEntity<List<Map<String, Object>>> getStudentGrades(@PathVariable int studentId) {
        List<Map<String, Object>> grades = studentService.getStudentGrades(studentId);
        return ResponseEntity.ok(grades);
    }

    // Feedback
    @GetMapping("/feedback/{userCode}")
    public ResponseEntity<List<Map<String, Object>>> getFeedbackByUserCode(@PathVariable String userCode) {
        List<Map<String, Object>> feedbacks = studentService.getFeedbackByUserCode(userCode);
        return ResponseEntity.ok(feedbacks);
    }

    // Lấy ra danh sách của lớp học đó
    @GetMapping("/course/{courseId}/students")
    public ResponseEntity<List<Map<String, Object>>> getStudentsByCourseId(@PathVariable int courseId) {
        List<Map<String, Object>> students = studentService.getStudentsByCourseId(courseId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/private-notifications/{userCode}")
    public ResponseEntity<List<Map<String, Object>>> getPrivateNotificationsByUserCode(@PathVariable int userCode) {
        List<Map<String, Object>> notifications = studentService.getPrivateNotificationsByUserCode(userCode);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/public-notifications/{userId}/{centerId}")
    public ResponseEntity<List<Map<String, Object>>> getPublicNotificationsByUserIdAndCenterId(
            @PathVariable int userId, @PathVariable int centerId) {
        List<Map<String, Object>> notifications = studentService.getPublicNotificationsByUserIdAndCenterId(userId, centerId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{studentId}/attendance")
    public ResponseEntity<List<Map<String, Object>>> getStudentAttendance(@PathVariable int studentId) {
        List<Map<String, Object>> attendance = studentService.getStudentAttendance(studentId);
        return ResponseEntity.ok(attendance);
    }


}


