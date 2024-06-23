package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.enums.PaymentStatus;
import com.example.SWP391_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);

    User findByEmailAndCode(String email, String code);
    Optional<User> findByCode(String code);

    List<User> findByRole_Id(int roleId);
    long countByRole_Id(int roleId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    void updateUserStatus(@Param("id") int id, @Param("status") boolean status);

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN u.enrollments e " +
            "JOIN e.course c " +
            "JOIN e.bills b " +
            "WHERE b.status = :status " +
            "AND c.center.id = :centerId " +
            "AND YEAR(b.createdAt) != :year " +
            "AND MONTH(b.createdAt) != :month " +
            "AND u.id NOT IN (" +
            "    SELECT DISTINCT u2.id FROM User u2 " +
            "    JOIN u2.enrollments e2 " +
            "    JOIN e2.bills b2 " +
            "    WHERE YEAR(b2.createdAt) <= :year AND MONTH(b2.createdAt) <= :month)")
    Optional<List<User>> findStudentsWithPaidFeesInCenter(
            @Param("status") PaymentStatus status,
            @Param("year") Year year,
            @Param("month") Month month,
            @Param("centerId") int centerId
    );

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN u.enrollments e " +
            "JOIN e.bills b " +
            "WHERE b.status = :status " +
            "AND e.course.id = :courseId " +
            "AND YEAR(b.createdAt) != :year " +
            "AND MONTH(b.createdAt) != :month " +
            "AND u.id NOT IN (" +
            "    SELECT DISTINCT u2.id FROM User u2 " +
            "    JOIN u2.enrollments e2 " +
            "    JOIN e2.bills b2 " +
            "    WHERE YEAR(b2.createdAt) <= :year AND MONTH(b2.createdAt) <= :month)")
    Optional<List<User>> findStudentsWithPaidFeesInCourse(
            @Param("status") PaymentStatus status,
            @Param("year") Year year,
            @Param("month") Month month,
            @Param("courseId") int courseId
    );

    @Query("SELECT DISTINCT u FROM User u WHERE u.parent.id = :parentId ")
    User findStudentsByParentId(@Param("parentId") int parentId);

    Optional<User> findById(int id);

    @Query("SELECT u FROM User u WHERE u.parent.id = :parentId")
    List<User> getStudentsByParentId(int parentId);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsernamee(@Param("username") String username);
///////////////////////////////////////////////
}
