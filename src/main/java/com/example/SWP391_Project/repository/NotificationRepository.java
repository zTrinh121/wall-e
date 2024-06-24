package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.CenterNotification;
import com.example.SWP391_Project.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

//    // Truy vấn để lấy thông báo cá nhân cho một người dùng
//    @Query("SELECT n FROM Notification n WHERE n.sendToUser = :username")
//    List<Notification> findPersonalNotificationsByUsername(@Param("username") String username);
//
//    // Truy vấn để lấy thông báo trung tâm cho một trung tâm cụ thể
//    @Query("SELECT n FROM CenterNotification n JOIN n.center c WHERE c.centerId = :centerId")
//    List<CenterNotification> findNotificationsByCenterId(@Param("centerId") int centerId);
}
