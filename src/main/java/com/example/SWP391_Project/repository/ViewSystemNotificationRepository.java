package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.ViewSystemNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ViewSystemNotificationRepository extends JpaRepository<ViewSystemNotification, Integer> {

    Optional<List<ViewSystemNotification>> findBySystemNotification_Id(int id);
}
