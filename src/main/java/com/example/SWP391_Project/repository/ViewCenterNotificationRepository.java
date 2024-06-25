package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.ViewCenterNotification;
import com.example.SWP391_Project.model.ViewSystemNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ViewCenterNotificationRepository extends JpaRepository<ViewCenterNotification, Integer> {

    Optional<List<ViewCenterNotification>> findByCenterNotification_Id(int id);

    @Query("SELECT vcn FROM ViewCenterNotification vcn " +
            "JOIN vcn.centerNotification cn " +
            "JOIN vcn.hasSeenBy u " +
            "WHERE cn.id = :notificationId " +
            "AND u.id = :userId")
    ViewCenterNotification findByCenterNotificationIdAndUserId(
            @Param("notificationId") int notificationId,
            @Param("userId") int userId);
}
