package com.example.SWP391_Project.service;

import com.example.SWP391_Project.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface GuestService {
    // View info (post/course/center/teacher)
    // View posts
    List<CenterPost> getAllCenterPosts();

    // View centers
    List<Center> getALLCenters();

    // View courses in a certain center
    List<Course> getCoursesInCertainCenter(int centerId);

    // View list teachers in a certain center
    List<User> getTeachersInCertainCenter(int centerId);

    // View student's/parent's feedback for teacher
    List<Feedback> getFeedbacksToTeacher();

    // View slots in a certain course
    List<Slot> getSlotsInCertainCourse(int courseId);

// lấy ra các khóa học trong center
    List<Map<String, Object>> getCoursesInCenter(int centerId);
}
