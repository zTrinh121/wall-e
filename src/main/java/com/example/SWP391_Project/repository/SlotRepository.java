package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Integer> {

    Optional<List<Slot>> findSlotByCourse_Id(int id);

    Optional<List<Slot>> findBySlotDate(Date date);

}
