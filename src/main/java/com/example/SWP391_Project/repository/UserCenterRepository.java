package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.enums.RoleDescription;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.model.UserCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCenterRepository extends JpaRepository<UserCenter, Integer> {

    @Query("SELECT uc.user " +
            "FROM UserCenter uc " +
            "JOIN uc.user u " +
            "JOIN u.role r " +
            "WHERE uc.center.id = :centerId " +
            "AND r.description = 'TEACHER'")
    Optional<List<User>> getTeachersInCenter(@Param("centerId") int centerId);

    @Query("SELECT uc.user " +
            "FROM UserCenter uc " +
            "JOIN uc.user u " +
            "JOIN u.role r " +
            "WHERE uc.center.id = :centerId " +
            "AND r.description = 'STUDENT'")
    Optional<List<User>> getStudentsInCenter(@Param("centerId") int centerId);
}
