package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Enrollment;
import com.example.SWP391_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
    List<User> findStudentsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId AND e.student.name LIKE %:studentName%")
    List<User> findStudentsByCourseIdAndName(@Param("courseId") Long courseId, @Param("studentName") String studentName);



}
