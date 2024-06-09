package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Center;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.response.CenterDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center, Integer> {

    @Query("SELECT new com.example.SWP391_Project.response.CenterDetailResponse(c.id, c.code, c.name, c.description, c.address, c.phone, c.email, c.regulation, c.createdAt, c.status, c.imagePath, m.name, m.phone, m.address, m.email) " +
            "FROM Center c LEFT JOIN c.manager m")
    List<CenterDetailResponse> findAllCentersWithManagerDetails();

    Optional<List<Center>> findByManager(User user);

    Optional<Center> findByCode(String code);
}
