package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int id);
    // Thêm các phương thức tùy chỉnh nếu cần
}
