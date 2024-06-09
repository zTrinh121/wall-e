package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c.name FROM Course c WHERE c.teacher.id = :teacherId")
    List<String> findCourseNamesByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
    List<User> findStudentsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT s.date, s.startTime, s.endTime, DAYNAME(s.date) FROM Schedule s WHERE s.course.id = :courseId")
    List<Object[]> findScheduleByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT r.type, r.value FROM Result r WHERE r.course.id = :courseId AND r.student.id = :studentId")
    List<Object[]> findResultsByCourseIdAndStudentId(@Param("courseId") Long courseId, @Param("studentId") Long studentId);


    // Xem toàn bộ thời khóa biểu của giáo viên đó
    @Query("SELECT s.slotDate, s.slotStartTime, s.slotEndTime, c.name " +
            "FROM Slot s JOIN s.course c " +
            "WHERE c.teacher.id = :teacherId")
    List<Object[]> findScheduleByTeacherId(@Param("teacherId") Long teacherId);

    // Thời khóa biểu của giáo viên theo trung tâm
    @Query("SELECT s.slotDate, s.slotStartTime, s.slotEndTime, c.name, ctr.name " +
            "FROM Slot s JOIN s.course c JOIN c.center ctr " +
            "WHERE c.teacher.id = :teacherId AND ctr.id = :centerId")
    List<Object[]> findScheduleByTeacherIdAndCenterId(@Param("teacherId") Long teacherId, @Param("centerId") Long centerId);

    // Lấy ra cả 3 loại thông báo

    @Query("SELECT p FROM PrivateNotification p")
    List<PrivateNotification> findAllPrivateNotifications();

    @Query("SELECT p FROM PublicNotification p")
    List<PublicNotification> findAllPublicNotifications();

    @Query("SELECT s FROM SystemNotification s")
    List<SystemNotification> findAllSystemNotifications();

}
