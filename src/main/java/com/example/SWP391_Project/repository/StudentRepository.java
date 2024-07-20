package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int id);
    // Thêm các phương thức tùy chỉnh nếu cần

    @Query("SELECT new map(s.student.id as studentId, s.slot.id as slotId, s.attendanceStatus as status) FROM StudentSlot s WHERE s.slot.course.id = :courseId")
    List<Map<String, Object>> findByCourseId(Long courseId);
}
