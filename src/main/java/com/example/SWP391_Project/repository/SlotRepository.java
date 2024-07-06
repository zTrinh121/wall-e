package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Slot;
import com.example.SWP391_Project.response.SlotResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Integer> {

    Optional<List<Slot>> findByCourse_Id(int id);

//    List<Slot> findByStudentId(int studentId);

    Optional<List<Slot>> findBySlotDate(Date date);

    @Query("SELECT s FROM Slot s " +
            "JOIN StudentSlot ss ON s.id = ss.slot.id " +
            "JOIN User u ON ss.student.id = u.id " +
            "WHERE u.parent.id = :parentId")
    Optional<List<Slot>> findAllSlotsWithParentUserId(@Param("parentId") int parentId);

    @Query("SELECT new com.example.SWP391_Project.response.SlotResponse(s.id, s.slotDate, s.slotStartTime, s.slotEndTime, c.id, c.name, ss.attendanceStatus) " +
            "FROM Slot s " +
            "JOIN s.course c " +
            "JOIN StudentSlot ss ON ss.slot.id = s.id " +
            "WHERE ss.student.id = :studentId AND c.id = :courseId")
    List<SlotResponse> findSlotsByStudentIdAndCourseId(@Param("studentId") int studentId,
                                                       @Param("courseId") int courseId);

    @Query("SELECT s FROM Slot s JOIN s.studentSlots ss WHERE ss.student.id = :studentId")
    Optional<List<Slot>> findSlotByStudent_Id(int studentId);

}
