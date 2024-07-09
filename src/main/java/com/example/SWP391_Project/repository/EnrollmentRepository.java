package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Enrollment;
import com.example.SWP391_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
    List<User> findStudentsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId AND e.student.name LIKE %:studentName%")
    List<User> findStudentsByCourseIdAndName(@Param("courseId") Long courseId, @Param("studentName") String studentName);

    Optional<List<Enrollment>> findByCourse_Id(int id);

    @Query("SELECT " +
            "   s.username AS username, " +
            "   s.name AS name, " +
            "   s.phone AS phone, " +
            "   s.address AS address, " +
            "   s.dob AS dob, " +
            "   s.gender AS gender, " +
            "   s.email AS email, " +
            "   GROUP_CONCAT(c.name) AS courses, " +
            "   p.username AS parentUsername, " +
            "   p.name AS parentName, " +
            "   p.phone AS parentPhone, " +
            "   p.dob AS parentDob, " +
            "   p.gender AS parentGender, " +
            "   p.email AS parentEmail " +
            "FROM Enrollment e " +
            "JOIN e.student s " +
            "LEFT JOIN s.parent p " +
            "JOIN e.course c " +
            "WHERE s.id = :studentId AND c.center.id = :centerId " +
            "GROUP BY s.id, p.name, p.phone")
    List<Object[]> findStudentInfoAndCoursesByStudentId(@Param("studentId") int studentId,
                                                        @Param("centerId") int centerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    void deleteByStudentIdAndCourseId(int studentId, int courseId);

    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
    List<User> findStudentsByCourseId(int courseId);


}
