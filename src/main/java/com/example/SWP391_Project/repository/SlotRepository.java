package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Integer> {

    List<Slot> findSlotByCourse_Id(int id);

}
