package com.example.SWP391_Project.controller.guest;

import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class GuestController {

    @Autowired
    GuestService guestService;

    @GetMapping("/guest")
    public String guestPage() {
        return "guest"; // This should match the name of your Thymeleaf template (guest.html)
    }

    @GetMapping("/center-posts")
    public ResponseEntity<List<CenterPost>> getAllCenterPosts() {
        List<CenterPost> centerPosts = guestService.getAllCenterPosts();
        return centerPosts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(centerPosts);
    }

    @GetMapping("/centers")
    @ResponseBody
    public ResponseEntity<List<Center>> getAllCenters() {
        List<Center> centers = guestService.getALLCenters();
        return centers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(centers);
    }

//    @GetMapping("/courses-in-center/{centerId}")
//    public ResponseEntity<List<Course>> getCoursesInCertainCenter(@PathVariable int centerId) {
//        List<Course> courses = guestService.getCoursesInCertainCenter(centerId);
//        return courses.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(courses);
//    }

    @GetMapping("/teachers-in-center/{centerId}")
    public ResponseEntity<List<User>> getTeachersInCertainCenter(@PathVariable int centerId) {
        List<User> teachers = guestService.getTeachersInCertainCenter(centerId);
        return teachers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(teachers);
    }

    @GetMapping("/feedbacks-to-teacher")
    public ResponseEntity<List<Feedback>> getFeedbacksToTeacher() {
        List<Feedback> feedbacks = guestService.getFeedbacksToTeacher();
        return feedbacks.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/slots-in-course/{courseId}")
    public ResponseEntity<List<Map<String, Object>>> getSlotsInCourse(@PathVariable int courseId) {
        List<Map<String, Object>> slotsInCourse = guestService.getSlotsInCourse(courseId);
        return new ResponseEntity<>(slotsInCourse, HttpStatus.OK);
    }
}
