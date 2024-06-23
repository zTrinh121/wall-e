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
}
