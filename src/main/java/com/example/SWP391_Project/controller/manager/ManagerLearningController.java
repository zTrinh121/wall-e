package com.example.SWP391_Project.controller.manager;

import com.example.SWP391_Project.dto.CenterDto;
import com.example.SWP391_Project.dto.CourseDto;
import com.example.SWP391_Project.dto.SlotDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.CourseDetailResponse;
import com.example.SWP391_Project.service.ManagerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manager")
public class ManagerLearningController {

    @Autowired
    ManagerService managerService;


    // --------------------------- MANAGER CENTER ------------------------------
    @GetMapping("/centers")
    public ResponseEntity<?> getCenters(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Don't create a new session if one doesn't exist
        if (session == null) {
            throw new RuntimeException("Session not found!");
        }

        String sessionId = session.getId();
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("It in session page: " + sessionId + " " + userId);

        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new RuntimeException("User not found in session!");
            }
            System.out.println("Manager validate: " + user);

            // Pass the HttpServletRequest to the service method
            List<Center> centers = managerService.getCenters(request);
            if (centers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(centers, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Log the exception (use a logger in real applications)
            System.err.println(e.getMessage());

            // Return a custom error response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/centers");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/center/{centerId}")
    public ResponseEntity<Center> getCenterById(@PathVariable int centerId) {
        Center center = managerService.findCenterById(centerId);
        if (center != null) {
            return ResponseEntity.ok(center);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/center/create")
    public Center createCenter(@RequestBody @Valid CenterDto centerDto, HttpSession session) {
        return managerService.createCenter(centerDto, session);
    }

    @PutMapping("/center/update/{centerId}")
    public ResponseEntity<Center> updateCenterInfo(@PathVariable int id,
                                                   @RequestBody @Valid CenterDto centerDto) {
        Center center = managerService.updateCenterInfo(id, centerDto);
        if (center != null) {
            return ResponseEntity.ok(center);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/center/delete/{id}")
    public ResponseEntity<String> deleteCenter(@PathVariable int id) {
        boolean deleted = managerService.deleteCenter(id);
        if (deleted) {
            return ResponseEntity.ok("Delete the center where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // --------------------------------------------------------------------------


    // --------------------------- MANAGER COURSE ------------------------------
    @GetMapping("/courses/center/{centerId}")
    public ResponseEntity<List<Course>> getCoursesByCenterId(@PathVariable int centerId) {
        List<Course> courses = managerService.getCoursesByCenterId(centerId);
        if (courses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<CourseDetailResponse> findCourseById(@PathVariable int courseId) {
        CourseDetailResponse courses = managerService.findCourseById(courseId);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/course/create")
    public Course createCourse(@RequestBody @Valid CourseDto courseDto) {
        return managerService.createCourse(courseDto);
    }

    @PutMapping("/course/update/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id,
                                               @RequestBody @Valid CourseDto courseDto) {
        Course course = managerService.updateCourse(id, courseDto);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/course/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        boolean deleted = managerService.deleteCourse(id);
        if (deleted) {
            return ResponseEntity.ok("Delete the course where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // --------------------------------------------------------------------------

    // --------------------------- MANAGER TEACHER ------------------------------
    @GetMapping("/getTeachers")
    public ResponseEntity<List<User>> getTeachersInCenter(@PathVariable int centerId) {
        List<User> teachers = managerService.getTeachersInCenter(centerId);
        if (teachers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @PatchMapping("/approveTeacher/{id}")
    public ResponseEntity<?> approveTeacherApply(@PathVariable int id) {
        try {
            managerService.approveTeacherApply(id);
            return ResponseEntity.ok().body("Successfully assigned the role of teacher to join the center.");
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("The role assignment of teacher to join the center failed." + e.getMessage());
        }
    }

    @PatchMapping("/rejectTeacher/{id}")
    public ResponseEntity<?> rejectTeacherApply(@PathVariable int id) {
        try {
            managerService.rejectTeacherApply(id);
            return ResponseEntity.ok().body("Teacher application rejected successfully.");
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to reject the teacher application: " + e.getMessage());
        }
    }

    @DeleteMapping("/teacher/delete/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable int id) {
        boolean deleted = managerService.deleteTeacher(id);
        if (deleted) {
            return ResponseEntity.ok("Delete the teacher where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // --------------------------------------------------------------------------


    // --------------------------- MANAGER STUDENT ------------------------------
    @GetMapping("/students/center/{centerId}")
    public ResponseEntity<List<User>> getStudentsInCenter(@PathVariable int centerId) {
        List<User> students = managerService.getStudentsInCenter(centerId);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/students/course/{courseId}")
    public ResponseEntity<List<User>> getStudentsInCertainCourse(@PathVariable int courseId) {
        List<User> students = managerService.getStudentsInCertainCourse(courseId);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @DeleteMapping("/student/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        boolean deleted = managerService.deleteStudent(id);
        if (deleted) {
            return ResponseEntity.ok("Delete the student where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // --------------------------------------------------------------------------


    // ---------------------------- MANAGER SLOT --------------------------------
    @GetMapping("slots/course/{courseId}")
    public ResponseEntity<List<Slot>> findSlotsInCourse(@PathVariable int courseId) {
        List<Slot> slots = managerService.findSlotsInCourse(courseId);
        if (slots.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(slots, HttpStatus.OK);
    }

    @GetMapping("slots/course/{date}")
    public ResponseEntity<List<Slot>> findSlotsInCertainDay(@PathVariable Date date) {
        List<Slot> slots = managerService.findSlotInCertainDay(date);
        if (slots.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(slots, HttpStatus.OK);
    }

    @PostMapping("/slot/create")
    public Slot createSlot(@RequestBody @Valid SlotDto slotDto) {
        return managerService.createNewSlot(slotDto);
    }

    @GetMapping("/emptyRooms")
    public List<Room> getEmptyRooms(
            @RequestParam("centerId") int centerId,
            @RequestParam("slotStartTime") Date slotStartTime,
            @RequestParam("slotEndTime") Date slotEndTime) {
        Slot slot = new Slot();
        Course course = new Course();
        Center center = new Center();

        center.setId(centerId);
        course.setCenter(center);

        slot.setCourse(course);
        slot.setSlotStartTime(slotStartTime);
        slot.setSlotEndTime(slotEndTime);
        return managerService.getListEmptyRooms(slot);
    }

    @DeleteMapping("/slot/delete/{id}")
    public ResponseEntity<String> deleteSlot(@PathVariable int id) {
        boolean deleted = managerService.deleteSlot(id);
        if (deleted) {
            return ResponseEntity.ok("Delete the slot where ID = " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // --------------------------------------------------------------------------


    // --------------------------- MANAGER REVENUE ------------------------------
    @GetMapping("/bills")
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(managerService.getAllBills());
    }

    @GetMapping("/bills/succeeded")
    public ResponseEntity<List<Bill>> findSucceededBills(
            @RequestParam int year,
            @RequestParam int month
    ) {
        return ResponseEntity.ok(managerService.findSucceededBills(Year.of(year), Month.of(month)));
    }

    @GetMapping("/bills/failed")
    public ResponseEntity<List<Bill>> findFailedBills(
            @RequestParam int year,
            @RequestParam int month
    ) {
        return ResponseEntity.ok(managerService.findFailedBills(Year.of(year), Month.of(month)));
    }

    @GetMapping("/bills/paidByCash")
    public ResponseEntity<List<Bill>> findBillsPaidByCash(
            @RequestParam int year,
            @RequestParam int month
    ) {
        return ResponseEntity.ok(managerService.findBillsPaidByCash(Year.of(year), Month.of(month)));
    }

    @GetMapping("/bills/paidByEBanking")
    public ResponseEntity<List<Bill>> findBillsPaidByEBanking(
            @RequestParam int year,
            @RequestParam int month
    ) {
        return ResponseEntity.ok(managerService.findBillsPaidByEBanking(Year.of(year), Month.of(month)));
    }

    @GetMapping("/students/withPaidFeesInCourse")
    public ResponseEntity<List<User>> findStudentsWithPaidFeesInCourse(
            @RequestParam int year,
            @RequestParam int month,
            @PathVariable int courseId
    ) {
        return ResponseEntity.ok(managerService.findStudentsWithPaidFeesInCourse(Year.of(year), Month.of(month), courseId));
    }

    @GetMapping("/students/withUnpaidFeesInCourse")
    public ResponseEntity<List<User>> findStudentsWithUnpaidFeesInCourse(
            @RequestParam int year,
            @RequestParam int month,
            @PathVariable int courseId
    ) {
        return ResponseEntity.ok(managerService.findStudentsWithUnpaidFeesInCourse(Year.of(year), Month.of(month), courseId));
    }

    @GetMapping("/students/withPaidFeesInCenter")
    public ResponseEntity<List<User>> findStudentsWithPaidFeesInCenter(
            @RequestParam int year,
            @RequestParam int month,
            @PathVariable int centerId
    ) {
        return ResponseEntity.ok(managerService.findStudentsWithPaidFeesInCenter(Year.of(year), Month.of(month), centerId));
    }

    @GetMapping("/students/withUnpaidFeesInCenter")
    public ResponseEntity<List<User>> findStudentsWithUnpaidFeesInCenter(
            @RequestParam int year,
            @RequestParam int month,
            @PathVariable int centerId
    ) {
        return ResponseEntity.ok(managerService.findStudentsWithUnpaidFeesInCenter(Year.of(year), Month.of(month), centerId));
    }
}
