package com.example.SWP391_Project.model;

import com.example.SWP391_Project.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "t08_bill")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C08_BILL_ID")
    int id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "C08_STATUS", nullable = false)
    PaymentStatus status;

    @Column(name = "C08_CREATED_AT", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    Date createdAt;

    // chỗ này sai
    // bill và enrollment là one-to-one
    @ManyToOne
    @JoinColumn(name = "C08_ENROLLMENT_ID")
    @JsonManagedReference
    Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "C08_PMETHOD_ID")
    @JsonManagedReference
    PaymentMethod paymentMethod;
}
