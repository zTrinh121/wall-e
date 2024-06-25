package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

    Optional<List<Material>> findByTeacher_Id(int teacherId);
}
