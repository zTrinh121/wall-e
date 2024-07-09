package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.CenterNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CenterNotificationRepository extends JpaRepository<CenterNotification, Integer> {

    @Query("SELECT cn FROM CenterNotification cn JOIN cn.center c WHERE c.manager.id = :managerId")
    Optional<List<CenterNotification>> findAllByManagerId(@Param("managerId") int managerId);

    @Query("SELECT n FROM CenterNotification n JOIN UserCenter uc ON n.center.id = uc.center.id WHERE uc.user.id = :userId ORDER BY n.createdAt DESC")
    List<CenterNotification> findCenterNotificationsByUserId(@Param("userId") int userId);

    Optional<CenterNotification> findById(int id);

    Optional<List<CenterNotification>> findByCenter_Id(int id);

//    @Query("SELECT DISTINCT n FROM CenterNotification n " +
//            "JOIN UserCenter uc ON n.center.id = uc.center.id " +
//            "JOIN User u ON uc.user.id = u.id " +
//            "WHERE u.parent.id = :parentId " +
//            "ORDER BY n.createdAt DESC")
//    List<CenterNotification> findCenterNotificationsOfStudentByParentId(@Param("parentId") int parentId);

}