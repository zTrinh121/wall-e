package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {

    @Query("SELECT r FROM Result r JOIN r.student u WHERE u.parent.id = :parentId")
    List<Result> findAllResultsWithParentUserId(@Param("parentId") int parentId);
}
