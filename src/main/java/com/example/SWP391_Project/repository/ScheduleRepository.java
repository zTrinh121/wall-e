//package com.example.SWP391_Project.repository;
//
//import com.example.SWP391_Project.model.Schedule;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
//
//    @Query("SELECT s FROM Schedule s WHERE s.course.id = :courseId")
//    List<Schedule> findSchedulesByCourseId(@Param("courseId") Long courseId);
//}
