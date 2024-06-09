package com.example.SWP391_Project.controller.admin;

import com.example.SWP391_Project.dto.SystemNotificationDto;
import com.example.SWP391_Project.model.PrivateNotification;
import com.example.SWP391_Project.model.PrivateNotificationDto;
import com.example.SWP391_Project.model.SystemNotification;
import com.example.SWP391_Project.response.CenterPostResponse;
import com.example.SWP391_Project.response.CenterDetailResponse;
import com.example.SWP391_Project.service.AdminService;
import jakarta.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        long userCount = adminService.getUserCount();
        long managementCount = adminService.getManagement();
        model.addAttribute("userCount", userCount);
        model.addAttribute("managementCount", managementCount);
        return "admin-dashboard"; // Tên của template view, không cần đuôi .html
    }

    // Get all manager's posts
    @GetMapping("/admin-centerPosts")
    public ResponseEntity<List<CenterPostResponse>> getCenterPosts() {
        List<CenterPostResponse> centerPosts = adminService.getCenterPosts();
        return ResponseEntity.ok().body(centerPosts);
    }

    @GetMapping("/admin-centers")
    public ResponseEntity<List<CenterDetailResponse>> getCenters() {
        List<CenterDetailResponse> centers = adminService.getCenters();
        return ResponseEntity.ok().body(centers);
    }

    // ------------------------------- Public notification ---------------------------------
    @GetMapping("/admin-publicNotifications")
    @ResponseBody
    public ResponseEntity<List<SystemNotification>> getAllSystemNotifications() {
        List<SystemNotification> system = adminService.getAllSystemNotifications();
        return ResponseEntity.ok().body(system);
    }

    @PostMapping("/admin-publicNotification/create")
    @ResponseBody
    public SystemNotification createSystemNotification(@RequestBody @Valid SystemNotificationDto systemNotificationDto) {
        return adminService.createSystemNotification(systemNotificationDto);
    }

    @PutMapping("/admin-publicNotification/update/{id}")
    @ResponseBody
    public ResponseEntity<SystemNotification> updateSystemNotification(@PathVariable int id,
                                                                       @RequestBody @Valid SystemNotificationDto systemNotificationDto) {
        SystemNotification system = adminService.updateSystemNotification(id, systemNotificationDto);
        if(system != null) {
            return ResponseEntity.ok(system);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin-publicNotification/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteSystemNotification(@PathVariable int id) {
        boolean deleted = adminService.deleteSystemNotification(id);
        if(deleted) {
            return ResponseEntity.ok("Delete the system notification where ID = "+ id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // -----------------------------------------------------------------------------------------

    @PatchMapping("admin-approveCenterPost/{id}")
    public ResponseEntity<?> approveCenterPost(@PathVariable int id) {
        try {
            adminService.approveCenterPost(id);
            return ResponseEntity.ok().body("Manager's center post approved successfully");
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to approve manager's center post: " + e.getMessage());
        }
    }

    @PatchMapping("admin-rejectCenterPost/{id}")
    public ResponseEntity<?> rejectCenterPost(@PathVariable int id) {
        try {
            adminService.rejectCenterPost(id);
            return ResponseEntity.ok().body("Manager's center post has been rejected");
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to reject manager's center post: " + e.getMessage());
        }
    }

    @PatchMapping("admin-approveCenter/{id}")
    public ResponseEntity<?> approveCenter(@PathVariable int id) {
        try {
            adminService.approveCenterApply(id);
            return ResponseEntity.ok().body("The center approved successfully");
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to approve center: " + e.getMessage());
        }
    }

    @PatchMapping("admin-rejectCenter/{id}")
    public ResponseEntity<?> rejectCenter(@PathVariable int id) {
        try {
            adminService.rejectCenterApply(id);
            return ResponseEntity.ok().body("The center rejected successfully");
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to reject center: " + e.getMessage());
        }
    }

    // ------------------------------- Private notification ---------------------------------
    @GetMapping("/admin-privateNotifications")
    public ResponseEntity<List<PrivateNotification>> getAllPrivateNotifications() {
        List<PrivateNotification> privateNotifications = adminService.getAllPrivateNotifications();
        return ResponseEntity.ok().body(privateNotifications);
    }

    @PostMapping("/admin-privateNotification/create")
    public ResponseEntity<PrivateNotification> createPrivateNotification(@RequestBody @Valid PrivateNotificationDto privateNotificationDto) {
        PrivateNotification createdNotification = adminService.createPrivateNotification(privateNotificationDto);
        return ResponseEntity.ok().body(createdNotification);
    }

    @PutMapping("/admin-privateNotification/update/{id}")
    public ResponseEntity<PrivateNotification> updatePrivateNotification(@PathVariable int id,
                                                                         @RequestBody @Valid PrivateNotificationDto privateNotificationDto) {
        PrivateNotification updatedNotification = adminService.updatePrivateNotification(id, privateNotificationDto);
        if (updatedNotification != null) {
            return ResponseEntity.ok(updatedNotification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin-privateNotification/delete/{id}")
    public ResponseEntity<String> deletePrivateNotification(@PathVariable int id) {
        boolean deleted = adminService.deletePrivateNotification(id);
        if (deleted) {
            return ResponseEntity.ok("Deleted the private notification where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

