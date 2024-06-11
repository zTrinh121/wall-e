package com.example.SWP391_Project.controller.manager;

import com.example.SWP391_Project.model.Center;
import com.example.SWP391_Project.model.Course;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.response.CourseDetailResponse;
import com.example.SWP391_Project.service.ManagerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manager")
public class ManagerLearningController {

    @Autowired
    ManagerService managerService;


    // ------------------------- Manager center ----------------------------
    @GetMapping("/centers")
    public ResponseEntity<List<Center>> getCenters(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        List<Center> centers = managerService.getCenters(httpSession);
        if (centers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(centers, HttpStatus.OK);
    }

    @GetMapping("/center/{centerId}")
    public ResponseEntity<Center> getCenterById(@PathVariable int centerId) {
        Optional<Center> center = managerService.findCenterById(centerId);
        return center.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // --------------------------------------------------------------------------

    @GetMapping("/courses/center/{centerId}")
    public ResponseEntity<List<Course>> getCoursesByCenterId(@PathVariable int centerId) {
        List<Course> courses = managerService.getCoursesByCenterId(centerId);
        if (courses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<CourseDetailResponse> getCoursesByCourseId(@PathVariable int courseId) {
        CourseDetailResponse courses = managerService.findCourseById(courseId);
        return ResponseEntity.ok(courses);
    }







}
