package com.example.SWP391_Project.controller.parent;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.service.ParentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParentController {

    @GetMapping("/parent")
    public String parentDashboard() {
        return "parent-dashboard";
    }

    @Autowired
    private ParentService parentService;

    @GetMapping("parent/results")
    public ResponseEntity<List<Result>> getStudentResults(HttpSession session) {
        int parentId = (int) session.getAttribute("authid");
        List<Result> results = parentService.getStudentResults(parentId);
        if (results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("parent/slots")
    public ResponseEntity<List<Slot>> getStudentSlots(HttpSession session) {
        int parentId = (int) session.getAttribute("authid");
        List<Slot> slots = parentService.getStudentSlots(parentId);
        if (slots.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(slots, HttpStatus.OK);
    }

    @GetMapping("parent/courses")
    public ResponseEntity<List<Course>> getStudentCourses(HttpSession session) {
        int parentId = (int) session.getAttribute("authid");
        List<Course> courses = parentService.getStudentCourses(parentId);
        if (courses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("parent/attendances")
    public ResponseEntity<List<Attendance>> getStudentAttendances(HttpSession session) {
        int parentId = (int) session.getAttribute("authid");
        List<Attendance> attendances = parentService.getStudentAttendances(parentId);
        if (attendances.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    @PostMapping("parent/enrollment")
    public ResponseEntity<String> enrollStudentInCourse(@RequestBody EnrollmentDto enrollmentDto, HttpSession session) {
        try {
            int parentId = (int) session.getAttribute("authid");
            parentService.enrollStudentInCourse(enrollmentDto, parentId);
            return ResponseEntity.ok("Student enrolled successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
