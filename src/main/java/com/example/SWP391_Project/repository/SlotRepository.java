package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Integer> {

    Optional<List<Slot>> findSlotByCourse_Id(int id);

    List<Slot> findByStudentId(int studentId);

    Optional<List<Slot>> findBySlotDate(Date date);

    @Query("SELECT s FROM Slot s " +
            "JOIN StudentSlot ss ON s.id = ss.slot.id " +
            "JOIN User u ON ss.student.id = u.id " +
            "WHERE u.parent.id = :parentId")
    List<Slot> findAllSlotsWithParentUserId(@Param("parentId") int parentId);

}
