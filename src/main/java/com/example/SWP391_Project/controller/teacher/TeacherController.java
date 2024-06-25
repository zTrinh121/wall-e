package com.example.SWP391_Project.controller.teacher;

import com.example.SWP391_Project.dto.MaterialDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.service.TeacherService;
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
@RequestMapping("/api/teacher")
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

    // ----------------------- MATERIALS -------------------------
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

    // --------------------------- NOTIFICATION -------------------------
    @GetMapping("notifications/all")
    public ResponseEntity<NotificationResponse> getAllNotifications(HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("authid");
        if (teacherId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher ID is not found in the session!");
        }
        NotificationResponse notificationResponse = teacherService.getAllNotifications(teacherId);
        return ResponseEntity.ok(notificationResponse);
    }

    @PatchMapping("/individualNotification/update/{notificationId}")
    public IndividualNotification updateIndividualNotification(@PathVariable int notificationId) {
        return teacherService.updateIndividualNotification(notificationId);
    }

    @PostMapping("/viewCenterNotification/update/{notificationId}")
    public ViewCenterNotification updateViewCenterNotification(@PathVariable int notificationId,
                                                               HttpSession session) {
        User teacher = (User) session.getAttribute("authid");
        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher is not found in the session!");
        }
        return teacherService.updateViewCenterNotification(notificationId, teacher);
    }

    @PostMapping("/viewSystemNotification/update/{notificationId}")
    public ViewSystemNotification updateViewSystemNotification(@PathVariable int notificationId,
                                                               HttpSession session) {
        User teacher = (User) session.getAttribute("authid");
        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher is not found in the session!");
        }
        return teacherService.updateViewSystemNotification(notificationId, teacher);
    }

    @GetMapping("/centerNotification/{centerNotificationId}/check")
    public ResponseEntity<Boolean> checkHasSeenCenterNotification(
            @PathVariable int centerNotificationId,
            HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("authid");
        if (teacherId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher ID is not found in the session!");
        }
        Boolean hasSeen = teacherService.checkHasSeenCenterNotification(centerNotificationId, teacherId);
        return ResponseEntity.ok(hasSeen);
    }

    // API endpoint for checking if a system notification has been seen by a student
    @GetMapping("/systemNotification/{systemNotificationId}/check")
    public ResponseEntity<Boolean> checkHasSeenSystemNotification(
            @PathVariable int systemNotificationId,
            HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("authid");
        if (teacherId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher ID is not found in the session!");
        }
        Boolean hasSeen = teacherService.checkHasSeenSystemNotification(systemNotificationId, teacherId);
        return ResponseEntity.ok(hasSeen);
    }

}
