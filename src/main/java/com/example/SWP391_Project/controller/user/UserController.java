package com.example.SWP391_Project.controller.user;

import com.example.SWP391_Project.model.Role;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.service.EmailService;
import com.example.SWP391_Project.service.TeacherService;
import com.example.SWP391_Project.service.UserService;
import com.example.SWP391_Project.service.impl.EmailServiceImpl;
import jakarta.mail.*;

import java.time.LocalDate;
import java.util.*;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import org.apache.catalina.Group;

import org.apache.catalina.UserDatabase;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailServiceImpl emailServiceImpl;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String login(Model model) {
        List<Role> roles = userService.findAllRoles();
        model.addAttribute("roles", roles);
        return "login";
    }
    // -------------- Gửi mail
    public boolean sendEmail(Email email) {
        boolean test = false;

        String toEmail = ""; // lấy email người dùng ra
        String code = "rendom ra";
        String fromEmail = "thanhdhde170795@fpt.edu.vn";
        String password = "pwpi acpp wcqr hion";

        try {

            // your host email smtp server details
            Properties pr = new Properties();
            // pr.put("mail.smtp.host", "smtp.gmail.com");
            // pr.put("mail.smtp.port", "587");
            // pr.put("mail.smtp.auth", "true");
            // pr.put("mail.smtp.starttls.enable", "true");
            // pr.put("mail.smtp.socketFactory.port", "587");
            // pr.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            pr.put("mail.smtp.host", "smtp.gmail.com");
            pr.put("mail.smtp.port", "465");
            pr.put("mail.smtp.auth", "true");
            pr.put("mail.smtp.starttls.enable", "true");
            pr.put("mail.smtp.ssl.protocols", "TLSv1.2");
            pr.put("mail.smtp.socketFactory.port", "465");
            pr.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            // get session to authenticate the host email address and password

            Session session = Session.getInstance(pr, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            // set email message details
            Message mess = new MimeMessage(session);

            // set from email address
            mess.setFrom(new InternetAddress(fromEmail));
            // set to email address or destination email address
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mess.setSubject("Verify Email");
            mess.setContent("This is verify code: " + code, "text/plain");

            Transport.send(mess);

            test = true;

        } catch (Exception e) {
            System.out.println(e);
        }

        return test;
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, @RequestParam int roleId, Model model, HttpSession session) {
        if (userService.authenticateUser(username, password, roleId)) {
            User user = userService.findByUsername(username);
            session.setAttribute("authid", user.getId());
            session.setAttribute("user", user);
//            session.setAttribute("userId", user.getId());//***

            System.out.println( session.getAttribute("authid"));
            String roleDesc = user.getRole().getDescription().name();

            switch (roleDesc) {
                case "ADMIN":
                    return "redirect:/admin";
                case "STUDENT":
                    return "redirect:/student-dashboard";
                case "PARENT":
                    return "redirect:/parent-dashboard";
                case "TEACHER":
                    return "redirect:/teacher-dashboard";
                case "MANAGER":
                    return "redirect:/manager/managerHome";
                default:
                    return "redirect:/login";
            }
        }

        List<Role> roles = userService.findAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("roleId", roleId);
        model.addAttribute("error", "Invalid username, password, or role");
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        String role = user.getRole().getDescription().name();
        switch (role){
            case "PARENT":
                return "redirect:/parent-dashboard";
            case "STUDENT":
                return "redirect:/student-dashboard";
            case "TEACHER":
                return "redirect:/teacher-dashboard";
        }

        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/profile-admin")
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //   String test = session.getId();
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "profile-admin";
    }

//    @GetMapping("/profile")
//    public String profile(Model model, HttpSession session)
//    {
//      User user = (User) session.getAttribute("user");
//           String test = session.getId();
//           if (user == null) { return "redirect:/login";
//           }
//           model.addAttribute("user", user);
//           return "profile";
//           }


//    @PostMapping("/profile-image")
//    public String updateProfileImage(@RequestParam("image") MultipartFile image, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            return "redirect:/login";
//        }
//        try {
//            userService.updateProfileImage(user, image);
//            session.setAttribute("user", user);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "redirect:/profile";
//    }

    @PostMapping("/profile-image")
    public String updateProfileImage(@RequestParam("files") MultipartFile image, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            User updatedUser = userService.uploadProfileImage(user.getId(), image);
            user.setProfileImage(updatedUser.getProfileImage());
            user.setCloudinaryImageId(updatedUser.getCloudinaryImageId());
            session.setAttribute("user", user);
            switch (user.getRole().getDescription().name()){
                case "STUDENT":
                    return "redirect:/student-profile";
                case "TEACHER":
                    return "redirect:/profile";
                case "PARENT":
                    return "redirect:/profile-parent";
                case "ADMIN":
                    return "redirect:/adminProfile";
                default:
                    return "redirect:/login";
            }


        } catch (Exception e) {
            e.printStackTrace();
//            session.setAttribute("profileImageError", "Failed to update profile image");

        }
        return "redirect:/login";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam String name, @RequestParam String email, @RequestParam String address, @RequestParam String phone, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }
        currentUser.setName(name);
        currentUser.setEmail(email);
        currentUser.setAddress(address);
        currentUser.setPhone(phone);
        currentUser.setDob(java.sql.Date.valueOf(dob));  // Chuyển đổi LocalDate sang java.sql.Date
        userService.saveUser(currentUser);
        session.setAttribute("user", currentUser);
        return "redirect:/profile";
    }


    @GetMapping("/register")
    public String register(Model model) {
        List<Role> roles = userService.findAllRoles();
        model.addAttribute("roles", roles);
        return "register";
    }
    /// test
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> registerUser(@ModelAttribute User user, @RequestParam int roleId, Model model, HttpSession session) {
        Map<String, String> errors = new HashMap<>();

        // Kiểm tra tên người dùng đã tồn tại
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.put("usernameError", "Tên người dùng đã tồn tại");
        }

        // Kiểm tra email đã tồn tại
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.put("emailError", "Email đã tồn tại");
        }

        // Kiểm tra số điện thoại đã tồn tại
        if (userService.findByPhone(user.getPhone()) != null) {
            errors.put("phoneError", "Số điện thoại đã được sử dụng");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        // Tạo và gán mã người dùng
        String userCode = userService.generateUserCode();
        user.setCode(userCode);

        Role role = userService.findRoleById(roleId);
        user.setRole(role);
        user.setStatus(false);

        // Tạo mã xác nhận và gửi email
        String verificationCode = userService.generateVerificationCode();
        session.setAttribute("userToRegister", user);
        session.setAttribute("verificationCode", verificationCode);
        userService.sendEmail(user.getEmail(), verificationCode);

        return ResponseEntity.ok().body(Map.of("redirect", "/verify-email"));
    }








    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String code, HttpSession session, Model model) {
        User user = (User) session.getAttribute("userToRegister");
        String storedCode = (String) session.getAttribute("verificationCode");

        if (user != null && storedCode != null && storedCode.equals(code)) {
            user.setStatus(true);  // Xác nhận thành công, cập nhật trạng thái người dùng
            userService.saveUser(user);
            session.removeAttribute("verificationCode");
            session.removeAttribute("userToRegister");
            return "redirect:/login";  // Chuyển hướng tới trang đăng nhập sau khi đăng ký thành công
        } else {
            model.addAttribute("error", "Mã xác nhận không hợp lệ");
            return "verify-email";
        }
    }


    @GetMapping("/verify-email")
    public String verifyEmail() {
        return "verify-email";  // Trả về trang xác nhận email
    }


    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("user", new org.apache.catalina.User() {
            @Override
            public String getFullName() {
                return "";
            }

            @Override
            public void setFullName(String s) {

            }

            @Override
            public Iterator<Group> getGroups() {
                return null;
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public void setPassword(String s) {

            }

            @Override
            public Iterator<org.apache.catalina.Role> getRoles() {
                return null;
            }

            @Override
            public UserDatabase getUserDatabase() {
                return null;
            }

            @Override
            public String getUsername() {
                return "";
            }

            @Override
            public void setUsername(String s) {

            }

            @Override
            public void addGroup(Group group) {

            }

            @Override
            public void addRole(org.apache.catalina.Role role) {

            }

            @Override
            public boolean isInGroup(Group group) {
                return false;
            }

            @Override
            public boolean isInRole(org.apache.catalina.Role role) {
                return false;
            }

            @Override
            public void removeGroup(Group group) {

            }

            @Override
            public void removeGroups() {

            }

            @Override
            public void removeRole(org.apache.catalina.Role role) {

            }

            @Override
            public void removeRoles() {

            }

            @Override
            public String getName() {
                return "";
            }
        });
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, @RequestParam String userCode, Model model, HttpSession session) {
        User user = userService.findByEmailAndCode(email, userCode);
        if (user == null) {
            model.addAttribute("error", "No user found with this email and code");
            return "forgot-password";
        }
        session.setAttribute("user", user);
        return "redirect:/reset-password";
    }

    @GetMapping("/reset-password")
    public String resetPassword(Model model) {
        model.addAttribute("newPassword", "");
        return "reset-password";
    }

    // làm
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String newPassword, @RequestParam String confirmPassword, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp");
                return "reset-password";
            }

            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            userService.saveUser(user);
            session.invalidate();  // Hủy session hiện tại để yêu cầu đăng nhập lại
            return "redirect:/login";
        }
        model.addAttribute("error", "Invalid reset process");
        return "reset-password";
    }


    @GetMapping("/change-password")
    public String changePassword(Model model) {
        model.addAttribute("user", new org.apache.catalina.User() {
            @Override
            public String getFullName() {
                return "";
            }

            @Override
            public void setFullName(String s) {

            }

            @Override
            public Iterator<Group> getGroups() {
                return null;
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public void setPassword(String s) {

            }

            @Override
            public Iterator<org.apache.catalina.Role> getRoles() {
                return null;
            }

            @Override
            public UserDatabase getUserDatabase() {
                return null;
            }

            @Override
            public String getUsername() {
                return "";
            }

            @Override
            public void setUsername(String s) {

            }

            @Override
            public void addGroup(Group group) {

            }

            @Override
            public void addRole(org.apache.catalina.Role role) {

            }

            @Override
            public boolean isInGroup(Group group) {
                return false;
            }

            @Override
            public boolean isInRole(org.apache.catalina.Role role) {
                return false;
            }

            @Override
            public void removeGroup(Group group) {

            }

            @Override
            public void removeGroups() {

            }

            @Override
            public void removeRole(org.apache.catalina.Role role) {

            }

            @Override
            public void removeRoles() {

            }

            @Override
            public String getName() {
                return "";
            }
        });
        List<Role> roles = userService.findAllRoles();
        model.addAttribute("roles", roles);
        return "change-password";
    }
    // làm

    private String validatePassword(String password) {
        if (password.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự.";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Mật khẩu phải có ít nhất một chữ cái viết thường.";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Mật khẩu phải có ít nhất một chữ cái viết hoa.";
        }
        if (!password.matches(".*\\d.*")) {
            return "Mật khẩu phải có ít nhất một chữ số.";
        }
        if (!password.matches(".*[@#$%^&+=].*")) {
            return "Mật khẩu phải có ít nhất một ký tự đặc biệt (@#$%^&+=).";
        }
        return "";  // Trả về chuỗi rỗng nếu không có lỗi nào
    }



    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String confirmPassword, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu hiện tại không đúng");
            return "change-password";
        }
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp");
            return "change-password";
        }

        String passwordError = validatePassword(newPassword);
        if (!passwordError.isEmpty()) {
            model.addAttribute("error", passwordError);
            return "change-password";
        }

        // Thực hiện hash mật khẩu mới và cập nhật
        String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPassword(newHash);
        userService.saveUser(user);

        return "redirect:/login";  // Chuyển hướng người dùng đến trang đăng nhập sau khi cập nhật thành công
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
//
//    @GetMapping("/profile-admin")
//    public String profile(HttpSession session) {
//        session.invalidate();
//        return "profile-admin";
//    }

    @GetMapping("/accountManagement")
    public String accountManagement(HttpSession session) {
        return "accountManagement";
    }

    @GetMapping("/approveManagement")
    public String approveManagement(HttpSession session) {
        return "approveManagement";
    }

    @GetMapping("/centerManagement")
    public String centerManagement(HttpSession session) {
        return "adminCenterManagement";
    }
    @GetMapping("/verify-emaill")
    public String verifyEmaill(HttpSession session) {
        session.invalidate();
        return "verify-emaill";
    }

    @GetMapping("/student-profile")
    public String profileStudent(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "studentProfile";
    }

    @GetMapping("/student-dashboard")
    public String studentDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "student-dashboard";
    }
    @GetMapping("/manager-dashboard")
    public String managerDashboard(HttpSession session) {
        return "managerHome";
    }

    @GetMapping("/guest-teacher")
    public String guestTeacher(HttpSession session) {
        session.invalidate();
        return "guest-teacher";
    }

    @GetMapping("/tkb-teacher")
    public String tkbTeacher(HttpSession session) {
        session.invalidate();
        return "tkb-teacher";
    }

    @GetMapping("/teacherFragments")
    public String teacherFragments(HttpSession session) {
        session.invalidate();
        return "teacherFragments";
    }

