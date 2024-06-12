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

    @Query("SELECT c FROM Course c " +
            "JOIN c.teacher t " +
            "JOIN c.subject s " +
            "JOIN c.center cn " +
            "WHERE t.parent.id = :parentId")
    List<Course> findAllCoursesWithParentUserId(@Param("parentId") int parentId);

}

