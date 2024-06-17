package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.config.VNPAYConfig;
import com.example.SWP391_Project.dto.PaymentDto;
import com.example.SWP391_Project.enums.PaymentStatus;
import com.example.SWP391_Project.model.Bill;
import com.example.SWP391_Project.model.Enrollment;
import com.example.SWP391_Project.model.PaymentMethod;
import com.example.SWP391_Project.repository.BillRepository;
import com.example.SWP391_Project.service.PaymentService;
import com.example.SWP391_Project.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final VNPAYConfig vnPayConfig;
    @Override
    public PaymentDto.VNPayResponse createVnPayPayment(HttpServletRequest request) {

        HttpSession session = request.getSession();
        int courseId = (int) session.getAttribute("courseId");
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDto.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

}