//    @GetMapping("/teacher-notification")
//    public String teacherNotification(HttpSession session) {
//        session.invalidate();
//        return "teacher-notification";
//    }

    @GetMapping("/detailCenter-teacher")
    public String detailCenterTeacher(HttpSession session) {
        session.invalidate();
        return "detailCenter-teacher";
    }

    @GetMapping("/student-classList")
    public String studentClassList(HttpSession session) {
        return "student-classList";
    }


//    @GetMapping("/teacher-dashboard")
//    public String teacherDashboard(HttpSession session) {
//        session.invalidate();
//        return "teacher-dashboard";
//    }

    @GetMapping("/search-in-student")
    public String search(HttpSession session) {
        session.invalidate();
        return "search-in-student";
    }


    @PostMapping("/users/updateStatus")
    @ResponseBody
    public void updateUserStatus(@RequestParam int userId, @RequestParam boolean status) {
        userService.updateUserStatus(userId, status);
    }

    @GetMapping("/course-details")
    public String detailCourse(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "classRegisteredDetail";
    }

    @GetMapping("/timetable")
    public String viewTimetable(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "timetableViewOnly";
    }

//    @GetMapping("/student-notification")
//    public String viewNotification(HttpSession session, Model model) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            return "redirect:/login";
//        }
//        model.addAttribute("user", user);
//        return "studentNotification";
//    }


