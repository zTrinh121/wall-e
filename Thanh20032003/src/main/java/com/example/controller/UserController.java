package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.UserService;
import com.example.service.impl.UserServiceImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, @RequestParam int roleId, Model model, HttpSession session) {
        if (userService.authenticateUser(username, password, roleId)) {
            User user = userService.findByUsername(username);
            session.setAttribute("user", user);

            String roleDesc = user.getRole().getDescription().name();
            System.out.println("Role Description: " + roleDesc);

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
                    return "redirect:/manager-dashboard";
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

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
     //   String test = session.getId();
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "profile";
    }

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
        model.addAttribute("user", new User());
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
        model.addAttribute("user", new User());
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, @RequestParam int userCode, Model model, HttpSession session) {
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
        model.addAttribute("user", new User());
        List<Role> roles = userService.findAllRoles();
        model.addAttribute("roles", roles);
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String email, @RequestParam int userCode, @RequestParam int roleId,
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

    @GetMapping("/profile-admin")
    public String profile(HttpSession session) {
        session.invalidate();
        return "profile-admin";
    }

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

}
