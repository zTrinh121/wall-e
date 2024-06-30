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

    Optional<List<Feedback>> findBySendToCourse_Id(int courseId);

    @Query("SELECT f FROM Feedback f " +
            "WHERE f.sendToUser.parent.id = :parentId ")
    Optional<List<Feedback>> getFeedbacksTeacherSendToStudent(@Param("parentId") int parentId);

    @Query("SELECT f FROM Feedback f WHERE f.actor.id = :actorId AND f.sendToUser IS NULL")
    Optional<List<Feedback>> viewFeedbacksToCourse(int actorId);

    @Query("SELECT f FROM Feedback f WHERE f.actor.id = :actorId AND f.sendToCourse IS NULL")
    Optional<List<Feedback>> viewFeedbacksToTeacher(int actorId);

    @Query("SELECT f FROM Feedback f JOIN f.sendToCourse c WHERE c.teacher.id = :teacherId")
    Optional<List<Feedback>> teacherViewCourseFeedback(int teacherId);

    @Query("SELECT f FROM Feedback f \n" +
            "JOIN f.sendToCourse c \n" +
            "JOIN c.center ct \n" +
            "WHERE ct.manager.id = :managerId")
    Optional<List<Feedback>> managerViewCourseFeedbacks(int managerId);

    @Query(value = "SELECT DISTINCT f.* " +
            "FROM t06_feedback f " +
            "JOIN t14_user u ON f.C06_SEND_TO_USER = u.C14_USER_ID " +
            "JOIN t05_role r ON r.C05_ROLE_ID = u.C14_ROLE_ID " +
            "JOIN t16_user_center uc ON f.C06_SEND_TO_USER = uc.C16_USER_ID " +
            "JOIN t03_center ct ON uc.C16_CENTER_ID = ct.C03_CENTER_ID " +
            "WHERE r.C05_ROLE_DESC = 'TEACHER' AND ct.C03_MANAGER_ID = ?1",
            nativeQuery = true)
    Optional<List<Feedback>> managerViewTeacherFeedbacks(int managerId);

}
