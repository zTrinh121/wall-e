package com.example.SWP391_Project.controller.manager;

import com.example.SWP391_Project.dto.CenterNotificationDto;
import com.example.SWP391_Project.dto.CenterPostDto;
import com.example.SWP391_Project.dto.IndividualNotificationDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.service.ManagerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerMediaController {

    @Autowired
    ManagerService managerService;

//    // ---------------------- PRIVATE NOTIFICATION -------------------------
//    @GetMapping("/privateNotifications")
//    public ResponseEntity<List<PrivateNotification>> getPrivateNotifications() {
//        List<PrivateNotification> notifications = managerService.getAllPrivateNotification();
//        return ResponseEntity.ok().body(notifications);
//    }
//
//    @PostMapping("/privateNotification/create")
//    public PrivateNotification createPrivateNotification(@RequestBody @Valid PrivateNotificationDto privateNotificationDto) {
//        return managerService.createPrivateNotification(privateNotificationDto);
//    }
//
//    @PutMapping("/privateNotification/update/{id}")
//    public ResponseEntity<PrivateNotification> updatePrivateNotification(@PathVariable int id,
//                                                                         @RequestBody @Valid PrivateNotificationDto privateNotificationDto) {
//        PrivateNotification notification = managerService.updatePrivateNotification(id, privateNotificationDto);
//        if (notification != null) {
//            return ResponseEntity.ok(notification);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/privateNotification/delete/{id}")
//    public ResponseEntity<String> deletePrivateNotification(@PathVariable int id) {
//        boolean deleted = managerService.deletePrivateNotification(id);
//        if (deleted) {
//            return ResponseEntity.ok("Delete the private notification where ID = " + id);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//    // ---------------------------------------------------------------------
//
//
//    // ---------------------- PUBLIC NOTIFICATION -------------------------
//    @GetMapping("/publicNotifications")
//    public ResponseEntity<List<PublicNotification>> getPublicNotifications() {
//        List<PublicNotification> notifications = managerService.getAllPublicNotification();
//        return ResponseEntity.ok().body(notifications);
//    }
//
//    @PostMapping("/publicNotification/create")
//    public PublicNotification createPublicNotification(@RequestBody @Valid PublicNotificationDto publicNotificationDto) {
//        return managerService.createPublicNotification(publicNotificationDto);
//    }
//
//    @PutMapping("/publicNotification/update/{id}")
//    public ResponseEntity<PublicNotification> updatePublicNotification(@PathVariable int id,
//                                                                       @RequestBody @Valid PublicNotificationDto publicNotificationDto) {
//        PublicNotification notification = managerService.updatePublicNotification(id, publicNotificationDto);
//        if (notification != null) {
//            return ResponseEntity.ok(notification);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/publicNotification/delete/{id}")
//    public ResponseEntity<String> deletePublicNotification(@PathVariable int id) {
//        boolean deleted = managerService.deletePublicNotification(id);
//        if (deleted) {
//            return ResponseEntity.ok("Delete the public notification where ID = " + id);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//    // --------------------------------------------------------------------


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

    // ----------------------- Individual notification ----------------------

    @GetMapping("/individualNotifications")
    public ResponseEntity<List<IndividualNotification>> getAllIndividualNotification(HttpSession httpSession) {
        int managerId = (int) httpSession.getAttribute("authid");
        List<IndividualNotification> notifications = managerService.getAllIndividualNotifications(managerId);
        if (notifications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping("/individualNotification/create")
    @ResponseBody
    public ResponseEntity<IndividualNotification> createIndividualNotification(@RequestBody @Valid IndividualNotificationDto individualNotificationDto, HttpSession httpSession) {
        User managerInfo = (User) httpSession.getAttribute("authid");
        IndividualNotification notification = managerService.createIndividualNotification(individualNotificationDto, managerInfo);
        return ResponseEntity.ok().body(notification);
    }

    @PutMapping("/individualNotification/update/{id}")
    public ResponseEntity<IndividualNotification> updateIndividualNotification(@PathVariable int id,
                                                                               @RequestBody @Valid IndividualNotificationDto individualNotificationDto) {
        IndividualNotification updatedNotification = managerService.updateIndividualNotification(id, individualNotificationDto);
        if (updatedNotification != null) {
            return ResponseEntity.ok(updatedNotification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/individualNotification/delete/{id}")
    public ResponseEntity<String> deletePrivateNotification(@PathVariable int id) {
        boolean deleted = managerService.deleteIndividualNotification(id);
        if (deleted) {
            return ResponseEntity.ok("Deleted the private notification where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/individualNotification/seen")
    public ResponseEntity<List<IndividualNotification>> getViewersIndividualNotification(HttpSession httpSession) {
        int managerId = (int) httpSession.getAttribute("authid");
        List<IndividualNotification> notifications = managerService.getViewersIndividualNotification(managerId);
        return ResponseEntity.ok(notifications);
    }
    // ------------------------ Center notification --------------------

    @GetMapping("/centerNotifications")
    public ResponseEntity<List<CenterNotification>> getAllCenterNotifications(HttpSession httpSession) {
        int managerId = (int) httpSession.getAttribute("authid");
        List<CenterNotification> notifications = managerService.getAllCenterNotifications(managerId);

        if (notifications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        }
    }

    @PostMapping("/centerNotification/create")
    public ResponseEntity<CenterNotification> createCenterNotification(@RequestBody @Valid CenterNotificationDto centerNotificationDto) {
        CenterNotification createdNotification = managerService.createCenterNotification(centerNotificationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
    }

    @PutMapping("/centerNotification/update/{id}")
    public ResponseEntity<CenterNotification> updateCenterNotification(@PathVariable int id,
                                                                       @RequestBody @Valid CenterNotificationDto centerNotificationDto) {
        CenterNotification updatedNotification = managerService.updateCenterNotification(id, centerNotificationDto);
        if (updatedNotification != null) {
            return ResponseEntity.ok(updatedNotification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCenterNotification(@PathVariable int id) {
        boolean deleted = managerService.deleteCenterNotification(id);
        if (deleted) {
            return ResponseEntity.ok("Deleted the center notification where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/centerNotification/viewers/{notificationId}")
    public ResponseEntity<List<ViewCenterNotification>> getListViewersCenterNotification(@PathVariable int notificationId) {
        List<ViewCenterNotification> viewers = managerService.getListViewersCenterNotification(notificationId);
        if (!viewers.isEmpty()) {
            return ResponseEntity.ok(viewers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

