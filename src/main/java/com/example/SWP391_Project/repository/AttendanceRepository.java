package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("SELECT a FROM Attendance a " +
            "JOIN a.student u " +
            "JOIN a.slot s " +
            "WHERE u.parent.id = :parentId")
    List<Attendance> findAllAttendanceByParentId(@Param("parentId") int parentId);
}
