package com.example.service;

import com.example.model.Role;
import com.example.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    User findByEmailAndCode(String email, int code);

    long countByRole_Id(int roleId);

    List<User> getAllUsers();

    List<User> findUsersByRole(int roleId);

    void updateUserStatus(int userId, boolean status);

    static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        // This method cannot be part of the interface; it needs to be part of the implementing class
    }
}
