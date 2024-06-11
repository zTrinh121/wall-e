package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
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

}
