package com.example.SWP391_Project.controller.admin;

import com.example.SWP391_Project.dto.IndividualNotificationDto;
import com.example.SWP391_Project.dto.SystemNotificationDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.CenterDetailResponse;
import com.example.SWP391_Project.response.CenterPostResponse;
import com.example.SWP391_Project.service.AdminService;
import com.example.SWP391_Project.service.WebhookService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
// @RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    private WebhookService webhookService;

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

    // ------------------------------- Public notification
    // ---------------------------------
    @GetMapping("/admin-systemNotifications")
    @ResponseBody
    public ResponseEntity<List<SystemNotification>> getAllSystemNotifications() {
        List<SystemNotification> system = adminService.getAllSystemNotifications();
        return ResponseEntity.ok().body(system);
    }



    @PostMapping("/admin-systemNotification/create")
    @ResponseBody
    public SystemNotification createSystemNotification(
            @RequestBody @Valid SystemNotificationDto systemNotificationDto) {
        SystemNotification systemNotification = adminService.createSystemNotification(systemNotificationDto);
        webhookService.sendNotificationToAllUsers("New system notification created: " + systemNotification.getTitle());
        return systemNotification;
    }

    private List<String> getAllUserWebhookUrls() {
        // Lấy danh sách URL webhook của tất cả người dùng từ cơ sở dữ liệu hoặc một nguồn khác
        return Arrays.asList("https://example.com/webhook1", "https://example.com/webhook2");
    }

    @PutMapping("/admin-systemNotification/update/{id}")
    @ResponseBody
    public ResponseEntity<SystemNotification> updateSystemNotification(@PathVariable int id,
            @RequestBody @Valid SystemNotificationDto systemNotificationDto) {
        SystemNotification system = adminService.updateSystemNotification(id, systemNotificationDto);
        if (system != null) {
            return ResponseEntity.ok(system);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin-systemNotification/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteSystemNotification(@PathVariable int id) {
        boolean deleted = adminService.deleteSystemNotification(id);
        if (deleted) {
            return ResponseEntity.ok("Delete the system notification where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/ViewSystemNotifications")
//    public ResponseEntity<List<ViewSystemNotification>> getAllViewSystemNotifications() {
//        List<ViewSystemNotification> notifications = adminService.getAllViewSystemNotifications();
//        return ResponseEntity.ok(notifications);
//    }

    // API to get a single system notification by ID
    @GetMapping("/ViewSystemNotification/{notificationId}")
    public ResponseEntity<List<ViewSystemNotification>> getSystemNotificationById(@PathVariable("notificationId") int notificationId) {
        List<ViewSystemNotification> notification = adminService.getListViewersSystemNotification(notificationId);
        if (notification != null) {
            return ResponseEntity.ok(notification);
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

    // ----------------------- Individual notification ----------------------

    @GetMapping("/individualNotifications")
    public ResponseEntity<List<IndividualNotification>> getAllIndividualNotification(HttpSession httpSession) {
        int adminId = (int) httpSession.getAttribute("authid");
        List<IndividualNotification> notifications = adminService.getAllIndividualNotifications(adminId);
        if (notifications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping("/individualNotification/create")
    @ResponseBody
    public ResponseEntity<IndividualNotification> createIndividualNotification(@RequestBody @Valid IndividualNotificationDto individualNotificationDto, HttpSession httpSession) {
        User adminInfo = (User) httpSession.getAttribute("user");
        IndividualNotification notification = adminService.createIndividualNotification(individualNotificationDto, adminInfo);
        return ResponseEntity.ok().body(notification);
    }

    @PutMapping("/admin-individualNotification/update/{id}")
    public ResponseEntity<IndividualNotification> updateIndividualNotification(@PathVariable int id,
            @RequestBody @Valid IndividualNotificationDto individualNotificationDto) {
        IndividualNotification updatedNotification = adminService.updateIndividualNotification(id, individualNotificationDto);
        if (updatedNotification != null) {
            return ResponseEntity.ok(updatedNotification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin-individualNotification/delete/{id}")
    public ResponseEntity<String> deletePrivateNotification(@PathVariable int id) {
        boolean deleted = adminService.deleteIndividualNotification(id);
        if (deleted) {
            return ResponseEntity.ok("Deleted the private notification where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/individualNotification/seen")
    public ResponseEntity<List<IndividualNotification>> getViewersIndividualNotification(HttpSession httpSession) {
        int adminId = (int) httpSession.getAttribute("authid");
        List<IndividualNotification> notifications = adminService.getViewersIndividualNotification(adminId);
        return ResponseEntity.ok(notifications);
    }
}
