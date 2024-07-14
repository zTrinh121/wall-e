package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.enums.PaymentMethodEnum;
import com.example.SWP391_Project.enums.PaymentStatus;
import com.example.SWP391_Project.model.Bill;
import com.example.SWP391_Project.model.Enrollment;
import com.example.SWP391_Project.model.PaymentMethod;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.repository.BillRepository;
import com.example.SWP391_Project.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;

    @Override
    public Bill createBill(PaymentStatus status, Enrollment enrollment, PaymentMethod paymentMethod) {
        System.out.println("Enrolmetn insert vào bill: " + enrollment);
        Bill bill = Bill.builder()
                .status(status)
                .createdAt(new Date())
                .enrollment(enrollment)
                .paymentMethod(paymentMethod)
                .build();
        return billRepository.save(bill);
    }
}
