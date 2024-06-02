package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {
    @GetMapping("/manager")
    public String managerDashboard() {
        return "manager-dashboard";
    }
}
