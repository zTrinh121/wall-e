package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.Course;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.response.CourseDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    Optional<List<Course>> findByCenter_Id(int centerId);

    @Query("SELECT NEW com.example.SWP391_Project.response.CourseDetailResponse(" +
            "c.name, c.code, c.description, c.startDate, c.endDate, " +
            "c.amountOfStudents, c.courseFee, " +
            "s.name, s.description, " +
            "u.name, u.phone, u.address, u.dob, u.gender, u.email) " +
            "FROM Course c " +
            "JOIN c.subject s " +
            "JOIN c.teacher u " +
            "WHERE c.id = :courseId")
    CourseDetailResponse getCourseDetailByCourseId(int courseId);

    @Query("SELECT DISTINCT ss.student FROM StudentSlot ss " +
            "JOIN ss.slot s " +
            "JOIN s.course c " +
            "WHERE c.id = :courseId")
    Optional<List<User>> getStudentsInCertainCourse(int courseId);

    Optional<Course> findByCode(String code);

    @Query("SELECT e.course FROM Enrollment e " +
            "JOIN e.student u " +
            "WHERE u.parent.id = :parentId")
    Optional<List<Course>> findAllCoursesWithParentUserId(@Param("parentId") int parentId);

    @Query(value = "SELECT COUNT(c) FROM Course c WHERE c.center.id = :centerId GROUP BY c.center.id")
    int countCourseByCenter(@Param("centerId") int centerId);

    @Query("SELECT " +
            "   c.teacher.code AS code, " +
            "   c.teacher.name AS name, " +
            "   c.teacher.phone AS phone, " +
            "   c.teacher.address AS address, " +
            "   c.teacher.dob AS dob, " +
            "   c.teacher.gender AS gender, " +
            "   c.teacher.email AS email, " +
            "   GROUP_CONCAT(c.name) AS courses " +
            "FROM Course c " +
            "WHERE c.teacher.id = :teacherId AND c.center.id = :centerId " +
            "GROUP BY c.teacher.id")
    List<Object[]> findTeacherInfoAndCoursesByTeacherId(@Param("teacherId") int teacherId,
                                                        @Param("centerId") int centerId);

}

