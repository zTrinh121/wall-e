package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.enums.PaymentMethodEnum;
import com.example.SWP391_Project.enums.PaymentStatus;
import com.example.SWP391_Project.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    Optional<List<Bill>> findByStatus(PaymentStatus status);

    @Query("SELECT b FROM Bill b JOIN b.paymentMethod pm " +
            "WHERE pm.paymentMethod = :paymentMethod " +
            "AND YEAR(b.createdAt) = :year AND MONTH(b.createdAt) = :month")
    Optional<List<Bill>> findByPaymentMethodAndCreatedAt(
            @Param("paymentMethod") PaymentMethodEnum paymentMethod,
            @Param("year") Year year,
            @Param("month") Month month
    );

    @Query("SELECT b FROM Bill b " +
            "WHERE b.status = :paymentStatus " +
            "AND YEAR(b.createdAt) = :year AND MONTH(b.createdAt) = :month")
    Optional<List<Bill>> findByStatusAndCreatedAt(
            @Param("paymentStatus") PaymentStatus paymentStatus,
            @Param("year") Year year,
            @Param("month") Month month
    );
}