//    @GetMapping("/parent-notification")
//    public String viewNotificationParent(Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            return "redirect:/login";
//        }
//        model.addAttribute("user", user);
//
//        return "parentNotification";
//    }

    @GetMapping("/parent")
    public String viewDashboardParent(HttpSession session) {
        session.invalidate();
        return "parent-dashboard";
    }

    @GetMapping("/teacher-studentDetail")
    public String teacherStudentDetail(HttpSession session) {
        session.invalidate();
        return "teacher-studentDetail";
    }

    @GetMapping("/search")
    public String searchAll(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "search";
    }

    @GetMapping("/mapping")
    public String mapping(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        return "mapping";
    }

    @GetMapping("/bill")
    public String Bill(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        return "bill";
    }

    @GetMapping("/profile")
    public String profileParent(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        System.out.println(user.getRole().getDescription().name() + " vai trò của người dùng");
        switch (user.getRole().getDescription().name()){
            case "STUDENT":
                return "studentProfile";
            case "TEACHER":
                return "teacherProfile";
            case "PARENT":
                return "parentProfile";
            case "ADMIN":
                return "adminProfile";
            default:
                return "redirect:/login";
        }

    }

    @GetMapping("/fragement")
    public String fragmentParent(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "fragments";
    }

    @GetMapping("/profile-parent")
    public String profiletParent(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        System.out.println(user.getProfileImage()+" ảnh của user");
        model.addAttribute("user", user);
        return "parentProfile";
    }

    @GetMapping("/parent-dashboard")
    public String parentDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "parent-dashboard";
    }

    @GetMapping("/billFail")
    public String billFail(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "billFail";
    }

    @GetMapping("/teacher-dashboard")
    public String teacherDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "teacher-dashboard";
    }

    @GetMapping("/material")
    public String material(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "material";
    }


    @GetMapping("/material-create")
    public String materialCreate(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "material-create";
    }

    @GetMapping("/notification")
    public String notification(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "notificationDetail";
    }

    @GetMapping("/material-create-select")
    public String materialCreateSelect(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "material-create-select";
    }


    @PostMapping("PDF/File/upload")
    public String uploadMaterialPdf(
            @RequestParam("file") MultipartFile file,
            @RequestParam("materialsName") String materialsName,
            @RequestParam("subjectName") String subjectName,
            HttpSession httpSession) {

        try {
            User user = (User) httpSession.getAttribute("user");

            if (user == null) {
                return "redirect:/login";
            }
            if (!file.getContentType().equals("application/pdf")) {
                // Handle the case where the uploaded file is not a PDF
                System.err.println("Only PDF files are allowed.");
                return "redirect:/material-create?status=invalid_file_type";
            }
            teacherService.uploadPdfFile(file, subjectName, materialsName, user);

            return "redirect:/material";
        } catch (Exception e) {
            System.err.println("Error uploading PDF: " + e.getMessage());
            return "redirect:/material";
        }
    }

    @GetMapping("/material-detail")
    public String materialDetail(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "material-detail";
    }









}
