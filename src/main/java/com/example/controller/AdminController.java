package com.example.controller;

import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        long userCount = adminService.getUserCount();
        long managementCount = adminService.getManagement();
        model.addAttribute("userCount", userCount);
        model.addAttribute("managementCount", managementCount);
        return "admin-dashboard"; // Tên của template view, không cần đuôi .html
    }
}