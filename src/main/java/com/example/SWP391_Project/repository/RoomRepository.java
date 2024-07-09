package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByName(String name);

    @Query("SELECT COUNT(s) > 0 " +
            "FROM Slot s JOIN s.room r " +
            "WHERE r = :room " +
            "AND s.slotStartTime < :slotEndTime " +
            "AND s.slotEndTime > :slotStartTime")
    boolean existsSlotOccurring(@Param("room") Room room,
                                @Param("slotStartTime") Date slotStartTime,
                                @Param("slotEndTime") Date slotEndTime);

    @Query("SELECT r " +
            "FROM Room r " +
            "WHERE NOT EXISTS (" +
            "   SELECT s " +
            "   FROM Slot s " +
            "   WHERE s.room = r " +
            "   AND s.slotStartTime < :slotEndTime " +
            "   AND s.slotEndTime > :slotStartTime " +
            "   AND s.course.center.id = :centerId" +
            ")")
    List<Room> findEmptyRooms(
            @Param("slotStartTime") Date slotStartTime,
            @Param("slotEndTime") Date slotEndTime,
            @Param("centerId") int centerId);

    Optional<List<Room>> findByCenter_Id(int id);

    @Query("SELECT r FROM Room r WHERE UPPER(r.name) = UPPER(:roomName) AND r.center.id = :centerId")
    Optional<Room> findByRoomNameAndCenterId(@Param("roomName") String roomName,
                                             @Param("centerId") int centerId);


}
