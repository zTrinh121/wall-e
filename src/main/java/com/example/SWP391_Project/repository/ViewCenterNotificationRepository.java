package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.ViewCenterNotification;
import com.example.SWP391_Project.model.ViewSystemNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ViewCenterNotificationRepository extends JpaRepository<ViewCenterNotification, Integer> {

    Optional<List<ViewCenterNotification>> findByCenterNotification_Id(int id);
}
