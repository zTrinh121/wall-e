package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public List<Slot> getSlotsInCertainCourse(int courseId) {
        Optional<List<Slot>> slots = slotRepository.findSlotByCourse_Id(courseId);
        return slots.orElse(Collections.emptyList());
    }

    @Override
    @Transactional
    public List<Map<String, Object>> getCoursesInCenter(int centerId) {
        String query = "SELECT c.C01_COURSE_ID as courseId, c.C01_COURSE_NAME as courseName, " +
                "c.C01_COURSE_CODE as courseCode, c.C01_COURSE_DESC as description, " +
                "c.C01_COURSE_START_DATE as startDate, c.C01_COURSE_END_DATE as endDate, " +
                "c.C01_AMOUNT_OF_STUDENTS as amountOfStudents, c.C01_COURSE_FEE as courseFee, " +
                "center.C03_CENTER_NAME as centerName, " +
                "center.C03_CENTER_ID as centerId, teacher.C14_USER_NAME as teacherName, " +
                "teacher.C14_USER_ID as teacherId " +
                "FROM t01_course c " +
                "JOIN t03_center center ON c.C01_CENTER_ID = center.C03_CENTER_ID " +
                "JOIN t14_user teacher ON c.C01_TEACHER_ID = teacher.C14_USER_ID " +
                "WHERE c.C01_CENTER_ID = ?";
        return jdbcTemplate.queryForList(query, centerId);
    }
}

