package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.*;
import com.example.SWP391_Project.enums.PaymentMethodEnum;
import com.example.SWP391_Project.enums.PaymentStatus;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.dto.PrivateNotificationDto;
import com.example.SWP391_Project.response.CourseDetailResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;

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
    Center findCenterById(int centerId);

    /*
        Tạo một trung tâm trên hệ thống
        --> Task này phải thông Admin duyệt
     */
    Center createCenter(CenterDto centerDto, HttpSession session);

    Center updateCenterInfo(int id, CenterDto centerDto);

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
    List<User> getTeachersInCenter(int centerId);

    // Approve teacher's apply form
    void approveTeacherApply(int id);

    // Reject teacher's apply form
    void rejectTeacherApply(int id);

    // Delete teacher
    boolean deleteTeacher(int id);
    // -------------------------------------------------------


    // ----------------- Manage the student ------------------
    // View all student in center
    List<User> getStudentsInCenter(int centerId);

    // View all student in a certain course
    List<User> getStudentsInCertainCourse(int courseId);

    // Delete student --> xóa khỏi bảng T16
    boolean deleteStudent(int id);
    // -------------------------------------------------------


    // ----------------- Manage the slot ---------------------
    // view course's slot
    List<Slot> findSlotsInCourse(int courseId);

    // view slots in certain day
    List<Slot> findSlotInCertainDay(Date date);

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
    // --> Vẫn chưa xử lí task quản lí lương cho giáo viên
    // --> Teamwork thống nhất lại 1 lần !

    // code vậy chứ ko biết FE cần hàm này không !
    List<Bill> getAllBills();

    /*
    Find bills with succeeded payments in a specific month.
    @param status Payment status
    @param date Month and year
    */
    List<Bill> findSucceededBills(Year year, Month month);

    /*
        Find bills with failed payments in a specific month.
        @param status Payment status
        @param date Month and year
    */
    List<Bill> findFailedBills(Year year, Month month);

    /*
        Find bills paid by cash in a specific month.
        @param method Payment method
        @param date Month and year
    */
    List<Bill> findBillsPaidByCash(Year year, Month month);

    /*
        Find bills paid by E-Banking in a specific month.
        @param method Payment method
        @param date Month and year
    */
    List<Bill> findBillsPaidByEBanking(Year year, Month month);

    /*
       Đứng ở giao diện của 1 khóa học cụ thể
       --> Có nút view ra danh sách học sinh đã đóng/chưa đóng
           học phí trong 1 tháng nhất định.
       -- * <Input truyền vào kiểu Date chỉ cần tháng và năm>
     */
    List<User> findStudentsWithUnpaidFeesInCourse(
            Year year, Month month, int courseId);

    List<User> findStudentsWithPaidFeesInCourse(
            Year year, Month month, int courseId);

    /*
       Đứng ở giao diện của 1 trung tâm cụ thể
       --> Có nút view ra danh sách học sinh đã đóng/chưa đóng
           học phí trong 1 tháng nhất định.
       -- * <Input truyền vào kiểu Date chỉ cần tháng và năm>
     */
    List<User> findStudentsWithUnpaidFeesInCenter(
            Year year, Month month, int centerId);

    List<User> findStudentsWithPaidFeesInCenter(
            Year year, Month month, int centerId);

    // -------------------------------------------------------

    // View feedback --> maybe bỏ qua Media Controller
    List<Feedback> getAllFeedbacks();

}
