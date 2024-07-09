package com.example.SWP391_Project.controller.manager;

import com.example.SWP391_Project.dto.CenterDto;
import com.example.SWP391_Project.dto.CourseDto;
import com.example.SWP391_Project.dto.SlotDto;
import com.example.SWP391_Project.dto.SlotsDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.CourseDetailResponse;
import com.example.SWP391_Project.response.StudentCoursesResponse;
import com.example.SWP391_Project.response.TeacherCoursesResponse;
import com.example.SWP391_Project.response.TeacherSalaryResponse;
import com.example.SWP391_Project.service.ManagerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manager")
public class ManagerLearningController {

    @Autowired
    ManagerService managerService;


    // --------------------------- MANAGER CENTER ------------------------------
    @GetMapping("/centers")
    public ResponseEntity<List<Center>> getCenters(HttpSession httpSession) {
        String id =  httpSession.getAttribute("authid").toString();
        int managerId = (int)httpSession.getAttribute("authid");
        List<Center> centers = managerService.getCenters(managerId);
        if (centers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(centers, HttpStatus.OK);
    }

//    @GetMapping("/centerHome")
//    public ResponseEntity<List<CenterDetailResponse>> getCenterInfo() {
//        List<CenterDetailResponse> centers = managerService.getCenters();
//        return ResponseEntity.ok().body(centers);
//    }

    @PostMapping("/center/image/{centerId}")
    public ResponseEntity<?> uploadCenterImage(@PathVariable final int centerId, @PathVariable MultipartFile file) {
        managerService.uploadCenterImage(centerId, file);
        return ResponseEntity.ok("Upload successfully !");
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

    @PostMapping("center/create")
    public ResponseEntity<Center> createCenter(@RequestBody @Valid CenterDto centerDto, HttpSession httpSession) {
        User managerInfo = (User) httpSession.getAttribute("user");
        Center createdCenter = managerService.createCenter(centerDto, managerInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCenter);
    }

    @PutMapping("/center/update/{centerId}")
    public ResponseEntity<Center> updateCenterInfo(@PathVariable int centerId,
                                                   @RequestBody @Valid CenterDto centerDto) {
        Center center = managerService.updateCenterInfo(centerId, centerDto);
        if (center != null) {
            return ResponseEntity.ok(center);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/center/delete/{id}")
    public ResponseEntity<String> deleteCenter(@PathVariable int id) {
        try {
            boolean deleted = managerService.deleteCenter(id);
            if (deleted) {
                return ResponseEntity.ok("Deleted the center where ID = " + id);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // --------------------------------------------------------------------------


    // --------------------------- MANAGER COURSE ------------------------------
    //  Get courses list in a center
    @GetMapping("/courses/center/{centerId}")
    public ResponseEntity<List<Course>> getCoursesByCenterId(@PathVariable int centerId) {
        List<Course> courses = managerService.getCoursesByCenterId(centerId);
        if (courses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    //  Get detail course information
    @GetMapping("/course/{courseId}")
    public ResponseEntity<CourseDetailResponse> findCourseById(@PathVariable int courseId) {
        CourseDetailResponse courses = managerService.findCourseById(courseId);
        return ResponseEntity.ok(courses);
    }
    //  Create a new course in a center
    @PostMapping("/course/create")
    public Course createCourse(@RequestBody @Valid CourseDto courseDto) {
        return managerService.createCourse(courseDto);
    }
    //  Update course information in a center -- checkout in the inner code of impl
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
    //  Delete center
    @DeleteMapping("/course/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        try {
            boolean deleted = managerService.deleteCourse(id);
            if (deleted) {
                return ResponseEntity.ok("Deleted the course where ID = " + id);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataIntegrityViolationException e) {
            // Xử lý ngoại lệ khi không thể xóa vì có khóa ngoại tham chiếu
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // --------------------------------------------------------------------------

    // --------------------------- MANAGER TEACHER ------------------------------
    @GetMapping("/getTeachers/{centerId}")
    public ResponseEntity<List<User>> getTeachersInCenter(@PathVariable int centerId) {
        List<User> teachers = managerService.getTeachersInCenter(centerId);
        if (teachers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

//    @GetMapping("/view-applyCenter-form")
//    public ResponseEntity<List<ApplyCenter>> getTeachersInCenter(HttpSession session) {
//        Integer managerId = (Integer) session.getAttribute("authid");
//        if (managerId == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manager ID is not found in the session!");
//        }
//
//        List<ApplyCenter> forms = managerService.viewApplyCenterForm(managerId);
//        if (forms.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(forms, HttpStatus.OK);
//    }
    @GetMapping("/view-applyCenter-form/{centerId}")
    public ResponseEntity<List<ApplyCenter>> ViewTeachersApplyForm(@PathVariable int centerId) {
        List<ApplyCenter> forms = managerService.viewApplyCenterForm(centerId);
        if (forms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(forms, HttpStatus.OK);
    }

    @GetMapping("/view-detail-apply-form/{applyId}")
    public ResponseEntity<ApplyCenter> getApplyCenter(@PathVariable int applyId) {
        ApplyCenter applyCenter = managerService.viewApplyFormDetail(applyId);

        if (applyCenter != null) {
            return ResponseEntity.ok(applyCenter); // Return 200 OK with ApplyCenter object
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found
        }
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

    @DeleteMapping("/teacher/delete/{id}/{centerId}")
        public ResponseEntity<String> deleteTeacherInCenter(@PathVariable("id") int teacherId,
                                                            @PathVariable("centerId") int centerId) {
            boolean deleted = managerService.deleteTeacherInCenter(teacherId, centerId);
            if (deleted) {
                return ResponseEntity.ok("Delete the teacher where ID = " + teacherId);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    // --------------------------------------------------------------------------


    // --------------------------- MANAGER STUDENT ------------------------------
    //  Get students list in center
    @GetMapping("/students/{centerId}")
    public ResponseEntity<List<User>> getStudentsInCenter(@PathVariable int centerId) {
        List<User> students = managerService.getStudentsInCenter(centerId);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    //  View in view -> in course can view students list in that course
    @GetMapping("/students/course/{courseId}")
    public ResponseEntity<List<User>> getStudentsInCertainCourse(@PathVariable int courseId) {
        List<User> students = managerService.getStudentsInCertainCourse(courseId);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @DeleteMapping("/student/delete/{id}/{centerId}")
    public ResponseEntity<String> deleteStudentInCenter(@PathVariable("id") int studentId,
                                                        @PathVariable("centerId") int centerId) {
        boolean deleted = managerService.deleteStudentInCenter(studentId, centerId);
        if (deleted) {
            return ResponseEntity.ok("Delete the student where ID = " + studentId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // --------------------------------------------------------------------------

    // ---------------------------- ADD MORE ----------------------------------
    @GetMapping("/teacher-count/{centerId}")
    public ResponseEntity<Integer> countTeachersByCenter(@PathVariable int centerId) {
        int count = managerService.countTeachersByCenter(centerId);
        if (count == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(count);
    }

    @GetMapping("/student-count/{centerId}")
    public ResponseEntity<Integer> countStudentsByCenter(@PathVariable int centerId) {
        int count = managerService.countStudentsByCenter(centerId);
        if (count == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(count);
    }
    //  Number of courses
    @GetMapping("/course-count/{centerId}")
    public ResponseEntity<Integer> countCoursesByCenter(@PathVariable int centerId) {
        int count = managerService.countCourseByCenter(centerId);
        if (count == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(count);
    }

    @GetMapping("/teacher-detail/{teacherId}/{centerId}")
    public ResponseEntity<List<TeacherCoursesResponse>> getTeacherInfoAndCourses(@PathVariable("teacherId") int teacherId,
                                                                                 @PathVariable("centerId") int centerId) {
        List<TeacherCoursesResponse> teacherCourses = managerService.getTeacherInfoAndCourses(teacherId, centerId);
        if (teacherCourses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(teacherCourses);
    }

    @GetMapping("/student-detail/{studentId}/{centerId}")
    public ResponseEntity<List<StudentCoursesResponse>> getStudentInfoAndCourses(@PathVariable("studentId") int studentId,
                                                                                 @PathVariable("centerId") int centerId) {
        List<StudentCoursesResponse> studentCourses = managerService.getStudentInfoAndCourses(studentId, centerId);
        if (studentCourses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(studentCourses);
    }

    // ---------------------------- SLOTS ---------------------------------
    @GetMapping("/slots/byCenter/{centerId}")
    public List<Map<String, Object>> getSlotsByCenterId(@PathVariable int centerId) {
        return managerService.getSlotsByCenterId(centerId);
    }

    @GetMapping("/slots/bySlot/{slotId}")
    public Map<String, Object> getSlotsBySlotId(@PathVariable int slotId) {
        return managerService.getSlotsBySlotId(slotId);
    }

    @PostMapping("/slots/create")
    public ResponseEntity<Slot> createSlot(@RequestBody SlotDto slotDto) {
        try {
            Slot createdSlot = managerService.createSlot(slotDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSlot);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/slots/update/{slotId}")
    public ResponseEntity<Slot> updateSlot(@PathVariable int slotId, @RequestBody SlotDto slotDto) {
        try {
            Slot updatedSlot = managerService.updateSlot(slotId, slotDto);
            return ResponseEntity.ok(updatedSlot);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Or handle error as needed
        }
    }

    @DeleteMapping("/slots/delete/{slotId}")
    public ResponseEntity<Void> deleteSlot(@PathVariable int slotId) {
        try {
            managerService.deleteSlot(slotId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Or handle error as needed
        }
    }

    @PostMapping("/overallSlots/certainCourse/insert")
    public void insertSlots(@RequestBody List<SlotsDto> slotsDtos,
                            @RequestParam String courseCode,
                            @RequestParam String roomName) {
        managerService.insertSlotsAndStudentSlots(slotsDtos, courseCode, roomName);
    }
    // -------------------------------------------------------------------------------------

    @GetMapping("/teacher-salaries")
    public List<TeacherSalaryResponse> getTeacherSalaries(@RequestParam int month, @RequestParam int year, @RequestParam Long centerId) {
        return managerService.getTeacherSalaries(month, year, centerId);
    }

//    @GetMapping("/total-teacher-salary")
//    public List<Map<String, Object>> getTotalTeacherSalary(@RequestParam int month, @RequestParam int year, @RequestParam Long centerId) {
//        return managerService.getTotalTeacherSalary(month, year, centerId);
//    }

    @GetMapping("/monthly-revenue")
    public Map<String, Object> getMonthlyRevenue(@RequestParam int month, @RequestParam int year, @RequestParam Long centerId) {
        return managerService.getMonthlyRevenue(month, year, centerId);
    }

    @GetMapping("/monthly-profit")
    public Map<String, Object> getMonthlyProfit(
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam Long centerId) {
        return managerService.getMonthlyProfit(month, year, centerId);
    }

    @GetMapping("/total-teacher-salary")
    public List<Map<String, Object>> getTotalTeacherSalary(@RequestParam int year, @RequestParam Long centerId) {
        return managerService.getTotalTeacherSalaryForYear(year, centerId);
    }

}
