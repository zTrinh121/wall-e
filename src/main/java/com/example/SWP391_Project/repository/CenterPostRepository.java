package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.CenterPost;
import com.example.SWP391_Project.response.CenterPostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CenterPostRepository extends JpaRepository<CenterPost, Integer> {

    @Query("SELECT new com.example.SWP391_Project.response.CenterPostResponse(cp.id, " +
            "cp.title, cp.content, cp.status, cp.imagePath, cp.createdAt, cp.updatedAt, " +
            "c.name, c.address, c.email, " +
            "m.name, m.phone, m.address, m.email) " +
            "FROM CenterPost cp " +
            "LEFT JOIN cp.center c " +
            "LEFT JOIN c.manager m")
    List<CenterPostResponse> findAllCenterPostsAndManagerDetails();

    @Query("SELECT cp FROM CenterPost cp JOIN cp.center ct WHERE ct.id = :centerId")
    List<CenterPost> findCenterPostsByCenterId(@Param("centerId") int centerId);
}

