package com.example.SWP391_Project.controller.teacher;

import com.example.SWP391_Project.dto.MaterialDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.service.TeacherService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/{teacherId}/courses")
    public ResponseEntity<List<String>> getCoursesByTeacherId(@PathVariable Long teacherId) {
        List<String> courseNames = teacherService.getCourseNamesByTeacherId(teacherId);
        return ResponseEntity.ok(courseNames);
    }

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<User>> getStudentsByCourseId(@PathVariable Long courseId) {
        List<User> students = teacherService.getStudentsByCourseId(courseId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/courses/{courseId}/students/search")
    public ResponseEntity<List<User>> searchStudentsByCourseIdAndName(@PathVariable Long courseId, @RequestParam String studentName) {
        List<User> students = teacherService.searchStudentsByCourseIdAndName(courseId, studentName);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/courses/{courseId}/schedule")
    public ResponseEntity<List<Object[]>> getScheduleByCourseId(@PathVariable Long courseId) {
        List<Object[]> schedule = teacherService.getScheduleByCourseId(courseId);
        return ResponseEntity.ok(schedule);
    }


    // CRUD điểm
    @GetMapping("/courses/{courseId}/students/{studentId}/results")
    public ResponseEntity<List<Object[]>> getResultsByCourseIdAndStudentId(@PathVariable Long courseId, @PathVariable Long studentId) {
        List<Object[]> results = teacherService.getResultsByCourseIdAndStudentId(courseId, studentId);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/courses/{courseId}/students/{studentId}/results")
    public ResponseEntity<Result> createResult(@RequestBody Result result) {
        Result createdResult = teacherService.createResult(result);
        return ResponseEntity.ok(createdResult);
    }

    @PutMapping("/results/{resultId}")
    public ResponseEntity<Result> updateResult(@PathVariable int resultId, @RequestBody Result result) {
        result.setId(resultId);
        Result updatedResult = teacherService.updateResult(result);
        return ResponseEntity.ok(updatedResult);
    }

    @DeleteMapping("/results/{resultId}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long resultId) {
        teacherService.deleteResult(resultId);
        return ResponseEntity.noContent().build();
    }

    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó
    @GetMapping("/{teacherId}/schedule")
    public ResponseEntity<List<Object[]>> getScheduleByTeacherId(@PathVariable Long teacherId) {
        List<Object[]> schedule = teacherService.getScheduleByTeacherId(teacherId);
        return ResponseEntity.ok(schedule);
    }

    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó theo trung tâm
    @GetMapping("/{teacherId}/schedule/{centerId}")
    public ResponseEntity<List<Object[]>> getScheduleByTeacherIdAndCenterId(@PathVariable Long teacherId, @PathVariable Long centerId) {
        List<Object[]> schedule = teacherService.getScheduleByTeacherIdAndCenterId(teacherId, centerId);
        return ResponseEntity.ok(schedule);
    }

//    // Lấy ra 3 loại thông báo
//    @GetMapping("/notifications/private")
//    public ResponseEntity<List<PrivateNotification>> getAllPrivateNotifications() {
//        List<PrivateNotification> notifications = teacherService.getAllPrivateNotifications();
//        return ResponseEntity.ok(notifications);
//    }
//
//    @GetMapping("/notifications/public")
//    public ResponseEntity<List<PublicNotification>> getAllPublicNotifications() {
//        List<PublicNotification> notifications = teacherService.getAllPublicNotifications();
//        return ResponseEntity.ok(notifications);
//    }
//
//    @GetMapping("/notifications/system")
//    public ResponseEntity<List<SystemNotification>> getAllSystemNotifications() {
//        List<SystemNotification> notifications = teacherService.getAllSystemNotifications();
//        return ResponseEntity.ok(notifications);
//    }
//
//    // in ra cả 3
//    @GetMapping("/notifications/all")
//    public ResponseEntity<NotificationResponse> getAllNotifications() {
//        NotificationResponse notifications = teacherService.getAllNotifications();
//        return ResponseEntity.ok(notifications);
//    }
//
//    // tạo thông báo private
//    @PostMapping(value = "/notifications/private", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<Void> addPrivateNotification(@RequestBody PrivateNotification notification) {
//        teacherService.addPrivateNotification(notification);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("PDF/File/upload")
    public ResponseEntity<?> uploadMaterialPdf(
            @RequestPart("file") MultipartFile file,
            @RequestPart("materialDto") @Valid MaterialDto materialDto,
            HttpSession httpSession) {

        try {
            User teacher = (User) httpSession.getAttribute("authid");

            teacherService.uploadPdfFile(file, materialDto, teacher);

            return ResponseEntity.ok().body("Material uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload material: " + e.getMessage());
        }
    }

    @GetMapping("/allMaterials")
    public ResponseEntity<List<Material>> getAllMaterials() {
        List<Material> materials = teacherService.getAllMaterials();
        if (materials.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(materials);
        }
    }

    @GetMapping("materials")
    public ResponseEntity<List<Material>> getMaterialsByTeacherId(HttpSession session) {
        int teacherId = (int) session.getAttribute("authid");
        List<Material> materials = teacherService.getMaterialsByTeacherId(teacherId);
        if (materials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(materials, HttpStatus.OK);
    }





}
