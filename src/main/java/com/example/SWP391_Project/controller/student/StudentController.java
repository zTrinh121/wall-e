package com.example.SWP391_Project.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
    @GetMapping("/student")
    public String studentDashboard() {
        return "student-dashboard";
    }

    @GetMapping("/student-timetable")
    public String studentTimetable() {
        return "student-timetable";
    }

    @GetMapping("/student-notification")
    public String studentNotification() {
        return "studentNotification";
    }
}
