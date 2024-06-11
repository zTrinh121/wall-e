package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.*;
import com.example.SWP391_Project.enums.RoleDescription;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.model.PrivateNotificationDto;
import com.example.SWP391_Project.response.CourseDetailResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ManagerService {

    // ------------- private Notifications --------------
    List<PrivateNotification> getAllPrivateNotification();

    PrivateNotification createPrivateNotification(PrivateNotificationDto privateNotificationDtoDto);

    PrivateNotification updatePrivateNotification(int id, PrivateNotificationDto privateNotificationDto);

    boolean deletePrivateNotification(int id);
    // --------------------------------------------------


    // ------------- public Notifications --------------
    List<PublicNotification> getAllPublicNotification();

    PublicNotification createPublicNotification(PublicNotificationDto publicNotificationDto);

    PublicNotification updatePublicNotification(int id, PublicNotificationDto publicNotificationDto);

    boolean deletePublicNotification(int id);
    // --------------------------------------------------


    // ----------------- center Posts ------------------
    List<CenterPost> getAllCenterPost();

    CenterPost createCenterPost(CenterPostDto centerPostDto);

    CenterPost updateCenterPost(int id, CenterPostDto centerPostDto);

    boolean deleteCenterPost(int id);
    // --------------------------------------------------

    // ---------------- Manage the center ------------------

    // View tất cả những center mà manager hiện tại quản lí
    // Trong này có cả các trung tâm đang đợi admin duyệt
    List<Center> getCenters(HttpSession httpSession);

    // View chi tiết thông tin của trung tâm
    Optional<Center> findCenterById(int centerId);

    /*
        Tạo một trung tâm trên hệ thống
        --> Task này phải thông Admin duyệt
     */
    Center createCenter(CenterDto centerDto, HttpSession session);

    boolean deleteCenter(int id);
    // -------------------------------------------------


    // ----------------- Manage the course ------------------

    // View tất cả các khóa học <course> trong 1 trung tâm
    List<Course> getCoursesByCenterId(int centerId);


    // View thông tin chi tiết của 1 khóa học <course>
    CourseDetailResponse findCourseById(int id);

    /*
          Hàm này có xíu đặc biệt là khi tạo course thì:
          Manager nhập vào là: + center_code chứ không phải là khóa ngoại ID
                               + subject_code
                               + teacher_code
     */
    Course createCourse(CourseDto courseDto);

    // update course
    Course updateCourse(int courseId, CourseDto courseDto);

    boolean deleteCourse(int id);
    // -------------------------------------------------


    // ----------------- Manage the teacher ------------------
    // View all teacher in center
    List<User> getTeachersInCenter(int centerId, RoleDescription role);

    // Approve teacher's apply form
    void approveTeacherApply(int id);

    // Reject teacher's apply form
    void rejectTeacherApply(int id);

    // Delete teacher
    boolean deleteTeacher(int id);
    // -------------------------------------------------------


    // ----------------- Manage the student ------------------
    // View all student in center
    List<User> getStudentsInCenter(int centerId, RoleDescription role);

    // View all student in a certain course
    List<User> getStudentsInCertainCourse(int courseId);

    // Delete student --> xóa khỏi bảng T16
    boolean deleteStudent(int id);
    // -------------------------------------------------------


    // ----------------- Manage the slot ---------------------
    // view course's slot
    List<Slot> findSlotsInCourse(int courseId);

    // createNewSlot
    Slot createNewSlot(SlotDto slotDto);

    /*
       + Lúc này đang đứng ở giao diện chi tiết của 1 Slot
       --> Muốn update Slot đó thì Manager dùng hàm này để tìm xem room đang trống ở thời điểm diễn ra slot trên
       + Trong trường hợp Manager ngầu lòi ko check phòng trống thì
         ở hàm updateSlot cũng có các hàm để xử lí validate vấn đề về room <3
     */
    List<Room> getListEmptyRooms(Slot slot);

    // update slot's information
    Slot updateSlot(int slotId, SlotDto slotDto);

    // delete slot
    boolean deleteSlot(int id);
    // -------------------------------------------------------


    // ----------------- Manage the revenue ------------------
    // View lương của teacher ??
    // -------------------------------------------------------

    // View feedback --> maybe bỏ qua Media Controller
}
