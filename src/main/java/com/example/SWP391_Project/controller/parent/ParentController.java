package com.example.SWP391_Project.controller.parent;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.model.Attendance;
import com.example.SWP391_Project.model.Course;
import com.example.SWP391_Project.model.Result;
import com.example.SWP391_Project.model.Slot;
import com.example.SWP391_Project.service.ParentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parent")
public class ParentController {
    @GetMapping("/parent")
    public String parentDashboard() {
        return "parent-dashboard";
    }

    @Autowired
    private ParentService parentService;

    @GetMapping("/results")
    public ResponseEntity<List<Result>> getStudentResults(HttpSession session) {
        List<Result> results = parentService.getStudentResults(session);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/slots")
    public ResponseEntity<List<Slot>> getStudentSlots(HttpSession session) {
        List<Slot> slots = parentService.getStudentSlots(session);
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getStudentCourses(HttpSession session) {
        List<Course> courses = parentService.getStudentCourses(session);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/attendances")
    public ResponseEntity<List<Attendance>> getStudentAttendances(HttpSession session) {
        List<Attendance> attendances = parentService.getStudentAttendances(session);
        return ResponseEntity.ok(attendances);
    }

    @PostMapping("/enrollment")
    public ResponseEntity<String> enrollStudentInCourse(@RequestBody EnrollmentDto enrollmentDto, HttpSession session) {
        try {
            parentService.enrollStudentInCourse(enrollmentDto, session);
            return ResponseEntity.ok("Student enrolled successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
