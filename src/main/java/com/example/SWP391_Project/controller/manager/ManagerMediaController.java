package com.example.SWP391_Project.controller.manager;

import com.example.SWP391_Project.dto.CenterPostDto;
import com.example.SWP391_Project.dto.PublicNotificationDto;
import com.example.SWP391_Project.model.CenterPost;
import com.example.SWP391_Project.model.Feedback;
import com.example.SWP391_Project.model.PrivateNotification;
import com.example.SWP391_Project.dto.PrivateNotificationDto;
import com.example.SWP391_Project.model.PublicNotification;
import com.example.SWP391_Project.service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerMediaController {

    @Autowired
    ManagerService managerService;

    // ---------------------- PRIVATE NOTIFICATION -------------------------
    @GetMapping("/privateNotifications")
    public ResponseEntity<List<PrivateNotification>> getPrivateNotifications() {
        List<PrivateNotification> notifications = managerService.getAllPrivateNotification();
        return ResponseEntity.ok().body(notifications);
    }

    @PostMapping("/privateNotification/create")
    public PrivateNotification createPrivateNotification(@RequestBody @Valid PrivateNotificationDto privateNotificationDto) {
        return managerService.createPrivateNotification(privateNotificationDto);
    }

    @PutMapping("/privateNotification/update/{id}")
    public ResponseEntity<PrivateNotification> updatePrivateNotification(@PathVariable int id,
                                                                         @RequestBody @Valid PrivateNotificationDto privateNotificationDto) {
        PrivateNotification notification = managerService.updatePrivateNotification(id, privateNotificationDto);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/privateNotification/delete/{id}")
    public ResponseEntity<String> deletePrivateNotification(@PathVariable int id) {
        boolean deleted = managerService.deletePrivateNotification(id);
        if (deleted) {
            return ResponseEntity.ok("Delete the private notification where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // ---------------------------------------------------------------------


    // ---------------------- PUBLIC NOTIFICATION -------------------------
    @GetMapping("/publicNotifications")
    public ResponseEntity<List<PublicNotification>> getPublicNotifications() {
        List<PublicNotification> notifications = managerService.getAllPublicNotification();
        return ResponseEntity.ok().body(notifications);
    }

    @PostMapping("/publicNotification/create")
    public PublicNotification createPublicNotification(@RequestBody @Valid PublicNotificationDto publicNotificationDto) {
        return managerService.createPublicNotification(publicNotificationDto);
    }

    @PutMapping("/publicNotification/update/{id}")
    public ResponseEntity<PublicNotification> updatePublicNotification(@PathVariable int id,
                                                                       @RequestBody @Valid PublicNotificationDto publicNotificationDto) {
        PublicNotification notification = managerService.updatePublicNotification(id, publicNotificationDto);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/publicNotification/delete/{id}")
    public ResponseEntity<String> deletePublicNotification(@PathVariable int id) {
        boolean deleted = managerService.deletePublicNotification(id);
        if (deleted) {
            return ResponseEntity.ok("Delete the public notification where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // --------------------------------------------------------------------


    // --------------------------- CENTER POST -----------------------------
    @GetMapping("/centerPosts")
    public ResponseEntity<List<CenterPost>> getCenterPosts() {
        List<CenterPost> posts = managerService.getAllCenterPost();
        return ResponseEntity.ok().body(posts);
    }

    @PostMapping("/centerPosts/create")
    public CenterPost createCenterPost(@RequestBody @Valid CenterPostDto centerPostDto) {
        return managerService.createCenterPost(centerPostDto);
    }

    //pathvariable => id bị ảnh hưởng ngay trên fe
    //requestbody ... => do ảnh hưởng của dữ liệu người dùng nhập vào
    @PutMapping("/centerPosts/update/{id}")
    public ResponseEntity<CenterPost> updatePublicNotification(@PathVariable int id,
                                                               @RequestBody @Valid CenterPostDto centerPostDto) {
        CenterPost centerPost = managerService.updateCenterPost(id, centerPostDto);
        if (centerPost != null) {
            return ResponseEntity.ok(centerPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/centerPosts/delete/{id}")
    public ResponseEntity<String> deleteCenterPost(@PathVariable int id) {
        boolean deleted = managerService.deleteCenterPost(id);
        if (deleted) {
            return ResponseEntity.ok("Delete the center post where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // ---------------------------------------------------------------------


    // --------------------------- FEEDBACKS -----------------------------
    @GetMapping("/feedbacks")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = managerService.getAllFeedbacks();
        return ResponseEntity.ok().body(feedbacks);
    }
}

