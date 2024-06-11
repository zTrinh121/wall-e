package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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
}

