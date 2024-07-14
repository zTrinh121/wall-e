package com.example.SWP391_Project.service;

import com.example.SWP391_Project.enums.PaymentMethodEnum;
import com.example.SWP391_Project.enums.PaymentStatus;
import com.example.SWP391_Project.model.Bill;
import com.example.SWP391_Project.model.Enrollment;
import com.example.SWP391_Project.model.PaymentMethod;
import com.example.SWP391_Project.model.User;
import org.springframework.stereotype.Service;

@Service
public interface BillService {

    Bill createBill(PaymentStatus status, Enrollment enrollment, PaymentMethod paymentMethod);

}
