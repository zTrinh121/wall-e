package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Course, Long> {
    //vvv
    @Query("SELECT c.name, c.id, c.code, c.description, c.startDate, c.endDate, COUNT(s), MAX(r.name), ctr.name " +
            "FROM Course c " +
            "JOIN c.slots s " +
            "JOIN s.room r " +
            "JOIN c.center ctr " +
            "WHERE c.teacher.id = :teacherId " +
            "GROUP BY c.name, c.id, c.code, c.description, c.startDate, c.endDate, ctr.name")
    List<Object[]> findCourseNamesByTeacherId(@Param("teacherId") Long teacherId);





//    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
//    List<User> findStudentsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT e.student FROM Enrollment e JOIN e.student u WHERE e.course.id = :courseId ORDER BY u.lastName")
    List<User> findStudentsByCourseId(@Param("courseId") Long courseId);


    //đ
    @Query("SELECT s.slotDate, s.slotStartTime, s.slotEndTime, DAYNAME(s.slotDate), r.name " +
            "FROM Slot s " +
            "JOIN s.room r " +
            "WHERE s.course.id = :courseId")
    List<Object[]> findScheduleByCourseId(@Param("courseId") Long courseId);




    @Query("SELECT r.id, r.course.id, r.student.id, r.type, r.value FROM Result r WHERE r.course.id = :courseId AND r.student.id = :studentId")
    List<Object[]> findResultsByCourseIdAndStudentId(@Param("courseId") Long courseId, @Param("studentId") Long studentId);


    // Xem toàn bộ thời khóa biểu của giáo viên đó
    @Query("SELECT s.slotDate, " +
            "DATE_FORMAT(s.slotStartTime, '%H:%i') AS slotStartTime, " +
            "DATE_FORMAT(s.slotEndTime, '%H:%i') AS slotEndTime, " +
            "c.name,c.id, r.name " +
            "FROM Slot s " +
            "JOIN s.course c " +
            "JOIN s.room r " +
            "WHERE c.teacher.id = :teacherId")
    List<Object[]> findScheduleByTeacherId(@Param("teacherId") Long teacherId);



    // Thời khóa biểu của giáo viên theo trung tâm
    @Query("SELECT s.slotDate, s.slotStartTime, s.slotEndTime, c.name, ctr.name " +
            "FROM Slot s JOIN s.course c JOIN c.center ctr " +
            "WHERE c.teacher.id = :teacherId AND ctr.id = :centerId")
    List<Object[]> findScheduleByTeacherIdAndCenterId(@Param("teacherId") Long teacherId, @Param("centerId") Long centerId);


    List<Feedback> findByTeacherId(int teacherId);


}
