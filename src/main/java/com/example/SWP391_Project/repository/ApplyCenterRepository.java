package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.ApplyCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplyCenterRepository extends JpaRepository<ApplyCenter, Integer> {

    @Query("SELECT ac FROM ApplyCenter ac JOIN ac.center ct WHERE ct.manager.id = :managerId")
    List<ApplyCenter> findApplyCentersByManagerId(@Param("managerId") int managerId);

}
