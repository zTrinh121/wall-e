package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.model.UserCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserCenterRepository extends JpaRepository<UserCenter, Integer> {

    @Query("SELECT uc.user " +
            "FROM UserCenter uc " +
            "JOIN uc.user u " +
            "JOIN u.role r " +
            "WHERE uc.center.id = :centerId " +
            "AND r.description = 'TEACHER'" +
            "ORDER BY u.lastName")
    Optional<List<User>> getTeachersInCenter(@Param("centerId") int centerId);

    @Query("SELECT uc.user " +
            "FROM UserCenter uc " +
            "JOIN uc.user u " +
            "JOIN u.role r " +
            "WHERE uc.center.id = :centerId " +
            "AND r.description = 'STUDENT'" +
            "ORDER BY u.lastName")
    Optional<List<User>> getStudentsInCenter(@Param("centerId") int centerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserCenter uc " +
            "JOIN uc.user u " +
            "JOIN uc.center c " +
            "WHERE u.id = :userId " +
            "AND c.id = :centerId")
    void deleteUserInCenter(int userId, int centerId);

    @Query(value = "SELECT COUNT(*) AS teacher_count " +
            "FROM t16_user_center uc " +
            "INNER JOIN t14_user u ON uc.C16_USER_ID = u.C14_USER_ID " +
            "INNER JOIN t05_role r ON u.C14_ROLE_ID = r.C05_ROLE_ID " +
            "WHERE r.C05_ROLE_DESC = 'TEACHER' " +
            "AND uc.C16_CENTER_ID = :centerId",
            nativeQuery = true)
    int countTeachersByCenter(@Param("centerId") int centerId);

    @Query(value = "SELECT COUNT(*) AS teacher_count " +
            "FROM t16_user_center uc " +
            "INNER JOIN t14_user u ON uc.C16_USER_ID = u.C14_USER_ID " +
            "INNER JOIN t05_role r ON u.C14_ROLE_ID = r.C05_ROLE_ID " +
            "WHERE r.C05_ROLE_DESC = 'STUDENT' " +
            "AND uc.C16_CENTER_ID = :centerId",
            nativeQuery = true)
    int countStudentsByCenter(@Param("centerId") int centerId);

    @Query("SELECT uc FROM UserCenter uc WHERE uc.user.id = :userId AND uc.center.id = :centerId")
    UserCenter findByUserIdAndCenterId(@Param("userId") int userId, @Param("centerId") int centerId);

}
