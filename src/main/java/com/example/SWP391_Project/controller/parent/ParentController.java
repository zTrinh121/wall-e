package com.example.SWP391_Project.controller.parent;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.dto.FeedbackDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.response.ParentNotificationResponse;
import com.example.SWP391_Project.service.ParentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/parent")
public class ParentController {

    @Autowired
    private ParentService parentService;

    @GetMapping("/studentResults")
    public ResponseEntity<List<Result>> getStudentResults(HttpSession session) {
        int parentId = (int) session.getAttribute("authid");
        List<Result> results = parentService.getStudentResults(parentId);
        if (results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/studentSlots")
    public ResponseEntity<List<Slot>> getStudentSlots(HttpSession session) {
        int parentId = (int) session.getAttribute("authid");
        List<Slot> slots = parentService.getStudentSlots(parentId);
        if (slots.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(slots, HttpStatus.OK);
    }

    @GetMapping("/studentCourses")
    public ResponseEntity<List<Course>> getStudentCourses(HttpSession session) {
        Integer parentIdObj = (Integer) session.getAttribute("authid");

        if (parentIdObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // or handle differently as per your application logic
        }

        int parentId = parentIdObj.intValue();

        List<Course> courses = parentService.getStudentCourses(parentId);
        if (courses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping("/studentEnrollment")
    public ResponseEntity<String> enrollStudentInCourse(@RequestBody EnrollmentDto enrollmentDto, HttpSession session) {
        try {
            int parentId = (int) session.getAttribute("authid");
            parentService.enrollStudentInCourse(enrollmentDto, parentId);
            return ResponseEntity.ok("Student enrolled successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    @GetMapping("/studentsByParent")
    public ResponseEntity<?> getStudentsByParentId(HttpSession session) {
        try {
            int parentId = (int) session.getAttribute("authid");
            List<User> students = parentService.getStudentsByParentId(parentId);

            if (students == null || students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No students found for parent ID: " + parentId);
            }

            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch students: " + e.getMessage());
        }
    }

    // ----------------------- NOTIFICATION -----------------------
    @GetMapping("/notifications/all")
    public ResponseEntity<ParentNotificationResponse> getAllNotifications(HttpSession session) {
        Integer parentId = (Integer) session.getAttribute("authid");
        if (parentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent ID is not found in the session!");
        }
        ParentNotificationResponse notificationResponse = parentService.getAllNotifications(parentId);
        return ResponseEntity.ok(notificationResponse);
    }

    @PatchMapping("/individualNotification/update/{notificationId}")
    public IndividualNotification updateIndividualNotification(@PathVariable int notificationId) {
        return parentService.updateIndividualNotification(notificationId);
    }

    @PostMapping("/parent/viewSystemNotification/update/{notificationId}")
    public ViewSystemNotification updateViewSystemNotification(@PathVariable int notificationId,
                                                               HttpSession session) {
        User parent = (User) session.getAttribute("authid");
        if (parent == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent is not found in the session!");
        }
        return parentService.updateViewSystemNotification(notificationId, parent);
    }

    @GetMapping("/systemNotification/{systemNotificationId}/check")
    public ResponseEntity<Boolean> checkHasSeenSystemNotification(
            @PathVariable int systemNotificationId,
            HttpSession session) {
        Integer parentId = (Integer) session.getAttribute("authid");
        if (parentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ParentD is not found in the session!");
        }
        Boolean hasSeen = parentService.checkHasSeenSystemNotification(systemNotificationId, parentId);
        return ResponseEntity.ok(hasSeen);
    }

    // ----------------------------- FEEDBACK -------------------------------
    // Lấy ra những feedback mà teacher gửi đến
    @GetMapping("/view-student-feedback")
    public ResponseEntity<List<Feedback>> getFeedbackTeacherSendToStudent(HttpSession session) {
        Integer parentId = (Integer) session.getAttribute("authid");
        if (parentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent ID is not found in the session!");
        }
        List<Feedback> feedbacks = parentService.parentFeedbackViewer(parentId);
        return ResponseEntity.ok(feedbacks);
    }

    @PostMapping("/create-feedback-to-teacher")
    public ResponseEntity<Feedback> createFeedbackToTeacher(HttpSession session, @RequestBody FeedbackDto feedbackDto) {
        User actor = (User) session.getAttribute("user");
        if (actor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent is not found in the session!");
        }
        Feedback createdFeedback = parentService.createFeedbackToTeacher(actor, feedbackDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }

    @PutMapping("/update-feedback-to-teacher/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedbackToTeacher(@PathVariable("feedbackId") int id, @RequestBody FeedbackDto feedbackDto) {
        Feedback updatedFeedback = parentService.updateFeedbackToTeacher(id, feedbackDto);
        return ResponseEntity.ok(updatedFeedback);
    }

    @PostMapping("/create-feedback-to-course")
    public ResponseEntity<Feedback> createFeedbackToCourse(HttpSession session, @RequestBody FeedbackDto feedbackDto) {
        User actor = (User) session.getAttribute("user");
        if (actor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent is not found in the session!");
        }
        Feedback createdFeedback = parentService.createFeedbackToCourse(actor, feedbackDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }

    @PutMapping("/update-feedback-to-course/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedbackToCourse(@PathVariable("feedbackId") int id, @RequestBody FeedbackDto feedbackDto) {
        Feedback updatedFeedback = parentService.updateFeedbackToCourse(id, feedbackDto);
        return ResponseEntity.ok(updatedFeedback);
    }
}
