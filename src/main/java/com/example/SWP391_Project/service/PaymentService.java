package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.PaymentDto;
import com.example.SWP391_Project.model.Bill;
import com.example.SWP391_Project.model.Enrollment;
import com.example.SWP391_Project.model.PaymentMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    public PaymentDto.VNPayResponse createVnPayPayment(HttpServletRequest request);
}
