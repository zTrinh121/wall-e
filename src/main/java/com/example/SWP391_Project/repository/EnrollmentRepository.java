package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Enrollment;
import com.example.SWP391_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
    List<User> findStudentsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId AND e.student.name LIKE %:studentName%")
    List<User> findStudentsByCourseIdAndName(@Param("courseId") Long courseId, @Param("studentName") String studentName);

    Optional<List<Enrollment>> findByCourse_Id(int id);

    @Query("SELECT " +
            "   s.code AS code, " +
            "   s.name AS name, " +
            "   s.phone AS phone, " +
            "   s.address AS address, " +
            "   s.dob AS dob, " +
            "   s.gender AS gender, " +
            "   s.email AS email, " +
            "   GROUP_CONCAT(e.course.name) AS courses " +
            "FROM Enrollment e " +
            "JOIN e.student s " +
            "WHERE s.id = :studentId " +
            "GROUP BY s.id")
    List<Object[]> findStudentInfoAndCoursesByStudentId(int studentId);

}
