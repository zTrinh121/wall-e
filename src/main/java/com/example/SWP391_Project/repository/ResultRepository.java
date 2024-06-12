package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query("SELECT r FROM Result r WHERE r.course.id = :courseId AND r.student.id = :studentId")
    List<Result> findByCourseIdAndStudentId(@Param("courseId") Long courseId, @Param("studentId") Long studentId);
}
