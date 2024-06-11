package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    // Thêm các phương thức tùy chỉnh nếu cần
}
