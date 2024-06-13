package com.example.SWP391_Project.service;

import com.example.SWP391_Project.model.Role;
import com.example.SWP391_Project.model.Slot;
import com.example.SWP391_Project.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {

    void saveUser(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    Role findRoleById(int id);

    List<Role> findAllRoles();

    boolean authenticateUser(String username, String password, int roleId);

    List<User> findAllUsers();

    void sendVerificationCode(User user);

    void sendPasswordResetCode(User user);

    void updateProfileImage(User user, MultipartFile image) throws IOException;

    User findByEmailAndCode(String email, String code);

    long countByRole_Id(int roleId);

    List<User> getAllUsers();

    List<User> findUsersByRole(int roleId);

    void updateUserStatus(int userId, boolean status);

    static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
    }


    User authenticateUser(String username, String password);

    boolean sendEmail(String toEmail, String verificationCode);

    String generateVerificationCode();

    //int getVerificationCode();

    List<Map<String, Object>> getUsersByRoleId(int roleId);
    List<Map<String, Object>> getAllUsersWithSpecificAttributes();




}
