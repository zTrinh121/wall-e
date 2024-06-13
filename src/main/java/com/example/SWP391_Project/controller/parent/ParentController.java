package com.example.SWP391_Project.controller.parent;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParentController {
    @GetMapping("/parent")
    public String parentDashboard() {
        return "parent-dashboard";
    }
}
