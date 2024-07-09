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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerMediaController {

    @Autowired
    ManagerService managerService;

    // --------------------------- CENTER POST -----------------------------
    @GetMapping("/centerPosts")
    public ResponseEntity<List<CenterPost>> getCenterPosts() {
        List<CenterPost> posts = managerService.getAllCenterPost();
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/centerPosts/byCenterId/{centerId}")
    public ResponseEntity<List<CenterPost>> getCenterPostsByCenterId(@PathVariable int centerId) {
        List<CenterPost> centerPosts = managerService.findCenterPostsByCenterId(centerId);
        if (centerPosts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(centerPosts, HttpStatus.OK);
    }

    @PostMapping("/centerPosts/create")
    public ResponseEntity<CenterPost> createCenterPost(
            @Valid @RequestBody CenterPostDto postDto,
            @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            CenterPost createdPost = managerService.createCenterPost(postDto, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/centerPosts/update/{id}")
    public ResponseEntity<CenterPost> updateCenterPost(@PathVariable int id,
                                                       @RequestPart("centerPostDto") @Valid CenterPostDto centerPostDto,
                                                       @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        CenterPost centerPost = managerService.updateCenterPost(id, centerPostDto, imageFile);
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
    public ResponseEntity<IndividualNotification> createIndividualNotification(@RequestBody @Valid IndividualNotificationDto individualNotificationDto,
                                                                               HttpSession httpSession) {
        User managerInfo = (User) httpSession.getAttribute("user");
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

    @GetMapping("/centerNotifications/center/{centerId}")
    public ResponseEntity<List<CenterNotification>> getAllCenterNotifications(@PathVariable int centerId) {
        List<CenterNotification> notifications = managerService.findByCenterId(centerId);
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
        if (viewers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(viewers, HttpStatus.OK);
    }

    // -------------------------- VIEW FEEDBACKS ---------------------------
    @GetMapping("/feedbacks/teacher/{teacherId}")
    public ResponseEntity<List<Feedback>> viewFeedbacksToCertainTeacherInCenter(@PathVariable int teacherId) {
        List<Feedback> feedbacks = managerService.viewFeedbacksToCertainTeacherInCenter(teacherId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/feedbacks/course/{courseId}")
    public ResponseEntity<List<Feedback>> viewFeedbacksToCertainCourseInCenter(@PathVariable int courseId) {
        List<Feedback> feedbacks = managerService.viewFeedbacksToCertainCourseInCenter(courseId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/allFeedbacks/teachersInCenter")
    public ResponseEntity<List<Feedback>> viewAllFeedbacksToTeacher(HttpSession session) {
        Integer managerId = (Integer) session.getAttribute("authid");
        if (managerId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manager ID is not found in the session!");
        }

        List<Feedback> feedbacks = managerService.viewAllFeedbacksToTeachers(managerId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/allFeedbacks/coursesInCenter")
    public ResponseEntity<List<Feedback>> viewAllFeedbacksToCourse(HttpSession session) {
        Integer managerId = (Integer) session.getAttribute("authid");
        if (managerId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manager ID is not found in the session!");
        }

        List<Feedback> feedbacks = managerService.viewAllFeedbacksToCourses(managerId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }


}

