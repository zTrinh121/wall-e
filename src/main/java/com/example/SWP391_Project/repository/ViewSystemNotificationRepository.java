package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.ViewSystemNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ViewSystemNotificationRepository extends JpaRepository<ViewSystemNotification, Integer> {

    Optional<List<ViewSystemNotification>> findBySystemNotification_Id(int id);

    @Query("SELECT vsn FROM ViewSystemNotification vsn " +
            "JOIN vsn.systemNotification sn " +
            "JOIN vsn.hasSeenBy u " +
            "WHERE sn.id = :notificationId " +
            "AND u.id = :userId")
    ViewSystemNotification findBySystemNotificationIdAndUserId(
            @Param("notificationId") int notificationId,
            @Param("userId") int userId);
}
