package com.example.SWP391_Project.controller.teacher;

import com.example.SWP391_Project.dto.ApplyCenterDto;
import com.example.SWP391_Project.dto.FeedbackDto;
import com.example.SWP391_Project.dto.MaterialDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.StudentSlotRepository;
import com.example.SWP391_Project.response.NotificationResponse;
import com.example.SWP391_Project.service.TeacherService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentSlotRepository studentSlotRepository;

    //mmm
@GetMapping("/{teacherId}/courses")
public ResponseEntity<List<Map<String, Object>>> getCoursesByTeacherId(@PathVariable Long teacherId) {
    List<Map<String, Object>> courseNames = teacherService.getCourseNamesByTeacherId(teacherId);
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
// ssx
@GetMapping("/courses/{courseId}/schedule")
public ResponseEntity<List<Map<String, Object>>> getScheduleByCourseId(@PathVariable Long courseId) {
    List<Map<String, Object>> schedule = teacherService.getScheduleByCourseId(courseId);
    return ResponseEntity.ok(schedule);
}

// CRUD điểm
    @GetMapping("/courses/{courseId}/students/{studentId}/results")
public ResponseEntity<List<Map<String, Object>>> getResultsByCourseIdAndStudentId(@PathVariable Long courseId, @PathVariable Long studentId) {
    List<Map<String, Object>> results = teacherService.getResultsByCourseIdAndStudentId(courseId, studentId);
    return ResponseEntity.ok(results);
}

    @PatchMapping("/results/{resultId}")
    public ResponseEntity<Result> updateResult(@PathVariable Long resultId, @RequestBody Map<String, Object> updates) {
        Result updatedResult = teacherService.updateResult(resultId, updates);
        return ResponseEntity.ok(updatedResult);
    }
    @PutMapping("/results/{resultId}")
    public ResponseEntity<Result> updateResult(@PathVariable int resultId, @RequestBody Result result) {
        result.setId(resultId);
        Result updatedResult = teacherService.updateResult(result);
        return   ResponseEntity.ok(updatedResult);
    }


    @DeleteMapping("/results/{resultId}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long resultId) {
        teacherService.deleteResult(resultId);
        return ResponseEntity.noContent().build();
    }

    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó
    @GetMapping("/{teacherId}/schedule")
    public ResponseEntity<List<Map<String, Object>>> getScheduleByTeacherId(@PathVariable Long teacherId) {
        TimeZone timeZone = TimeZone.getDefault();
        System.out.println("------Time Zone: " + timeZone.getID());

        List<Map<String, Object>> schedule = teacherService.getScheduleByTeacherId(teacherId);
        System.out.println("-----------");
        System.out.println(schedule);
        return ResponseEntity.ok(schedule);
    }



    // Lấy ra toàn bộ thời khóa biểu của giáo viên đó theo trung tâm
    @GetMapping("/{teacherId}/schedule/{centerId}")
    public ResponseEntity<List<Object[]>> getScheduleByTeacherIdAndCenterId(@PathVariable Long teacherId, @PathVariable Long centerId) {
        List<Object[]> schedule = teacherService.getScheduleByTeacherIdAndCenterId(teacherId, centerId);
        return ResponseEntity.ok(schedule);
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

    // API to update PDF file
    @PutMapping("/{materialId}/pdf")
    public void updatePdfFile(
            @PathVariable int materialId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("subjectName") String subjectName,
            @RequestParam("materialsName") String materialsName,
            HttpSession httpSession) {
        User teacher = (User) httpSession.getAttribute("user");
        teacherService.updatePdfFile(materialId, file, subjectName, materialsName, teacher);
    }

    // API to delete PDF file
    @DeleteMapping("/{materialId}/pdf/delete")
    public void deletePdfFile(@PathVariable int materialId) {
        teacherService.deletePdfFile(materialId);
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
    @ResponseBody
    public IndividualNotification updateIndividualNotification(@PathVariable int notificationId, HttpSession session) {
        User student = (User) session.getAttribute("user");
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher is not found in the session!");
        }
        return teacherService.updateIndividualNotification(notificationId, student);
    }

    @PostMapping("/viewCenterNotification/update/{notificationId}")
    @ResponseBody
    public ViewCenterNotification updateViewCenterNotification(@PathVariable int notificationId,
                                                               HttpSession session) {
        User teacher = (User) session.getAttribute("user");
        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher is not found in the session!");
        }
        return teacherService.updateViewCenterNotification(notificationId, teacher);
    }

    @PostMapping("/viewSystemNotification/update/{notificationId}")
    @ResponseBody
    public ViewSystemNotification updateViewSystemNotification(@PathVariable int notificationId,
                                                               HttpSession session) {
        User teacher = (User) session.getAttribute("user");
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

    // -------------------------- FEEDBACK ---------------------------
    @PostMapping("/create-feedback-to-student")
    public ResponseEntity<Feedback> createFeedbackToTeacher(HttpSession session, @RequestBody FeedbackDto feedbackDto) {
        User actor = (User) session.getAttribute("user");
        if (actor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher is not found in the session!");
        }
        Feedback createdFeedback = teacherService.createFeedbackToStudent(actor, feedbackDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }

    @PutMapping("/update-feedback-to-student/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedbackToTeacher(@PathVariable("feedbackId") int id, @RequestBody FeedbackDto feedbackDto) {
        Feedback updatedFeedback = teacherService.updateFeedbackToStudent(id, feedbackDto);
        return ResponseEntity.ok(updatedFeedback);
    }

    @PostMapping("/courses/{courseId}/students/{studentId}/results")
    @ResponseBody
    public ResponseEntity<?> createResult(@PathVariable int courseId, @PathVariable int studentId,
                                          @RequestBody Map<String, Object> resultData) {
        try {
            int type = (Integer) resultData.get("type");
            int value = (Integer) resultData.get("value");
            Result result = teacherService.createResult(courseId, studentId, type, value);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create result: " + e.getMessage());
        }
    }


    // -------------------------- VIEW FEEDBACK ------------------------------
    // Lấy ra những feedback mà student gửi đến teacher này
    @GetMapping("/fetch-student-feedback")
    public ResponseEntity<List<Feedback>> fetchStudentFeedback(HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("authid");
        if (teacherId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher ID is not found in the session!");
        }

        List<Feedback> feedbacks = teacherService.fetchStudentFeedback(teacherId);
        
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // Lấy ra những feedback mà teacher này đã tạo ra
    @GetMapping("/view-feedback-to-student")
    public ResponseEntity<List<Feedback>> viewFeedbackToStudent(HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("authid");
        if (teacherId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher ID is not found in the session!");
        }

        List<Feedback> feedbacks = teacherService.viewFeedbackToStudent(teacherId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // API để view những feedbacks của các khóa học của một giáo viên
    @GetMapping("/{teacherId}/course-feedbacks")
    public ResponseEntity<List<Feedback>> getFeedbackToCourses(HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("authid");
        if (teacherId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher ID is not found in the session!");
        }

        List<Feedback> feedbacks = teacherService.fetchFeedbackToCourses(teacherId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    // API để view những feedbacks của một khóa học cụ thể mà giáo viên này đang dạy
    @GetMapping("/{courseId}/feedbacks")
    public ResponseEntity<List<Feedback>> getFeedbackToCertainCourse(@PathVariable int courseId) {
        List<Feedback> feedbacks = teacherService.fetchFeedbackToCertainCourse(courseId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }
    // -----------------------------------------------------------------------

    // -------------------- APPLY CENTER -----------------------------
    // tạo form để apply center
    @PostMapping("/create-applyCenter-form-to-manager")
    public ResponseEntity<ApplyCenter> applyForCenter(@RequestBody ApplyCenterDto applyCenterDto, HttpSession session) {
        User teacher = (User) session.getAttribute("user");
        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher is not found in the session!");
        }

        ApplyCenter applyCenter = teacherService.createApplyCenterForm(teacher, applyCenterDto);
        return ResponseEntity.ok(applyCenter);
    }

    //
    @GetMapping("view/slot/{slotId}")
    public List<Map<String, Object>> getStudentSlotBySlotId(@PathVariable int slotId) {
        return teacherService.getStudentSlotBySlotId(slotId);
    }

    @PostMapping("/attendance/certainSlot/{studentId}/{slotId}")
    public ResponseEntity<String> updateAttendanceStatus(@PathVariable int studentId, @PathVariable int slotId) {
        try {
            teacherService.updateAttendanceStatusBySlotId(studentId, slotId);
            return ResponseEntity.ok("Attendance status updated successfully for studentId: " + studentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating attendance status: " + e.getMessage());
        }
    }



    @PostMapping("/attendance/update")
    public ResponseEntity<String> updateAttendanceStatuss(@RequestParam int studentId, @RequestParam int slotId, @RequestParam boolean status) {
        try {
            teacherService.updateAttendanceStatus(studentId, slotId, status);
            return ResponseEntity.ok("Attendance status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating attendance status: " + e.getMessage());
        }
    }

    @GetMapping("/student-slots")
    public ResponseEntity<List<Map<String, Object>>> getStudentSlots(@RequestParam("courseId") int courseId,
                                                                     @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Map<String, Object>> attendanceList = teacherService.getAttendanceByCourseIdAndDate(courseId, date);
        return ResponseEntity.ok(attendanceList);
    }

    @PostMapping("/attendance/updateStatus")
    public ResponseEntity<String> updateAttendanceStatus(@RequestParam int studentId, @RequestParam int slotId, @RequestParam boolean status) {
        try {
            teacherService.updateAttendanceStatusBySlotIdAndStatus(studentId, slotId, status);
            return ResponseEntity.ok("Attendance status updated successfully for studentId: " + studentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating attendance status: " + e.getMessage());
        }
    }

    @GetMapping("/courses/{courseId}/studentss")
    public ResponseEntity<List<Map<String, Object>>> getStudentsByCourseIdAndDate(@PathVariable int courseId,
                                                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Map<String, Object>> students = teacherService.getStudentsByCourseIdAndDate(courseId, date);
        return ResponseEntity.ok(students);
    }

}
