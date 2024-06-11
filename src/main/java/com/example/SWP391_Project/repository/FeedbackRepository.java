package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("SELECT f FROM Feedback f " +
            "JOIN f.sendToUser u " +
            "JOIN u.role r " +
            "WHERE r.description = 'TEACHER'")
    Optional<List<Feedback>> findFeedbacksToTeacher();
}
