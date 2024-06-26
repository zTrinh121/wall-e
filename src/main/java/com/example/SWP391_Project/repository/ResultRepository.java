package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query("SELECT r FROM Result r WHERE r.course.id = :courseId AND r.student.id = :studentId")
    List<Result> findByCourseIdAndStudentId(@Param("courseId") Long courseId, @Param("studentId") Long studentId);

    @Query("SELECT r FROM Result r JOIN r.student u WHERE u.parent.id = :parentId")
    Optional<List<Result>> findAllResultsWithParentUserId(@Param("parentId") int parentId);

    @Query("SELECT r FROM Result r WHERE r.id = :id")
    Result findResultById(@Param("id") Long id);
}
