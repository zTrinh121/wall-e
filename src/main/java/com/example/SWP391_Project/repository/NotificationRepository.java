//package com.example.SWP391_Project.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface NotificationRepository extends JpaRepository<Notification, Integer> {
//
////    // Truy vấn để lấy thông báo cá nhân cho một người dùng
////    @Query("SELECT n FROM Notification n WHERE n.sendToUser = :username")
////    List<Notification> findPersonalNotificationsByUsername(@Param("username") String username);
////
////    // Truy vấn để lấy thông báo trung tâm cho một trung tâm cụ thể
////    @Query("SELECT n FROM CenterNotification n JOIN n.center c WHERE c.centerId = :centerId")
////    List<CenterNotification> findNotificationsByCenterId(@Param("centerId") int centerId);
//}
