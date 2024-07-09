package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    Optional<PaymentMethod> findById(int id);
}
