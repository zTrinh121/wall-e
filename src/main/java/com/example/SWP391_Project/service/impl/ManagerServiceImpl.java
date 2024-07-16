package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.dto.*;
import com.example.SWP391_Project.enums.PaymentMethodEnum;
import com.example.SWP391_Project.enums.PaymentStatus;
import com.example.SWP391_Project.enums.Status;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.response.*;
import com.example.SWP391_Project.service.ManagerService;
import com.example.SWP391_Project.utils.FileUploadUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private CenterPostRepository centerPostRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private CourseRepository courseRepository;

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

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private IndividualNotificationRepository individualNotificationRepository;

    @Autowired
    private CenterNotificationRepository centerNotificationRepository;

    @Autowired
    private ViewCenterNotificationRepository viewCenterNotificationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private StudentSlotRepository studentSlotRepository;

    // -------------------------- Center Post -------------------------------
    @Override
    public List<CenterPost> getAllCenterPost() {
        return centerPostRepository
                .findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Override
    public List<CenterPost> findCenterPostsByCenterId(int centerId) {
        return centerPostRepository.findCenterPostsByCenterId(centerId);
    }

    @Transactional
    public CenterPost createCenterPost(CenterPostDto postDto, MultipartFile imageFile) {
        FileUploadUtil.assertAllowedImage(imageFile);
        Optional<Center> centerOtp = centerRepository.findByCode(postDto.getCenterSendTo());
        if (!centerOtp.isPresent()) {
            throw new IllegalArgumentException("Center not found when finding by code !");
        }
        Center center = centerOtp.get();

        try {
            String fileName = FileUploadUtil.getFileName(imageFile.getOriginalFilename());
            CloudinaryResponse imageResponse = cloudinaryService.uploadImageFile(imageFile, fileName);

            CenterPost centerPost = CenterPost.builder()
                    .title(postDto.getTitle())
                    .content(postDto.getContent())
                    .status(Status.Wait_to_process)
                    .imagePath(imageResponse.getUrl())
                    .cloudinaryImageId(imageResponse.getPublicId())
                    .createdAt(new Date())
                    .center(center)
                    .build();

            centerPost = centerPostRepository.save(centerPost);
            return centerPost;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create center post and upload image: " + e.getMessage());
        }
    }


    public CenterPost findCenterPostById(int id) {
        return centerPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The center post hasn't been existed!"));
    }

    @Override
    public CenterPost updateCenterPost(int id, CenterPostDto centerPostDto, MultipartFile imageFile) {
        CenterPost centerPost = findCenterPostById(id);

        centerPost.setTitle(centerPostDto.getTitle());
        centerPost.setContent(centerPostDto.getContent());
        centerPost.setUpdatedAt(new Date());

        if (imageFile != null && !imageFile.isEmpty()) {
            FileUploadUtil.assertAllowedImage(imageFile);
            String fileName = FileUploadUtil.getFileName(imageFile.getOriginalFilename());
            CloudinaryResponse imageResponse = cloudinaryService.uploadImageFile(imageFile, fileName);

            centerPost.setImagePath(imageResponse.getUrl());
            centerPost.setCloudinaryImageId(imageResponse.getPublicId());
        }

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

    // ------------------------- Manager center ----------------------------
    @Override
    public List<Center> getCenters(int managerId) {
        Optional<List<Center>> centers = centerRepository.findByManager_Id(managerId);
        return centers.orElse(Collections.emptyList());
    }

    @Override
    public Center findCenterById(int centerId) {
        return centerRepository.findById(centerId)
                .orElseThrow(() -> new IllegalStateException("Center hasn't been existed with ID: " + centerId));
    }

    @Override
    public void uploadCenterImage(final int id, final MultipartFile file) {
        final Center center = findCenterById(id);
        FileUploadUtil.assertAllowedImage(file);
        final String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
        final CloudinaryResponse response = this.cloudinaryService.uploadImageFile(file, fileName);
        center.setImagePath(response.getUrl());
        center.setCloudinaryImageId(response.getPublicId());
        this.centerRepository.save(center);
    }

    @Override
    public Center createCenter(CenterDto centerDto, User manager) {
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
                .manager(manager)
                .build();
        return centerRepository.save(center);
    }


    @Override
    @Transactional
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
        Optional<Center> centerOptional = centerRepository.findById(id);
        if (centerOptional.isPresent()) {
            Center center = centerOptional.get();

            // Kiểm tra xem có các Course tham chiếu đến Center này không
            Optional<List<Course>> coursesOptional = courseRepository.findByCenter_Id(id);
            coursesOptional.ifPresent(courses -> {
                if (!courses.isEmpty()) {
                    throw new DataIntegrityViolationException("Cannot delete center with ID " + id + " because it is referenced by existing courses.");
                }
            });

            // Kiểm tra xem có các Room tham chiếu đến Center này không
            Optional<List<Room>> roomsOptional = roomRepository.findByCenter_Id(id);
            roomsOptional.ifPresent(rooms -> {
                if (!rooms.isEmpty()) {
                    throw new DataIntegrityViolationException("Cannot delete center with ID " + id + " because it is referenced by existing rooms.");
                }
            });

            // Xóa Center nếu không có Course hoặc Room nào tham chiếu đến
            centerRepository.delete(center);
            return true;
        }
        return false;
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

        // Find Teacher by code
        Optional<User> teacherOtp = userRepository.findByUsernamee(courseDto.getTeacherCode());
        if (!teacherOtp.isPresent()) {
            throw new IllegalArgumentException("Teacher not found");
        }
        User teacher = teacherOtp.get();

        Course course = Course.builder()
                .name(courseDto.getName())
                .code(courseDto.getCode())
                .description(courseDto.getDescription())
                .startDate(courseDto.getStartDate())
                .endDate(courseDto.getEndDate())
                .amountOfStudents(courseDto.getAmountOfStudents())
                .courseFee(courseDto.getCourseFee())
                .subject(courseDto.getSubjectName())
                .center(center)
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

        // Cập nhật các thuộc tính của Course
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setStartDate(courseDto.getStartDate());
        course.setEndDate(courseDto.getEndDate());
        course.setAmountOfStudents(courseDto.getAmountOfStudents());
        course.setCourseFee(courseDto.getCourseFee());

        return courseRepository.save(course);
    }

    public boolean deleteCourse(int courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();

            Optional<List<Enrollment>> enrollmentsOptional = enrollmentRepository.findByCourse_Id(courseId);
            if (enrollmentsOptional.isPresent() && !enrollmentsOptional.get().isEmpty()) {
                throw new DataIntegrityViolationException("Cannot delete course with ID " + courseId + " because it is referenced by existing enrollments.");
            }

            courseRepository.delete(course);
            return true;
        }
        return false;
    }
    // ------------------------------------------------------------------------


    // -------------------------- Manager teacher -----------------------------
    @Override
    public List<User> getTeachersInCenter(int centerId) {
        Optional<List<User>> teachers = userCenterRepository.getTeachersInCenter(centerId);
        return teachers.orElse(Collections.emptyList());
    }

    @Override
    public List<ApplyCenter> viewApplyCenterForm(int centerId) {
        return applyCenterRepository.findByStatusAndCenter_Id(Status.Wait_to_process ,centerId);
    }

    @Override
    public ApplyCenter viewApplyFormDetail(int applyId) {
        return applyCenterRepository.findById(applyId).orElse(null);
    }

    @Override
    public void approveTeacherApply(int id) {
        try {
            ApplyCenter applyCenter = applyCenterRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("The teacher apply form not found with ID: " + id));
            applyCenter.setStatus(Status.Approved);
            applyCenterRepository.save(applyCenter);

            // add teacher to center when approve apply form
            User teacher = applyCenter.getTeacher();
            Center center = applyCenter.getCenter();
            UserCenter userCenter = UserCenter.builder()
                    .user(teacher)
                    .center(center)
                    .build();
            userCenterRepository.save(userCenter);
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
    public boolean deleteTeacherInCenter(int teacherId, int centerId) {
        try {
            userCenterRepository.deleteUserInCenter(teacherId, centerId);
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
    public boolean deleteStudentInCenter(int studentId, int centerId) {
        try {
            userCenterRepository.deleteUserInCenter(studentId, centerId);
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
        Optional<List<Bill>> optionalBills =
                billRepository.findByStatusAndCreatedAt(PaymentStatus.Succeeded, year, month);
        return optionalBills.orElse(Collections.emptyList());
    }

    @Override
    public List<Bill> findFailedBills(Year year, Month month) {
        Optional<List<Bill>> optionalBills =
                billRepository.findByStatusAndCreatedAt(PaymentStatus.Failed, year, month);
        return optionalBills.orElse(Collections.emptyList());
    }

    @Override
    public List<Bill> findBillsPaidByCash(Year year, Month month) {
        Optional<List<Bill>> optionalBills =
                billRepository.findByPaymentMethodAndCreatedAt(PaymentMethodEnum.Cash, year, month);
        return optionalBills.orElse(Collections.emptyList());
    }

    @Override
    public List<Bill> findBillsPaidByEBanking(Year year, Month month) {
        Optional<List<Bill>> optionalBills =
                billRepository.findByPaymentMethodAndCreatedAt(PaymentMethodEnum.E_Banking, year, month);
        return optionalBills.orElse(Collections.emptyList());
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
    //bổ sung giáo diện nút tìm hs chưa nộp fee ở trong khoá học ở trong trung tâm
    @Override
    public List<User> findStudentsWithUnpaidFeesInCenter(Year year, Month month, int centerId) {
        Optional<List<User>> users =
                userRepository.findStudentsWithPaidFeesInCenter(PaymentStatus.Failed, year, month, centerId);
        return users.orElse(Collections.emptyList());
    }

    // -------------------------------------------------------------------

    @Override
    public int countTeachersByCenter(int centerId) {
        return userCenterRepository.countTeachersByCenter(centerId);
    }

    @Override
    public int countStudentsByCenter(int centerId) {
        return userCenterRepository.countStudentsByCenter(centerId);
    }

    @Override
    public int countCourseByCenter(int centerId) {
        return courseRepository.countCourseByCenter(centerId);
    }

    @Override
    public List<TeacherCoursesResponse> getTeacherInfoAndCourses(int teacherId, int centerId) {
        List<Object[]> results = courseRepository.findTeacherInfoAndCoursesByTeacherId(teacherId, centerId);
        return results.stream()
                .map(obj -> new TeacherCoursesResponse(
                        (String) obj[0],      // teacherUsername
                        (String) obj[1],      // teacherName
                        (String) obj[2],      // teacherPhone
                        (String) obj[3],      // teacherAddress
                        (java.util.Date) obj[4], // teacherDob
                        (boolean) obj[5],     // teacherGender
                        (String) obj[6],      // teacherEmail
                        (String) obj[7]       // courseNames
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentCoursesResponse> getStudentInfoAndCourses(int studentId, int centerId) {
        List<Object[]> results = enrollmentRepository.findStudentInfoAndCoursesByStudentId(studentId, centerId);
        return results.stream()
                .map(obj -> new StudentCoursesResponse(
                        (String) obj[0],      // studentUsername
                        (String) obj[1],      // studentName
                        (String) obj[2],      // studentPhone
                        (String) obj[3],      // studentAddress
                        (java.util.Date) obj[4], // studentDob
                        (Boolean) obj[5],     // studentGender
                        (String) obj[6],      // studentEmail
                        (String) obj[7],      // courseNames
                        (String) obj[8],      // parentUsername
                        (String) obj[9],      // parentName
                        (String) obj[10],      // parentPhone
                        (java.util.Date) obj[11], // parentDob
                        (Boolean) obj[12],    // parentGender
                        (String) obj[13]     // parentEmail
                ))
                .collect(Collectors.toList());
    }


    // ------------------------- Individual notification ---------------------------
    @Override
    public List<IndividualNotification> getAllIndividualNotifications(int managerId) {
        Optional<List<IndividualNotification>> notifications = individualNotificationRepository.findByActor_Id(managerId);
        return notifications.orElse(Collections.emptyList());
    }

    @Override
    public IndividualNotification createIndividualNotification(IndividualNotificationDto invididualNotificationDto, User manager) {
        Optional<User> sendTo = userRepository.findByUsernamee(invididualNotificationDto.getSendToUser());
        if (!sendTo.isPresent()) {
            throw new IllegalArgumentException("User not found when finding by username !");
        }
        User user = sendTo.get();

        IndividualNotification individualNotification = IndividualNotification.builder()
                .title(invididualNotificationDto.getTitle())
                .content(invididualNotificationDto.getContent())
                .actor(manager)
                .createdAt(new Date())
                .sendToUser(user)
                .hasSeen(false)
                .build();
        return individualNotificationRepository.save(individualNotification);
    }

    @Override
    public IndividualNotification updateIndividualNotification(int id, IndividualNotificationDto individualNotificationDto) {
        IndividualNotification individualNotification = individualNotificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The individual notification hasn't been existed ! "));

        individualNotification.setTitle(individualNotificationDto.getTitle());
        individualNotification.setContent(individualNotification.getContent());
        individualNotification.setUpdatedAt(new Date());

        return individualNotificationRepository.save(individualNotification);
    }

    @Override
    public boolean deleteIndividualNotification(int id) {
        try {
            individualNotificationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<IndividualNotification> getViewersIndividualNotification(int managerId) {
        Optional<List<IndividualNotification>> optionalNotifications = individualNotificationRepository.findByActorIdAndHasSeen(managerId);
        return optionalNotifications.orElse(Collections.emptyList());
    }

    // ---------------------------- Center notification ---------------------------
    @Override
    public List<CenterNotification> getAllCenterNotifications(int managerId) {
        Optional<List<CenterNotification>> optionalNotifications = centerNotificationRepository.findAllByManagerId(managerId);
        return optionalNotifications.orElse(Collections.emptyList());
    }

    @Override
    public List<CenterNotification> findByCenterId(int centerId) {
        Optional<List<CenterNotification>> optionalNotifications = centerNotificationRepository.findByCenter_Id(centerId);
        return optionalNotifications.orElse(Collections.emptyList());
    }

    @Override
    public CenterNotification createCenterNotification(CenterNotificationDto centerNotificationDto) {
        Optional<Center> center = centerRepository.findById(centerNotificationDto.getCenterId());
        if (!center.isPresent()) {
            throw new IllegalArgumentException("Center not found when finding by ID !");
        }
        Center ct = center.get();

        CenterNotification centerNotification = CenterNotification.builder()
                .title(centerNotificationDto.getTitle())
                .content(centerNotificationDto.getContent())
                .createdAt(new Date())
                .center(ct)
                .build();
        return centerNotificationRepository.save(centerNotification);
    }

    @Override
    public CenterNotification updateCenterNotification(int id, CenterNotificationDto centerNotificationDto) {
        CenterNotification centerNotification = centerNotificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The center notification hasn't been existed ! "));

        centerNotification.setTitle(centerNotification.getTitle());
        centerNotification.setContent(centerNotification.getContent());
        centerNotification.setUpdatedAt(new Date());

        return centerNotificationRepository.save(centerNotification);
    }

    @Override
    public boolean deleteCenterNotification(int id) {
        try {
            centerNotificationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ViewCenterNotification> getListViewersCenterNotification(int notificationId) {
        Optional<List<ViewCenterNotification>> optionalList = viewCenterNotificationRepository.findByCenterNotification_Id(notificationId);
        return optionalList.orElse(Collections.emptyList());
    }

    // ------------------------- VIEW FEEDBACKS ---------------------------
    @Override
    public List<Feedback> viewFeedbacksToCertainTeacherInCenter(int teacherId) {
        Optional<List<Feedback>> feedbacks = feedbackRepository.findBySendToUser_Id(teacherId);
        return feedbacks.orElse(Collections.emptyList());
    }

    @Override
    public List<Feedback> viewFeedbacksToCertainCourseInCenter(int courseId) {
        Optional<List<Feedback>> feedbacks = feedbackRepository.findBySendToCourse_Id(courseId);
        return feedbacks.orElse(Collections.emptyList());
    }

    @Override
    public List<Feedback> viewAllFeedbacksToTeachers(int managerId) {
        Optional<List<Feedback>> feedbacks = feedbackRepository.managerViewTeacherFeedbacks(managerId);
        return feedbacks.orElse(Collections.emptyList());
    }

    @Override
    public List<Feedback> viewAllFeedbacksToCourses(int managerId) {
        Optional<List<Feedback>> feedbacks = feedbackRepository.managerViewCourseFeedbacks(managerId);
        return feedbacks.orElse(Collections.emptyList());
    }
    // ------------------------------------------------------------------

    // -------------------------- SLOTS -------------------------------
    @Transactional
    @Override
    public List<Map<String, Object>> getSlotsByCenterId(int centerId) {
        String query = "SELECT DISTINCT\n" +
                "    s.C02_SLOT_ID as slotId,\n" +
                "    s.C02_SLOT_DATE as slotDate, \n" +
                "    s.C02_SLOT_START_TIME as slotStartTime, \n" +
                "    s.C02_SLOT_END_TIME as slotEndTime, \n" +
                "    c.C01_COURSE_NAME as courseName, \n" +
                "    r.C18_ROOM_NAME as roomName, \n" +
                "    u_teacher.C14_NAME as teacherName\n" +
                "FROM \n" +
                "    t02_slot s \n" +
                "    JOIN t01_course c ON s.C02_COURSE_ID = c.C01_COURSE_ID \n" +
                "    JOIN t18_room r ON s.C02_ROOM_ID = r.C18_ROOM_ID \n" +
                "    JOIN t14_user u_teacher ON c.C01_TEACHER_ID = u_teacher.C14_USER_ID \n" +
                "    JOIN t03_center ct ON c.C01_CENTER_ID = ct.C03_CENTER_ID \n" +
                "WHERE \n" +
                "    ct.C03_CENTER_ID = :centerId\n" +
                "ORDER BY \n" +
                "    CASE WHEN courseName IS NULL THEN 1 ELSE 0 END, courseName, slotDate;";

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("centerId", centerId);

        List<Object[]> resultList = nativeQuery.getResultList();
        List<Map<String, Object>> slots = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> slotMap = new HashMap<>();
            slotMap.put("slotId", result[0]);
            slotMap.put("slotDate", ((java.sql.Date) result[1]).toLocalDate());
            slotMap.put("slotStartTime", ((java.sql.Time) result[2]).toLocalTime());
            slotMap.put("slotEndTime", ((java.sql.Time) result[3]).toLocalTime());
            slotMap.put("courseName", result[4]);
            slotMap.put("roomName", result[5]);
            slotMap.put("teacherName", result[6]);

            slots.add(slotMap);
        }

        return slots;
    }

    @Transactional
    @Override
    public Map<String, Object> getSlotsBySlotId(int slotId) {
        String query = "SELECT DISTINCT\n" +
                "    s.C02_SLOT_ID as slotId,\n" +
                "    s.C02_SLOT_DATE as slotDate, \n" +
                "    s.C02_SLOT_START_TIME as slotStartTime, \n" +
                "    s.C02_SLOT_END_TIME as slotEndTime, \n" +
                "    c.C01_COURSE_NAME as courseName, \n" +
                "    r.C18_ROOM_NAME as roomName, \n" +
                "    u_teacher.C14_NAME as teacherName\n" +
                "FROM \n" +
                "    t02_slot s \n" +
                "    JOIN t01_course c ON s.C02_COURSE_ID = c.C01_COURSE_ID \n" +
                "    JOIN t18_room r ON s.C02_ROOM_ID = r.C18_ROOM_ID \n" +
                "    JOIN t14_user u_teacher ON c.C01_TEACHER_ID = u_teacher.C14_USER_ID \n" +
                "WHERE \n" +
                "    s.C02_SLOT_ID = :slotId\n" +
                "ORDER BY \n" +
                "    CASE WHEN courseName IS NULL THEN 1 ELSE 0 END, courseName, slotDate;";

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("slotId", slotId);

        Object[] result = (Object[]) nativeQuery.getSingleResult();

        Map<String, Object> slotMap = new HashMap<>();
        slotMap.put("slotId", result[0]);
        slotMap.put("slotDate", ((java.sql.Date) result[1]).toLocalDate());
        slotMap.put("slotStartTime", ((java.sql.Time) result[2]).toLocalTime());
        slotMap.put("slotEndTime", ((java.sql.Time) result[3]).toLocalTime());
        slotMap.put("courseName", result[4]);
        slotMap.put("roomName", result[5]);
        slotMap.put("teacherName", result[6]);

        return slotMap;
    }

    @Override
    public Slot createSlot(SlotDto slotDto) {
        // Tìm roomId từ roomName
        Room room = roomRepository.findByName(slotDto.getRoomName())
                .orElseThrow(() -> new IllegalArgumentException("Room not found with name: " + slotDto.getRoomName()));

        // Tìm courseId từ courseCode hoặc courseName
        Course course = courseRepository.findByCode(slotDto.getCourseCode())
                .orElseThrow(() -> new IllegalArgumentException("Course not found with code or name: " + slotDto.getCourseCode()));

        // Kiểm tra xem phòng có sẵn cho khoảng thời gian này không (mình giả sử đã có hàm isRoomAvailable)
        if (!isRoomAvailable(room.getId(), slotDto.getSlotDate(), slotDto.getSlotStartTime(), slotDto.getSlotEndTime())) {
            throw new IllegalArgumentException("Room is not available for the given time slot.");
        }

        Slot slot = Slot.builder()
                .slotDate(slotDto.getSlotDate())
                .slotStartTime(slotDto.getSlotStartTime())
                .slotEndTime(slotDto.getSlotEndTime())
                .room(room)
                .course(course)
                .build();

        // Lưu slot vào database
        slot = slotRepository.save(slot);

        // Lấy danh sách học sinh trong khóa học
        List<User> studentsInCourse = getStudentsInCertainCourse(course.getId());

        // Cập nhật bảng StudentSlot cho từng học sinh
        for (User student : studentsInCourse) {
            StudentSlot studentSlot = StudentSlot.builder()
                    .student(student)
                    .slot(slot)
                    .attendanceStatus(false) // Mặc định là chưa điểm danh
                    .build();
            studentSlotRepository.save(studentSlot);
        }

        return slot;
    }

    private boolean isRoomAvailable(int roomId, Date slotDate, Date slotStartTime, Date slotEndTime) {
        String query = "SELECT COUNT(*) " +
                "FROM t02_slot " +
                "WHERE C02_ROOM_ID = :roomId " +
                "AND C02_SLOT_DATE = :slotDate " +
                "AND ((C02_SLOT_START_TIME < :slotEndTime AND C02_SLOT_END_TIME > :slotStartTime) " +
                "    OR (C02_SLOT_START_TIME >= :slotStartTime AND C02_SLOT_START_TIME < :slotEndTime) " +
                "    OR (C02_SLOT_END_TIME > :slotStartTime AND C02_SLOT_END_TIME <= :slotEndTime))";

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("roomId", roomId);
        // Convert slotDate to java.sql.Date
        java.sql.Date sqlSlotDate = new java.sql.Date(slotDate.getTime());
        nativeQuery.setParameter("slotDate", sqlSlotDate);
        // Convert slotStartTime to java.sql.Time
        java.sql.Time sqlSlotStartTime = new java.sql.Time(slotStartTime.getTime());
        nativeQuery.setParameter("slotStartTime", sqlSlotStartTime);
        // Convert slotEndTime to java.sql.Time
        java.sql.Time sqlSlotEndTime = new java.sql.Time(slotEndTime.getTime());
        nativeQuery.setParameter("slotEndTime", sqlSlotEndTime);

        int count = ((Number) nativeQuery.getSingleResult()).intValue();
        return count == 0;
    }

    @Override
    public Slot updateSlot(int slotId, SlotDto slotDto) {
        // Find the existing slot entity
        Slot existingSlot = slotRepository.findById(slotId)
                .orElseThrow(() -> new IllegalArgumentException("Slot not found with ID: " + slotId));

        // Tìm roomId từ roomName
        Room room = roomRepository.findByName(slotDto.getRoomName())
                .orElseThrow(() -> new IllegalArgumentException("Room not found with name: " + slotDto.getRoomName()));

        // Tìm courseId từ courseCode hoặc courseName
        Course course = courseRepository.findByCode(slotDto.getCourseCode())
                .orElseThrow(() -> new IllegalArgumentException("Course not found with code or name: " + slotDto.getCourseCode()));

        // Kiểm tra xem phòng có sẵn cho khoảng thời gian mới không (mình giả sử đã có hàm isRoomAvailable)
        if (!isRoomAvailableForUpdate(existingSlot.getId(), room.getId(), slotDto.getSlotDate(), slotDto.getSlotStartTime(), slotDto.getSlotEndTime())) {
            throw new IllegalArgumentException("Room is not available for the given time slot.");
        }

        // Update the existing slot entity
        existingSlot.setSlotDate(slotDto.getSlotDate());
        existingSlot.setSlotStartTime(slotDto.getSlotStartTime());
        existingSlot.setSlotEndTime(slotDto.getSlotEndTime());
        existingSlot.setRoom(room);
        existingSlot.setCourse(course);

        // Save and return the updated slot entity
        return slotRepository.save(existingSlot);
    }

    private boolean isRoomAvailableForUpdate(int slotId, int roomId, Date slotDate, Date slotStartTime, Date slotEndTime) {
        String query = "SELECT COUNT(*) " +
                "FROM t02_slot " +
                "WHERE C02_ROOM_ID = :roomId " +
                "AND C02_SLOT_DATE = :slotDate " +
                "AND C01_SLOT_ID != :slotId " + // Exclude the current slot being updated
                "AND ((C02_SLOT_START_TIME < :slotEndTime AND C02_SLOT_END_TIME > :slotStartTime) " +
                "    OR (C02_SLOT_START_TIME >= :slotStartTime AND C02_SLOT_START_TIME < :slotEndTime) " +
                "    OR (C02_SLOT_END_TIME > :slotStartTime AND C02_SLOT_END_TIME <= :slotEndTime))";

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("roomId", roomId);
        nativeQuery.setParameter("slotId", slotId); // Pass the slotId to exclude from count
        // Convert slotDate to java.sql.Date
        java.sql.Date sqlSlotDate = new java.sql.Date(slotDate.getTime());
        nativeQuery.setParameter("slotDate", sqlSlotDate);
        // Convert slotStartTime to java.sql.Time
        java.sql.Time sqlSlotStartTime = new java.sql.Time(slotStartTime.getTime());
        nativeQuery.setParameter("slotStartTime", sqlSlotStartTime);
        // Convert slotEndTime to java.sql.Time
        java.sql.Time sqlSlotEndTime = new java.sql.Time(slotEndTime.getTime());
        nativeQuery.setParameter("slotEndTime", sqlSlotEndTime);

        int count = ((Number) nativeQuery.getSingleResult()).intValue();
        return count == 0;
    }

    @Override
    @Transactional
    public void deleteSlot(int slotId) {
        // Find the slot entity by ID
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new IllegalArgumentException("Slot not found with ID: " + slotId));

        // Delete the slot entity
        slotRepository.delete(slot);
    }

    // -------------------------- CREATE OVERALL SLOTS FOR A CERTAIN COURSE ------------------------------
    // create overall slots for a certain course
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public void insertSlotsAndStudentSlots(List<SlotsDto> slotsDtos, String courseCode, String roomName) {
        // Log các tham số
        System.out.println("Course Code: " + courseCode);
        System.out.println("Room Name: " + roomName);

        // Log các tham số
        System.out.println("Course Code: [" + courseCode + "]");
        System.out.println("Room Name: [" + roomName + "]");

        // Lấy thông tin course và room
        Optional<Course> course = courseRepository.findByCode(courseCode.trim());
        if (course.isEmpty()) {
            throw new IllegalArgumentException("Course not found: " + courseCode);
        }

        int centerId = course.get().getCenter().getId();
        Optional<Room> room = roomRepository.findByRoomNameAndCenterId(roomName.trim(), centerId);
        if (room.isEmpty()) {
            throw new IllegalArgumentException("Room not found: " + roomName);
        }

        // Log kết quả tìm kiếm
        System.out.println("Course: " + course);
        System.out.println("Room: " + room);

        int courseId = course.get().getId();
        int roomId = room.get().getId();

        // Chèn slots vào cơ sở dữ liệu
        batchInsertSlots(slotsDtos, course.get().getStartDate(), course.get().getEndDate(), courseId, roomId);

        // Lấy danh sách slots và students
        List<Slot> slots = slotRepository.findByCourse_Id(courseId).orElse(Collections.emptyList());
        List<User> students = enrollmentRepository.findStudentsByCourseId(courseId);

        // Chèn dữ liệu vào bảng t17_student_slot
        batchInsertStudentSlot(students, slots);
    }

    @Transactional
    public void batchInsertSlots(List<SlotsDto> slotsDtos, Date courseStartDate, Date courseEndDate, int courseId, int roomId) {
        String sql = "INSERT INTO t02_slot (c02_slot_start_time, c02_slot_end_time, C02_COURSE_ID, C02_ROOM_ID, c02_slot_date) VALUES (?, ?, ?, ?, ?)";

        LocalDate localCourseStartDate = convertToLocalDate(courseStartDate);
        LocalDate localCourseEndDate = convertToLocalDate(courseEndDate);
        List<Object[]> batchArgs = createBatchArgs(slotsDtos, localCourseStartDate, localCourseEndDate, courseId, roomId);

        int batchSize = 10;
        for (int i = 0; i < batchArgs.size(); i += batchSize) {
            List<Object[]> batchArgsSubset = batchArgs.subList(i, Math.min(i + batchSize, batchArgs.size()));
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int k) throws SQLException {
                    Object[] args = batchArgsSubset.get(k);
                    LocalTime startTime = (LocalTime) args[1];
                    LocalTime endTime = (LocalTime) args[2];
                    LocalDate specificDate = (LocalDate) args[3];

                    ps.setTime(1, Time.valueOf(startTime)); // Start time as SQL time
                    ps.setTime(2, Time.valueOf(endTime)); // End time as SQL time
                    ps.setLong(3, (Integer) args[4]); // Course ID
                    ps.setLong(4, (Integer) args[5]); // Room ID
                    ps.setDate(5, java.sql.Date.valueOf(specificDate)); // Specific date for the slot
                }

                @Override
                public int getBatchSize() {
                    return batchArgsSubset.size();
                }
            });
        }
    }

    private List<Object[]> createBatchArgs(List<SlotsDto> slotDtos, LocalDate courseStartDate, LocalDate courseEndDate, int courseId, int roomId) {
        List<Object[]> batchArgs = new ArrayList<>();

        LocalDate currentDate = courseStartDate;
        while (!currentDate.isAfter(courseEndDate)) {
            for (SlotsDto slotDto : slotDtos) {
                // Tính toán ngày cụ thể trong tuần
                LocalDate specificDate = getSpecificDate(slotDto.getDayOfWeek(), currentDate);
                if (!specificDate.isAfter(courseEndDate)) {
                    Object[] args = { slotDto.getDayOfWeek(), slotDto.getStartTime(), slotDto.getEndTime(), specificDate, courseId, roomId };
                    batchArgs.add(args);
                }
            }
            currentDate = currentDate.plusWeeks(1); // Tăng ngày lên 1 tuần
        }

        return batchArgs;
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


    private LocalDate getSpecificDate(DayOfWeek dayOfWeek, LocalDate startDate) {
        // Tìm ngày đầu tiên trong tuần có ngày trùng với dayOfWeek
        LocalDate date = startDate;
        while (date.getDayOfWeek() != dayOfWeek) {
            date = date.plusDays(1); // Tăng ngày lên 1
        }
        return date;
    }

    //  ------------------- INSERT TABLE T17_STUDENT_SLOT KHI TẠO SLOTS -----------------
    @Transactional // Mark this method as transactional
    public void batchInsertStudentSlot(List<User> students, List<Slot> slots) {
        String sql = "INSERT INTO t17_student_slot (C17_STUDENT_ID, C17_SLOT_ID, c17_attendance_status) VALUES (?, ?, ?)";

        List<Object[]> batchArgs = createBatchStudentSlotArgs(students, slots);

        // Split batchArgs into smaller batches and perform batch update
        int batchSize = 100;
        for (int i = 0; i < batchArgs.size(); i += batchSize) {
            List<Object[]> batchArgsSubset = batchArgs.subList(i, Math.min(i + batchSize, batchArgs.size()));
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int k) throws SQLException {
                    Object[] args = batchArgsSubset.get(k);
                    ps.setInt(1, (Integer) args[0]); // Cast args[0] to Integer assuming it's C17_STUDENT_ID
                    ps.setInt(2, (Integer) args[1]); // Cast args[1] to Integer assuming it's C17_SLOT_ID
                    ps.setBoolean(3, (Boolean) args[2]); // Cast args[2] to Boolean assuming it's c17_attendance_status
                }

                @Override
                public int getBatchSize() {
                    return batchArgsSubset.size();
                }
            });
        }
    }

    private List<Object[]> createBatchStudentSlotArgs(List<User> students, List<Slot> slots) {
        List<Object[]> batchArgs = new ArrayList<>();

        for (User student : students) {
            for (Slot slot : slots) {
                Object[] args = { student.getId(), slot.getId(), false }; // Assuming getId() methods are present in User and Slot classes
                batchArgs.add(args);
            }
        }
        return batchArgs;
    }
    // ---------------------------------------------------------------------------

    public List<TeacherSalaryResponse> getTeacherSalaries(int month, int year, Long centerId) {
        String sql = "SELECT " +
                "    u.C14_USER_ID AS TeacherId, " +
                "    u.c14_name AS TeacherName, " +
                "    SUM(s.C02_SLOTS_COUNT * c.C01_SALARY_PER_SLOT) AS TotalSalary " +
                "FROM " +
                "    t14_user u " +
                "JOIN " +
                "    (SELECT " +
                "         c.C01_TEACHER_ID AS TeacherId, " +
                "         s.C02_COURSE_ID AS CourseId, " +
                "         COUNT(*) AS C02_SLOTS_COUNT " +
                "     FROM " +
                "         t01_course c " +
                "     JOIN " +
                "         t02_slot s ON c.C01_COURSE_ID = s.C02_COURSE_ID " +
                "     WHERE " +
                "         MONTH(s.c02_slot_date) = :month " +
                "         AND YEAR(s.c02_slot_date) = :year " +
                "     GROUP BY " +
                "         c.C01_TEACHER_ID, s.C02_COURSE_ID " +
                "    ) s ON u.C14_USER_ID = s.TeacherId " +
                "JOIN " +
                "    t01_course c ON s.CourseId = c.C01_COURSE_ID " +
                "WHERE " +
                "    c.C01_CENTER_ID = :centerId " +
                "GROUP BY " +
                "    u.C14_USER_ID, u.c14_name";

        Query query = entityManager.createNativeQuery(sql)
                .setParameter("month", month)
                .setParameter("year", year)
                .setParameter("centerId", centerId);

        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(result -> new TeacherSalaryResponse(
                        ((Number) result[0]).longValue(),
                        (String) result[1],
                        ((Number) result[2]).doubleValue()))
                .toList();
    }

    @Override
    public List<Map<String, Object>> getTotalTeacherSalary(int month, int year, Long centerId) {
        String sql = "SELECT " +
                "    :month AS Month, " +
                "    :year AS Year, " +
                "    SUM(s.C02_SLOTS_COUNT * c.C01_SALARY_PER_SLOT) AS TotalSalary " +
                "FROM " +
                "    t14_user u " +
                "JOIN " +
                "    (SELECT " +
                "         c.C01_TEACHER_ID AS TeacherId, " +
                "         s.C02_COURSE_ID AS CourseId, " +
                "         COUNT(*) AS C02_SLOTS_COUNT " +
                "     FROM " +
                "         t01_course c " +
                "     JOIN " +
                "         t02_slot s ON c.C01_COURSE_ID = s.C02_COURSE_ID " +
                "     WHERE " +
                "         MONTH(s.c02_slot_date) = :month " +
                "         AND YEAR(s.c02_slot_date) = :year " +
                "     GROUP BY " +
                "         c.C01_TEACHER_ID, s.C02_COURSE_ID " +
                "    ) s ON u.C14_USER_ID = s.TeacherId " +
                "JOIN " +
                "    t01_course c ON s.CourseId = c.C01_COURSE_ID " +
                "WHERE " +
                "    c.C01_CENTER_ID = :centerId ";

        Query query = entityManager.createNativeQuery(sql)
                .setParameter("month", month)
                .setParameter("year", year)
                .setParameter("centerId", centerId);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> results = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("Month", month);
            resultMap.put("Year", year);// Index 2 corresponds to TeacherId
            resultMap.put("TotalSalary", result[2]); // Index 3 corresponds to TotalSalary
            results.add(resultMap);
        }

        return results;



    }


    @Override
    public Map<String, Object> getMonthlyRevenue(int month, int year, Long centerId) {
        String sql = "SELECT " +
                "    SUM(c.C01_COURSE_FEE) AS TotalRevenue " +
                "FROM " +
                "    t08_bill b " +
                "JOIN " +
                "    t15_enrollment e ON b.c08_enrollment_id = e.C15_ENROLLMENT_ID " +
                "JOIN " +
                "    t01_course c ON e.C15_COURSE_ID = c.C01_COURSE_ID " +
                "WHERE " +
                "    c.C01_CENTER_ID = :centerId " +
                "    AND b.c08_status = 1 " + // Assuming status 1 indicates a paid bill
                "    AND MONTH(b.C08_CREATED_AT) = :month " +
                "    AND YEAR(b.C08_CREATED_AT) = :year ";

        Query query = entityManager.createNativeQuery(sql)
                .setParameter("month", month)
                .setParameter("year", year)
                .setParameter("centerId", centerId);

        Object result = query.getSingleResult();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("Month", month);
        resultMap.put("Year", year);
        resultMap.put("TotalRevenue", result != null ? result : BigDecimal.ZERO); // Handle null result

        return resultMap;
    }

//    public Double getMonthlyProfit(int month, int year, Long centerId) {
//        Double revenue = getMonthlyRevenue(month, year, centerId);
//        List<Map<String, Object>> totalTeacherSalary = getTotalTeacherSalary(month, year, centerId);
//        return revenue - totalTeacherSalary;
//    }

    @Override
    public Map<String, Object> getMonthlyProfit(int month, int year, Long centerId) {
        // Get total monthly revenue
        Map<String, Object> revenueMap = getMonthlyRevenue(month, year, centerId);
        BigDecimal revenue = (BigDecimal) revenueMap.getOrDefault("TotalRevenue", BigDecimal.ZERO);

        // Get total monthly salary for teachers
        List<Map<String, Object>> teacherSalaryList = getTotalTeacherSalary(month, year, centerId);
        BigDecimal totalTeacherSalary = teacherSalaryList.stream()
                .map(entry -> (BigDecimal) entry.getOrDefault("TotalSalary", BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate monthly profit
        BigDecimal profit = revenue.subtract(totalTeacherSalary);

        // Prepare the result map
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("Month", month);
        resultMap.put("Year", year);
        resultMap.put("MonthlyProfit", profit);

        return resultMap;
    }

    @Override
    public List<Map<String, Object>> getTotalTeacherSalaryForYear(int year, Long centerId) {
        List<Map<String, Object>> results = new ArrayList<>();

        String sql = "SELECT " +
                "    MONTH(s.c02_slot_date) AS Month, " +
                "    :year AS Year, " +
                "    SUM(s.C02_SLOTS_COUNT * c.C01_SALARY_PER_SLOT) AS TotalSalary " + // Sử dụng cột C01_COURSE_FEE thay vì C01_SALARY_PER_SLOT
                "FROM " +
                "    t14_user u " +
                "JOIN " +
                "    (SELECT " +
                "         c.C01_TEACHER_ID AS TeacherId, " +
                "         s.C02_COURSE_ID AS CourseId, " +
                "         COUNT(*) AS C02_SLOTS_COUNT, " +
                "         s.c02_slot_date " +
                "     FROM " +
                "         t01_course c " +
                "     JOIN " +
                "         t02_slot s ON c.C01_COURSE_ID = s.C02_COURSE_ID " +
                "     WHERE " +
                "         YEAR(s.c02_slot_date) = :year " +
                "     GROUP BY " +
                "         c.C01_TEACHER_ID, s.C02_COURSE_ID, s.c02_slot_date " +
                "    ) s ON u.C14_USER_ID = s.TeacherId " +
                "JOIN " +
                "    t01_course c ON s.CourseId = c.C01_COURSE_ID " +
                "WHERE " +
                "    c.C01_CENTER_ID = :centerId " +
                "GROUP BY " +
                "    MONTH(s.c02_slot_date), YEAR(s.c02_slot_date)";

        Query query = entityManager.createNativeQuery(sql)
                .setParameter("year", year)
                .setParameter("centerId", centerId);

        List<Object[]> resultList = query.getResultList();

        Map<Integer, Double> monthlySalaries = new HashMap<>();

        for (Object[] result : resultList) {
            int month = ((Number) result[0]).intValue();
            double totalSalary = ((Number) result[2]).doubleValue();

            monthlySalaries.put(month, monthlySalaries.getOrDefault(month, 0.0) + totalSalary);
        }

        for (Map.Entry<Integer, Double> entry : monthlySalaries.entrySet()) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("Month", entry.getKey());
            resultMap.put("Year", year);
            resultMap.put("TotalSalary", String.format("%.2f", entry.getValue())); // Format TotalSalary as a string with 2 decimal places
            results.add(resultMap);
        }

        for (int month = 1; month <= 12; month++) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("Month", month);
            resultMap.put("Year", year);
            resultMap.put("TotalSalary", monthlySalaries.getOrDefault(month, 0.0));
            results.add(resultMap);
        }

        return results;
    }

}


