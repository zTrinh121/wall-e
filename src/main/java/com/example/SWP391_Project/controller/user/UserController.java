package com.example.SWP391_Project.controller.user;

import com.example.SWP391_Project.model.Role;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.service.UserService;
import jakarta.mail.*;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import org.apache.catalina.Group;

import org.apache.catalina.UserDatabase;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

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
//            session.setAttribute("userId", user.getId());//***

            System.out.println( session.getAttribute("authid"));
            String roleDesc = user.getRole().getDescription().name();

            switch (roleDesc) {
                case "ADMIN":
                    return "redirect:/admin";
                case "STUDENT":
                    return "redirect:/student-dashboard";
                case "PARENT":
                    return "parent-dashboard";
                case "TEACHER":
                    return "redirect:/teacher-dashboard";
                case "MANAGER":
                    return "managerHome";
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


    @PostMapping("/profile-image")
    public String updateProfileImage(@RequestParam("image") MultipartFile image, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        try {
            userService.updateProfileImage(user, image);
            session.setAttribute("user", user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam String name, @RequestParam String email, HttpSession session) {
        User currenUser = (User) session.getAttribute("user");
        if (currenUser == null) {
            return "redirect:/login";
        }
        currenUser.setName(name);
        currenUser.setEmail(email);
        currenUser.setPhone(currenUser.getPhone());
        userService.saveUser(currenUser);
        session.setAttribute("userrr", currenUser);
        return "redirect:/profile";
    }

    @GetMapping("/register")
    public String register(Model model) {
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
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, @RequestParam int roleId, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }
        Role role = userService.findRoleById(roleId);
        user.setRole(role);
        user.setStatus(true);  // Skip email verification
        userService.saveUser(user);
        // userService.sendVerificationCode(user);  // Skip sending verification code
        return "redirect:/login";  // Redirect to login after registration
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

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String newPassword, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            userService.saveUser(user);
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

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String email, @RequestParam String userCode, @RequestParam int roleId,
                                 @RequestParam String currentPassword, @RequestParam String newPassword, HttpSession session, Model model) {
        User user = userService.findByEmailAndCode(email, userCode);
        if (user == null || user.getRole().getId() != roleId || !BCrypt.checkpw(currentPassword, user.getPassword())) {
            model.addAttribute("error", "Invalid credentials");
            return "change-password";
        }
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        userService.saveUser(user);
        session.invalidate(); // Invalidate session after password change
        return "redirect:/login";
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
        session.invalidate();
        return "accountManagement";
    }

    @GetMapping("/approveManagement")
    public String approveManagement(HttpSession session) {
        session.invalidate();
        return "approveManagement";
    }

    @GetMapping("/centerManagement")
    public String centerManagement(HttpSession session) {
        session.invalidate();
        return "adminCenterManagement";
    }

    @GetMapping("/profile-student")
    public String profileStudent(HttpSession session) {
        session.invalidate();
        return "profile-student";
    }

    @GetMapping("/student-dashboard")
    public String studentDashboard(HttpSession session) {
        session.invalidate();
        return "student-dashboard";
    }
    @GetMapping("/manager-dashboard")
    public String managerDashboard(HttpSession session) {
        session.invalidate();
        return "managerHome";
    }

    @GetMapping("/student-classList")
    public String studentClassList(HttpSession session) {
        session.invalidate();
        return "student-classList";
    }

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
    public String detailCourse(HttpSession session) {
        session.invalidate();
        return "student-classListDetails";
    }

    @GetMapping("/student-timetable")
    public String viewTimetable(HttpSession session) {
        session.invalidate();
        return "student-timetable";
    }

    @GetMapping("/student-notification")
    public String viewNotification(HttpSession session) {
        session.invalidate();
        return "studentNotification";
    }

    @GetMapping("/parent-timetable")
    public String viewTimetableParent(HttpSession session) {
        session.invalidate();
        return "parent-timetable";
    }

    @GetMapping("/parent-notification")
    public String viewNotificationParent(HttpSession session) {
        session.invalidate();
        return "parentNotification";
    }

    @GetMapping("/parent")
    public String viewDashboardParent(HttpSession session) {
        session.invalidate();
        return "parent-dashboard";
    }


}
