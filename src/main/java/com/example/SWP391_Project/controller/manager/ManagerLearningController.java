package com.example.SWP391_Project.controller.manager;

import com.example.SWP391_Project.dto.CenterDto;
import com.example.SWP391_Project.dto.CourseDto;
import com.example.SWP391_Project.dto.SlotDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.CourseDetailResponse;
import com.example.SWP391_Project.response.StudentCoursesResponse;
import com.example.SWP391_Project.response.TeacherCoursesResponse;
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

import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;

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

    @PostMapping("/create")
    public ResponseEntity<Center> createCenter(@RequestBody @Valid CenterDto centerDto, HttpSession httpSession) {
        User managerInfo = (User) httpSession.getAttribute("authid");
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
    @GetMapping("/students/{centerId}")
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

    @GetMapping("/emptyRooms") //truyền vào centers
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
}
