package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.enums.RoleDescription;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.model.UserCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCenterRepository extends JpaRepository<UserCenter, Integer> {

    @Query("SELECT uc.user " +
            "FROM UserCenter uc " +
            "JOIN uc.user u " +
            "JOIN u.role r " +
            "WHERE uc.center.id = :centerId " +
            "AND r = :role")
    List<User> getUsersInCenter(@Param("centerId") int centerId, @Param("role") RoleDescription role);
}
