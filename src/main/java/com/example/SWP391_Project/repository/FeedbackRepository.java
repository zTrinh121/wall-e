package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Feedback;
import com.example.SWP391_Project.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("SELECT f FROM Feedback f " +
            "JOIN f.sendToUser u " +
            "JOIN u.role r " +
            "WHERE r.description = com.example.SWP391_Project.enums.RoleDescription.TEACHER")
    Optional<List<Feedback>> findFeedbacksToTeacher();

    Optional<List<Feedback>> findByActor_Id(int userId);

    Optional<List<Feedback>> findBySendToUser_Id(int userId);

    @Query("SELECT f FROM Feedback f " +
            "WHERE f.sendToUser.parent.id = :parentId ")
    Optional<List<Feedback>> getFeedbacksTeacherSendToStudent(@Param("parentId") int parentId);

//    Optional<List<Feedback>> findFeedbacksToCourse(int courseId);
}
