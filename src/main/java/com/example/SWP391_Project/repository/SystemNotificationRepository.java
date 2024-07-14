package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.SystemNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemNotificationRepository extends JpaRepository<SystemNotification, Integer> {

    Optional<SystemNotification> findById(int id);

}
