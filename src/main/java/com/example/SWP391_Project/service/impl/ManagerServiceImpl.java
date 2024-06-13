package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.dto.*;
import com.example.SWP391_Project.enums.PaymentMethodEnum;
import com.example.SWP391_Project.enums.PaymentStatus;
import com.example.SWP391_Project.enums.RoleDescription;
import com.example.SWP391_Project.enums.Status;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.dto.PrivateNotificationDto;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.response.CourseDetailResponse;
import com.example.SWP391_Project.service.ManagerService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.*;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private PrivateNotificationRepository privateNotificationRepository;

    @Autowired
    private PublicNotificationRepository publicNotificationRepository;

    @Autowired
    private CenterPostRepository centerPostRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCenterRepository userCenterRepository;

    @Autowired
    private ApplyCenterRepository applyCenterRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private BillRepository billRepository;


    // ----------------------- Private notification ----------------------------
    @Override
    public List<PrivateNotification> getAllPrivateNotification() {
        return privateNotificationRepository
                .findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public PrivateNotification createPrivateNotification(PrivateNotificationDto privateNotificationDto) {
        String sendTo = privateNotificationDto.getSendTo();
        if (sendTo.startsWith("USER")) {
            Optional<User> userCode = userRepository.findByCode(privateNotificationDto.getSendTo());
            if (userCode.isPresent()) {
                PrivateNotification privateNotification = PrivateNotification.builder()
                        .title(privateNotificationDto.getTitle())
                        .content(privateNotificationDto.getContent())
                        .createdAt(new Date())
                        //.actor(Actor.MANAGER)
                        .actor(RoleDescription.MANAGER)
                        .userSendTo(userCode.get())
                        .centerSendTo(null)
                        .build();
                return privateNotificationRepository.save(privateNotification);
            } else {
                throw new IllegalArgumentException("User not found");
            }
        } else if (sendTo.startsWith("CENTER")) {
            Optional<Center> centerOptional = centerRepository.findByCode(privateNotificationDto.getSendTo());
            if (centerOptional.isPresent()) {
                PrivateNotification privateNotification = PrivateNotification.builder()
                        .title(privateNotificationDto.getTitle())
                        .content(privateNotificationDto.getContent())
                        .createdAt(new Date())
                       // .actor(Actor.MANAGER)
                        .actor(RoleDescription.MANAGER)
                        .userSendTo(null)
                        .centerSendTo(centerOptional.get())
                        .build();
                return privateNotificationRepository.save(privateNotification);
            } else {
                throw new IllegalArgumentException("Center not found");
            }
        } else {
            throw new IllegalArgumentException("Invalid sendTo value");
        }
    }

    @Override
    public PrivateNotification updatePrivateNotification(int id, PrivateNotificationDto privateNotificationDto) {
        PrivateNotification privateNotification = privateNotificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The private notification hasn't been existed ! "));

        privateNotification.setTitle(privateNotificationDto.getTitle());
        privateNotification.setContent(privateNotificationDto.getContent());
        privateNotification.setUpdatedAt(new Date());

        return privateNotificationRepository.save(privateNotification);
    }

    @Override
    public boolean deletePrivateNotification(int id) {
        try {
            privateNotificationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // ------------------------------------------------------------------------


    // ----------------------- Public notification ----------------------------
    @Override
    public List<PublicNotification> getAllPublicNotification() {
        return publicNotificationRepository
                .findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public PublicNotification createPublicNotification(PublicNotificationDto publicNotificationDto) {
        Optional<Center> centerOtp = centerRepository.findByCode(publicNotificationDto.getCenterSendTo());
        if (!centerOtp.isPresent()) {
            throw new IllegalArgumentException("Center not found when finding by code !");
        }
        Center center = centerOtp.get();

        PublicNotification publicNotification = PublicNotification.builder()
                .title(publicNotificationDto.getTitle())
                .content(publicNotificationDto.getContent())
                .createdAt(new Date())
                .center(center)
                .build();
        return publicNotificationRepository.save(publicNotification);
    }

    public PublicNotification findPublicNotificationById(int id) {
        return publicNotificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The public notification hasn't been existed ! "));
    }

    @Override
    public PublicNotification updatePublicNotification(int id, PublicNotificationDto publicNotificationDto) {
        PublicNotification publicNotification = findPublicNotificationById(id);

        publicNotification.setTitle(publicNotificationDto.getTitle());
        publicNotification.setContent(publicNotificationDto.getContent());
        publicNotification.setUpdatedAt(new Date());

        return publicNotificationRepository.save(publicNotification);
    }

    @Override
    public boolean deletePublicNotification(int id) {
        try {
            publicNotificationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // ------------------------------------------------------------------------


    // -------------------------- Center Post -------------------------------
    @Override
    public List<CenterPost> getAllCenterPost() {
        return centerPostRepository
                .findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public CenterPost createCenterPost(CenterPostDto centerPostDto) {
        Optional<Center> centerOtp = centerRepository.findByCode(centerPostDto.getCenterSendTo());
        if (!centerOtp.isPresent()) {
            throw new IllegalArgumentException("Center not found when finding by code !");
        }
        Center center = centerOtp.get();

        CenterPost centerPost = CenterPost.builder()
                .title(centerPostDto.getTitle())
                .content(centerPostDto.getContent())
                .createdAt(new Date())
                .file_url(centerPostDto.getFile_url())
                .center(center)
                .build();
        return centerPostRepository.save(centerPost);
    }

    public CenterPost findCenterPostById(int id) {
        return centerPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The center post hasn't been existed ! "));
    }

    @Override
    public CenterPost updateCenterPost(int id, CenterPostDto centerPostDto) {
        CenterPost centerPost = findCenterPostById(id);

        centerPost.setTitle(centerPostDto.getTitle());
        centerPost.setContent(centerPostDto.getContent());
        centerPost.setFile_url(centerPostDto.getFile_url());
        centerPost.setUpdatedAt(new Date());

        return centerPostRepository.save(centerPost);
    }

    @Override
    public boolean deleteCenterPost(int id) {
        try {
            centerPostRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // ------------------------------------------------------------------------

    // ----------------------------- Feedback --------------------------------
    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll
                (Sort.by(Sort.Direction.DESC, "id"));
    }
    // -----------------------------------------------------------------------


    // ------------------------- Manager center ----------------------------
    @Override
    public List<Center> getCenters(User user) {
        Optional<List<Center>> centers = centerRepository.findByManager(user);
        return centers.orElse(Collections.emptyList());
    }


    @Override
    public Center findCenterById(int centerId) {
        return centerRepository.findById(centerId)
                .orElseThrow(() -> new IllegalStateException("Center hasn't been existed with ID: " + centerId));
    }

    @Override
    public Center createCenter(CenterDto centerDto, HttpSession session) {
        if (centerDto == null) {
            return null;
        }
        User loggedInUser = (User) session.getAttribute("User");
        if (loggedInUser == null) {
            throw new IllegalArgumentException("User is not present in the session.");
        }

        Center center = Center.builder()
                .code(centerDto.getCode())
                .name(centerDto.getName())
                .description(centerDto.getDescription())
                .address(centerDto.getAddress())
                .phone(centerDto.getPhone())
                .email(centerDto.getEmail())
                .regulation(centerDto.getRegulation())
                .createdAt(new Date())
                .imagePath(centerDto.getImagePath())
                .manager(loggedInUser)
                .build();
        return centerRepository.save(center);
    }

    @Override
    public Center updateCenterInfo(int id, CenterDto centerDto) {
        Center center = centerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The center hasn't been existed"));

        center.setName(centerDto.getName());
        center.setDescription((centerDto.getDescription()));
        center.setAddress(centerDto.getAddress());
        center.setPhone(centerDto.getPhone());
        center.setEmail(centerDto.getEmail());
        center.setRegulation(centerDto.getRegulation());
        center.setImagePath(centerDto.getImagePath());

        return centerRepository.save(center);
    }

    @Override
    public boolean deleteCenter(int id) {
        try {
            centerRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // ------------------------------------------------------------------------


    // ------------------------- Manager course ----------------------------
    @Override
    public List<Course> getCoursesByCenterId(int centerId) {
        Optional<List<Course>> optionalCourses = courseRepository.findByCenter_Id(centerId);
        return optionalCourses.isPresent()
                ? optionalCourses.get() : Collections.emptyList();
    }

    @Override
    public CourseDetailResponse findCourseById(int courseId) {
        return courseRepository.getCourseDetailByCourseId(courseId);
    }

    @Override
    public Course createCourse(CourseDto courseDto) {
        // Find Center by code
        Optional<Center> centerOtp = centerRepository.findByCode(courseDto.getCenterCode());
        if (!centerOtp.isPresent()) {
            throw new IllegalArgumentException("Center not found");
        }
        Center center = centerOtp.get();

        // Find Subject by code
        Optional<Subject> subjectOtp = subjectRepository.findByCode(courseDto.getSubjectCode());
        if (!subjectOtp.isPresent()) {
            throw new IllegalArgumentException("Subject not found");
        }
        Subject subject = subjectOtp.get();

        // Find Teacher by code
        Optional<User> teacherOtp = userRepository.findByCode(courseDto.getTeacherCode());
        if (!teacherOtp.isPresent()) {
            throw new IllegalArgumentException("Teacher not found");
        }
        User teacher = teacherOtp.get();

        // Lấy thông tin từ CourseDto
        Date startDate = courseDto.getStartDate();
        Date endDate = courseDto.getEndDate();
        float courseFee = courseDto.getCourseFee();

        // Chuyển Date thành Calendar để tính toán
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        int startYear = startCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int endYear = endCalendar.get(Calendar.YEAR);
        int endMonth = endCalendar.get(Calendar.MONTH);

        // Tính số tháng giữa startDate và endDate
        int totalMonths = (endYear - startYear) * 12 + (endMonth - startMonth) + 1;

        // Tính totalCourseFee
        float totalCourseFee = totalMonths * courseFee;

        Course course = Course.builder()
                .name(courseDto.getName())
                .code(courseDto.getCode())
                .description(courseDto.getDescription())
                .startDate(courseDto.getStartDate())
                .endDate(courseDto.getEndDate())
                .amountOfStudents(courseDto.getAmountOfStudents())
                .totalCourseFee(totalCourseFee)
                .courseFee(courseDto.getCourseFee())
                .center(center)
                .subject(subject)
                .teacher(teacher)
                .build();
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(int courseId, CourseDto courseDto) {
        // Tìm Course theo ID
        Optional<Course> courseOtp = courseRepository.findById(courseId);
        if (!courseOtp.isPresent()) {
            throw new IllegalArgumentException("Course not found");
        }
        Course course = courseOtp.get();

        // Chuyển Date thành Calendar để tính toán
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(courseDto.getStartDate());
        int startYear = startCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(courseDto.getEndDate());
        int endYear = endCalendar.get(Calendar.YEAR);
        int endMonth = endCalendar.get(Calendar.MONTH);

        // Tính số tháng giữa startDate và endDate
        int totalMonths = (endYear - startYear) * 12 + (endMonth - startMonth) + 1;

        // Tính tổng học phí (totalCourseFee)
        float totalCourseFee = totalMonths * courseDto.getCourseFee();

        // Cập nhật các thuộc tính của Course
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setStartDate(courseDto.getStartDate());
        course.setEndDate(courseDto.getEndDate());
        course.setAmountOfStudents(courseDto.getAmountOfStudents());
        course.setCourseFee(courseDto.getCourseFee());
        course.setTotalCourseFee(totalCourseFee);

        return courseRepository.save(course);
    }

    @Override
    public boolean deleteCourse(int id) {
        try {
            courseRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // ------------------------------------------------------------------------


    // -------------------------- Manager teacher -----------------------------
    public List<User> getTeachersInCenter(int centerId) {
        Optional<List<User>> teachers = userCenterRepository.getTeachersInCenter(centerId);
        return teachers.orElse(Collections.emptyList());
    }

    @Override
    public void approveTeacherApply(int id) {
        try {
            ApplyCenter applyCenter = applyCenterRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("The teacher apply form not found with ID: " + id));
            applyCenter.setStatus(Status.Approved);
            applyCenterRepository.save(applyCenter);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to approve teacher form with ID: " + id, e);
        }
    }

    @Override
    public void rejectTeacherApply(int id) {
        try {
            ApplyCenter applyCenter = applyCenterRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("The teacher apply form not found with ID: " + id));
            applyCenter.setStatus(Status.Rejected);
            applyCenterRepository.save(applyCenter);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to reject teacher form with ID: " + id, e);
        }
    }

    @Override
    public boolean deleteTeacher(int id) {
        try {
            userCenterRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // ------------------------------------------------------------------------


    // -------------------------- Manager student -----------------------------
    @Override
    public List<User> getStudentsInCenter(int centerId) {
        Optional<List<User>> students = userCenterRepository.getStudentsInCenter(centerId);
        return students.orElse(Collections.emptyList());
    }

    @Override
    public List<User> getStudentsInCertainCourse(int courseId) {
        Optional<List<User>> students = courseRepository.getStudentsInCertainCourse(courseId);
        return students.orElse(Collections.emptyList());
    }

    @Override
    public boolean deleteStudent(int id) {
        try {
            userCenterRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // ------------------------------------------------------------------------


    // ----------------------------- Manage slot ------------------------------
    @Override
    public List<Slot> findSlotsInCourse(int courseId) {
        Optional<List<Slot>> slots = slotRepository.findSlotByCourse_Id(courseId);
        return slots.orElse(Collections.emptyList());
    }

    @Override
    public List<Slot> findSlotInCertainDay(Date date) {
        Optional<List<Slot>> slots = slotRepository.findBySlotDate(date);
        if(!slots.isPresent()) {
            throw new IllegalArgumentException("Do not exist the slot in" + date);
        }
        return slots.get();
    }

    @Override
    public Slot createNewSlot(SlotDto slotDto) {
        // Find room by name
        Optional<Room> roomName = roomRepository.findByName(slotDto.getRoomName());
        if (!roomName.isPresent()) {
            throw new IllegalArgumentException("Room not found");
        }
        Room room = roomName.get();

        // Find course by code
        Optional<Course> courseOtp = courseRepository.findByCode(slotDto.getCourseCode());
        if (!courseOtp.isPresent()) {
            throw new IllegalArgumentException("Course not found");
        }
        Course course = courseOtp.get();

        Slot slot = Slot.builder()
                .slotStartTime(slotDto.getSlotStartTime())
                .slotEndTime(slotDto.getSlotEndTime())
                .course(course)
                .room(room)
                .build();
        return slotRepository.save(slot);
    }

    @Override
    public List<Room> getListEmptyRooms(Slot slot) {
        int centerId = slot.getCourse().getCenter().getId();
        Date slotStartTime = slot.getSlotStartTime();
        Date slotEndTime = slot.getSlotEndTime();

        return roomRepository.findEmptyRooms(slotStartTime, slotEndTime, centerId);
    }

    // cái này còn thiếu view ra thời khóa biểu của giáo viên và học sinh
    // trong trường hợp giáo viên muốn đổi slot đó qua ngày khác
    @Override
    public Slot updateSlot(int slotId, SlotDto slotDto) {
        Optional<Slot> slotOpt = slotRepository.findById(slotId);
        if (!slotOpt.isPresent()) {
            throw new IllegalArgumentException("Slot not found");
        }
        Slot slot = slotOpt.get();

        // Kiểm tra course có trong trung tâm hay ko
        Optional<Course> courseOtp = courseRepository.findByCode(slotDto.getCourseCode());
        if (!courseOtp.isPresent()) {
            throw new IllegalArgumentException("Course not found");
        }
        Course course = courseOtp.get();

        // Kiểm tra phòng có tồn tại trong trung tâm hay không
        Optional<Room> roomOpt = roomRepository.findByNameAndCenterId(slotDto.getRoomName(), course.getCenter().getId());
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("Room not found");
        }
        Room room = roomOpt.get();

        // Kiểm tra xem phòng có slot diễn ra vào thời gian mới cập nhật hay không
        boolean isSlotOccurring = roomRepository.existsSlotOccurring(room, slotDto.getSlotStartTime(), slotDto.getSlotEndTime());
        if (isSlotOccurring) {
            throw new IllegalArgumentException("Room has slot occurring now");
        }

        slot.setSlotStartTime(slotDto.getSlotStartTime());
        slot.setSlotEndTime(slotDto.getSlotEndTime());
        slot.setRoom(room);

        return slotRepository.save(slot);
    }

    @Override
    public boolean deleteSlot(int id) {
        try {
            slotRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // ------------------------------------------------------------------------


    // ---------------------------- Manage the revenue ------------------------
    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll
                (Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<Bill> findSucceededBills(Year year, Month month) {
        Optional<List<Bill>> bills =
                billRepository.findByStatusAndCreatedAt(PaymentStatus.Succeeded, year, month);
        return bills.orElse(Collections.emptyList());
    }

    @Override
    public List<Bill> findFailedBills(Year year, Month month) {
        Optional<List<Bill>> bills =
                billRepository.findByStatusAndCreatedAt(PaymentStatus.Failed, year, month);
        return bills.orElse(Collections.emptyList());
    }

    @Override
    public List<Bill> findBillsPaidByCash(Year year, Month month) {
        Optional<List<Bill>> bills =
                billRepository.findByPaymentMethodAndCreatedAt(PaymentMethodEnum.Cash, year, month);
        return bills.orElse(Collections.emptyList());
    }

    @Override
    public List<Bill> findBillsPaidByEBanking(Year year, Month month) {
        Optional<List<Bill>> bills =
                billRepository.findByPaymentMethodAndCreatedAt(PaymentMethodEnum.E_Banking, year, month);
        return bills.orElse(Collections.emptyList());
    }

    @Override
    public List<User> findStudentsWithPaidFeesInCourse(Year year, Month month, int courseId) {
        Optional<List<User>> users =
                userRepository.findStudentsWithPaidFeesInCourse(PaymentStatus.Succeeded, year, month, courseId);
        return users.orElse(Collections.emptyList());
    }

    @Override
    public List<User> findStudentsWithUnpaidFeesInCourse(Year year, Month month, int courseId) {
        Optional<List<User>> users =
                userRepository.findStudentsWithPaidFeesInCourse(PaymentStatus.Failed, year, month, courseId);
        return users.orElse(Collections.emptyList());
    }

    @Override
    public List<User> findStudentsWithPaidFeesInCenter(Year year, Month month, int centerId) {
        Optional<List<User>> users =
                userRepository.findStudentsWithPaidFeesInCenter(PaymentStatus.Succeeded, year, month, centerId);
        return users.orElse(Collections.emptyList());
    }

    @Override
    public List<User> findStudentsWithUnpaidFeesInCenter(Year year, Month month, int centerId) {
        Optional<List<User>> users =
                userRepository.findStudentsWithPaidFeesInCenter(PaymentStatus.Failed, year, month, centerId);
        return users.orElse(Collections.emptyList());
    }
}



