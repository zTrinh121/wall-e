package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.*;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.CourseDetailResponse;
import com.example.SWP391_Project.response.StudentCoursesResponse;
import com.example.SWP391_Project.response.TeacherCoursesResponse;
import com.example.SWP391_Project.response.TeacherSalaryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface ManagerService {
    // ----------------- center Posts ------------------
    List<CenterPost> getAllCenterPost();

    List<CenterPost> findCenterPostsByCenterId(int centerId);

    CenterPost createCenterPost(CenterPostDto postDto, MultipartFile imageFile);

    CenterPost updateCenterPost(int id, CenterPostDto centerPostDto, MultipartFile imageFile);

    boolean deleteCenterPost(int id);
    // --------------------------------------------------

    // ---------------- Manage the center ------------------

    // View tất cả những center mà manager hiện tại quản lí
    // Trong này có cả các trung tâm đang đợi admin duyệt
    List<Center> getCenters(int managerId);

    // View chi tiết thông tin của trung tâm
    Center findCenterById(int centerId);

    /*
        Tạo một trung tâm trên hệ thống
        --> Task này phải thông Admin duyệt
     */
    Center createCenter(CenterDto centerDto, User manager);

    void uploadCenterImage(final int id, final MultipartFile file);

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

    // View all apply center form
    List<ApplyCenter> viewApplyCenterForm(int managerId);

    ApplyCenter viewApplyFormDetail(int applyId);

    // Approve teacher's apply form
    void approveTeacherApply(int id);

    // Reject teacher's apply form
    void rejectTeacherApply(int id);

    // Delete teacher
    boolean deleteTeacherInCenter(int teacherId, int centerId);
    // -------------------------------------------------------


    // ----------------- Manage the student ------------------
    // View all student in center
    List<User> getStudentsInCenter(int centerId);

    // View all student in a certain course
    List<User> getStudentsInCertainCourse(int courseId);

    // Delete student --> xóa khỏi bảng T16
    boolean deleteStudentInCenter(int studentId, int centerId);
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

    // ---------------------- BỔ SUNG ----------------------
    int countTeachersByCenter(int centerId);

    int countStudentsByCenter(int centerId);

    int countCourseByCenter(int centerId);

    List<TeacherCoursesResponse> getTeacherInfoAndCourses(int teacherId, int centerId);

    List<StudentCoursesResponse> getStudentInfoAndCourses(int studentId, int centerId);

    // ------------- Individual Notifications --------------
    List<IndividualNotification> getAllIndividualNotifications(int managerId);

    IndividualNotification createIndividualNotification(IndividualNotificationDto invididualNotificationDto, User admin);

    IndividualNotification updateIndividualNotification(int id, IndividualNotificationDto invididualNotificationDto);

    boolean deleteIndividualNotification(int id);

    List<IndividualNotification> getViewersIndividualNotification(int managerId);
    // --------------------------------------------------

    // ------------- Center Notifications --------------
    List<CenterNotification> getAllCenterNotifications(int managerId);

    List<CenterNotification> findByCenterId(int centerId);

    CenterNotification createCenterNotification(CenterNotificationDto centerNotificationDto);

    CenterNotification updateCenterNotification(int id, CenterNotificationDto centerNotificationDto);

    boolean deleteCenterNotification(int id);

    List<ViewCenterNotification> getListViewersCenterNotification(int notificationId);
    // --------------------------------------------------

    // --------------- VIEW FEEDBACK -------------------
    // view fb đến 1 teacher, 1 course cụ thể
    List<Feedback> viewFeedbacksToCertainTeacherInCenter(int teacherId);
    List<Feedback> viewFeedbacksToCertainCourseInCenter(int courseId);

    // view fb đến toàn bộ teacher, course trong trung tâm
    List<Feedback> viewAllFeedbacksToTeachers(int managerId);
    List<Feedback> viewAllFeedbacksToCourses(int managerId);
    // ------------------------------------------------

    // --------------------- SLOTS -------------------------
    List<Map<String, Object>> getSlotsByCenterId(int centerId);

    Map<String, Object> getSlotsBySlotId(int slotId);

    Slot createSlot(SlotDto slotDto);

    Slot updateSlot(int slotId, SlotDto slotDto);

    void deleteSlot(int slotId);

    // tạo slots cho toàn bộ khóa học
    void insertSlotsAndStudentSlots(List<SlotsDto> slotsDtos, String courseCode, String roomName);

    // -----------------------------------------------------

    List<TeacherSalaryResponse> getTeacherSalaries(int month, int year, Long centerId);

    List<Map<String, Object>> getTotalTeacherSalary(int month, int year, Long centerId);

    Map<String, Object> getMonthlyRevenue(int month, int year, Long centerId);

    Map<String, Object> getMonthlyProfit(int month, int year, Long centerId);

}
