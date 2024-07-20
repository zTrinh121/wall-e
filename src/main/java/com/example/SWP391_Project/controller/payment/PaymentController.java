package com.example.SWP391_Project.controller.payment;

import com.example.SWP391_Project.dto.EnrollmentDto;
import com.example.SWP391_Project.dto.PaymentDto;
import com.example.SWP391_Project.enums.PaymentMethodEnum;
import com.example.SWP391_Project.enums.PaymentStatus;
import com.example.SWP391_Project.model.Bill;
import com.example.SWP391_Project.model.Enrollment;
import com.example.SWP391_Project.model.PaymentMethod;
import com.example.SWP391_Project.response.PaymentResponse;
import com.example.SWP391_Project.service.BillService;
import com.example.SWP391_Project.service.EnrollmentService;
import com.example.SWP391_Project.service.ParentService;
import com.example.SWP391_Project.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final EnrollmentService enrollmentService;
    private final BillService billService;

    @GetMapping("/vn-pay")
    @ResponseBody
    public PaymentResponse<PaymentDto.VNPayResponse> pay(HttpServletRequest request) {
        return new PaymentResponse<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }
    @GetMapping("/vn-pay-callback")
    public String payCallbackHandlerParent(HttpServletRequest request, HttpSession session) {
        String status = request.getParameter("vnp_ResponseCode");
        String amount = request.getParameter("vnp_Amount");
        String date = request.getParameter("vnp_PayDate");
        String transactionNo = request.getParameter("vnp_TransactionNo");
        String orderInfo = request.getParameter("vnp_OrderInfo");

        if (status.equals("00")) {
            EnrollmentDto enrollmentDto = new EnrollmentDto();
            int parentId = (int) session.getAttribute("authid");

            Enrollment enrollment = enrollmentService.enrollStudentInCourse(enrollmentDto, parentId, session);


            enrollment.setEnrollDate(new Date());

            PaymentMethod paymentMethod = PaymentMethod.builder()
                    .id(1)
                    .paymentMethod(PaymentMethodEnum.E_Banking)
                    .build();
            Bill bill = billService.createBill(PaymentStatus.Succeeded, enrollment, paymentMethod);
            String billUrl = "/bill?status=success&courseId=" + enrollment.getCourse().getId()+"&userId="+parentId+"&amount="+amount+"&date="+date+"&transactionNo="+transactionNo+"&orderInfo="+orderInfo;
            return "redirect:" + billUrl;
        } else if (status.equals("24")) {
            System.out.println("Hủy thanh toán");
            return "redirect:/billFail";
        } else {
            return "redirect:/billFail";
        }
    }


    @GetMapping("/auth/status")
    @ResponseBody
    public ResponseEntity<Boolean> isAuthenticated(HttpSession session) {
        Object authId = session.getAttribute("authid");
        System.out.println("ID người dùng: " + authId);
        boolean isAuthenticated = authId != null;
        return new ResponseEntity<>(isAuthenticated, HttpStatus.OK);
    }

    @PostMapping("/courseId")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveCourseIdToSession(@RequestBody CourseIdRequest courseIdRequest, HttpSession session) {
        int courseId = courseIdRequest.getCourseId();
        session.setAttribute("courseId", courseId);
        System.out.println("Course ID in session: " + courseId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "CourseId saved to session: " + courseId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/studentID")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveStudentIdToSession(@RequestBody StudentIdRequest studentIdRequest, HttpSession session) {
        int studentId = studentIdRequest.getStudentId();
        System.out.println("Id student co duoc luu ");
        System.out.println(studentId);
        session.setAttribute("studentId", studentId);
        System.out.println("Student ID in session: " + studentId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "StudentId saved to session: " + studentId);
        return ResponseEntity.ok(response);
    }

    static class CourseIdRequest {
        private int courseId;

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }
    }

    static class StudentIdRequest {
        private int studentId;

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }
    }

}