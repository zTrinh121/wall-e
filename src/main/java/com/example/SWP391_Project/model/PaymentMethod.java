package com.example.SWP391_Project.model;

import com.example.SWP391_Project.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "t04_payment_method")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C04_PMETHOD_ID")
    int id;

    @Column(name = "C04_PMETHOD_DESC")
    PaymentMethodEnum paymentMethod;
}
