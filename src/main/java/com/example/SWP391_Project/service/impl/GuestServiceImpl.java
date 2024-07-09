package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.service.GuestService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import jakarta.persistence.Query;
import java.util.*;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private CenterPostRepository centerPostRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserCenterRepository userCenterRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // -----------------------------------------------------------------------

    @Override
    public List<CenterPost> getAllCenterPosts() {
        return centerPostRepository
                .findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<Center> getALLCenters() {
        return centerRepository
                .findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<Course> getCoursesInCertainCenter(int centerId) {
        Optional<List<Course>> courses = courseRepository.findByCenter_Id(centerId);
        return courses.orElse(Collections.emptyList());
    }

    @Override
    public List<User> getTeachersInCertainCenter(int centerId) {
        Optional<List<User>> teachers = userCenterRepository.getTeachersInCenter(centerId);
        return teachers.orElse(Collections.emptyList());
    }

    @Override
    public List<Feedback> getFeedbacksToTeacher() {
        Optional<List<Feedback>> feedbacks = feedbackRepository.findFeedbacksToTeacher();
        return feedbacks.orElse(Collections.emptyList());
    }

    @Override
    @Transactional
    public List<Map<String, Object>> getSlotsInCourse(int courseId) {
        String query = "SELECT c.C01_COURSE_ID as courseId, c.C01_COURSE_NAME as courseName, " +
                "c.C01_COURSE_CODE as courseCode, c.C01_COURSE_DESC as description, " +
                "c.C01_COURSE_START_DATE as startDate, c.C01_COURSE_END_DATE as endDate, " +
                "c.C01_AMOUNT_OF_STUDENTS as amountOfStudents, c.C01_COURSE_FEE as courseFee, " +
                "center.C03_CENTER_NAME as centerName, " +
                "center.C03_CENTER_ID as centerId, teacher.C14_NAME as teacherName, " +
                "teacher.C14_USER_ID as teacherId, slot.C02_SLOT_ID as slotId, " +
                "slot.C02_SLOT_START_TIME as slotStartTime, slot.C02_SLOT_END_TIME as slotEndTime, " +
                "slot.C02_SLOT_DATE as slotDay " +
                "FROM t01_course c " +
                "JOIN t03_center center ON c.C01_CENTER_ID = center.C03_CENTER_ID " +
                "JOIN t14_user teacher ON c.C01_TEACHER_ID = teacher.C14_USER_ID " +
                "JOIN t02_slot slot ON c.C01_COURSE_ID = slot.C02_COURSE_ID " +
                "WHERE c.C01_COURSE_ID = :courseId";

        System.out.println("Query: " + query);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("courseId", courseId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> courseInfo = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> courseMap = new HashMap<>();
            courseMap.put("courseId", result[0]);
            courseMap.put("courseName", result[1]);
            courseMap.put("courseCode", result[2]);
            courseMap.put("description", result[3]);
            courseMap.put("startDate", result[4]);
            courseMap.put("endDate", result[5]);
            courseMap.put("amountOfStudents", result[6]);
            courseMap.put("courseFee", result[7]);
            courseMap.put("centerName", result[8]);
            courseMap.put("centerId", result[9]);
            courseMap.put("teacherName", result[10]);
            courseMap.put("teacherId", result[11]);
            courseMap.put("slotId", result[12]);
            courseMap.put("slotStartTime", result[13]);
            courseMap.put("slotEndTime", result[14]);
            courseMap.put("slotDay", result[15]);

            courseInfo.add(courseMap);
        }

        return courseInfo;
    }


    @Transactional
    @Override
    public List<Map<String, Object>> getCoursesInCenter(int centerId) {
        String query = "SELECT c.C01_COURSE_ID as courseId, c.C01_COURSE_NAME as courseName, " +
                "c.C01_COURSE_CODE as courseCode, c.C01_COURSE_DESC as description, " +
                "c.C01_COURSE_START_DATE as startDate, c.C01_COURSE_END_DATE as endDate, " +
                "c.C01_AMOUNT_OF_STUDENTS as amountOfStudents, c.C01_COURSE_FEE as courseFee, " +
                "center.C03_CENTER_NAME as centerName, " +
                "center.C03_CENTER_ID as centerId, teacher.C14_USERNAME as teacherName, " +
                "teacher.C14_USER_ID as teacherId " +
                "FROM t01_course c " +
                "JOIN t03_center center ON c.C01_CENTER_ID = center.C03_CENTER_ID " +
                "JOIN t14_user teacher ON c.C01_TEACHER_ID = teacher.C14_USER_ID " +
                "WHERE c.C01_CENTER_ID = :centerId";

        System.out.println("Query: " + query);
        System.out.println("Center ID: " + centerId);

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("centerId", centerId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> courses = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> courseMap = new HashMap<>();
            courseMap.put("courseId", result[0]);
            courseMap.put("courseName", result[1]);
            courseMap.put("courseCode", result[2]);
            courseMap.put("description", result[3]);
            courseMap.put("startDate", result[4]);
            courseMap.put("endDate", result[5]);
            courseMap.put("amountOfStudents", result[6]);
            courseMap.put("courseFee", result[7]);
            courseMap.put("centerName", result[8]);
            courseMap.put("centerId", result[9]);
            courseMap.put("teacherName", result[10]);
            courseMap.put("teacherId", result[11]);

            courses.add(courseMap);
        }

        return courses;
    }

}

