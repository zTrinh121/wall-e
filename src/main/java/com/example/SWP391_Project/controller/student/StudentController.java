package com.example.SWP391_Project.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
    @GetMapping("/student")
    public String studentDashboard() {
        return "student-dashboard";
    }
}
