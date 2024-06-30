package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.model.Center;
import com.example.SWP391_Project.model.Role;
import com.example.SWP391_Project.model.Slot;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.repository.RoleRepository;
import com.example.SWP391_Project.repository.SlotRepository;
import com.example.SWP391_Project.repository.UserRepository;
import com.example.SWP391_Project.response.CloudinaryResponse;
import com.example.SWP391_Project.service.UserService;
import java.util.UUID;
import com.example.SWP391_Project.utils.FileUploadUtil;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.SecureRandom;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CloudinaryService cloudinaryService;

    private int verificationCode;
    @Autowired
    private SlotRepository slotRepository;

    @Override
    public void saveUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
//    public User findById(int id){
//        return userRepository.findById(id);
//    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Role findRoleById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public boolean authenticateUser(String username, String password, int roleId) {
        User user = findByUsername(username);
        return user != null && BCrypt.checkpw(password, user.getPassword()) && user.getRole().getId() == roleId && user.isStatus();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public List<Map<String, Object>> getUsersByRoleId(int roleId) {
        String query = "SELECT u.C14_USER_ID as userId, u.C14_ROLE_ID as roleId, u.C14_USER_CODE as userCode,  u.c14_acc_status as accStatus " +
                "FROM t14_user u " +
                "WHERE u.C14_ROLE_ID = ?";
        return jdbcTemplate.queryForList(query, roleId);
    }

    @Override
    @Transactional
    public List<Map<String, Object>> getAllUsersWithSpecificAttributes() {
        String query = "SELECT u.C14_USER_ID as userId, u.C14_ROLE_ID as roleId, u.C14_USER_CODE as userCode,  u.c14_acc_status as accStatus " +
                "FROM t14_user u";
        return jdbcTemplate.queryForList(query);
    }

    @Override
    public User uploadProfileImage(final int userId, final MultipartFile file) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found !!!"));
        FileUploadUtil.assertAllowedImage(file);
        final String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
        final CloudinaryResponse response = this.cloudinaryService.uploadImageFile(file, fileName);
        user.setProfileImage(response.getUrl());
        user.setCloudinaryImageId(response.getPublicId());
        this.userRepository.save(user);
        return user;
    }

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (var inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }
    }

    @Override
    public User findByEmailAndCode(String email, String code) {
        return userRepository.findByEmailAndCode(email, code);
    }

    @Override
    public long countByRole_Id(int roleId) {
        return userRepository.countByRole_Id(roleId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findUsersByRole(int roleId) {
        return userRepository.findByRole_Id(roleId);
    }

    @Override
    public void updateUserStatus(int userId, boolean status) {
        userRepository.updateUserStatus(userId, status);
    }

    @Override
    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean sendEmail(String toEmail, String verificationCode) {
        boolean test = false;
        String fromEmail = "thanhdhde170795@fpt.edu.vn";
        String password = "redm djng jorn pqcv";

        try {
            Properties pr = new Properties();
            pr.put("mail.smtp.host", "smtp.gmail.com");
            pr.put("mail.smtp.port", "465");
            pr.put("mail.smtp.auth", "true");
            pr.put("mail.smtp.starttls.enable", "true");
            pr.put("mail.smtp.ssl.protocols", "TLSv1.2");
            pr.put("mail.smtp.socketFactory.port", "465");
            pr.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(pr, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message mess = new MimeMessage(session);
            mess.setFrom(new InternetAddress(fromEmail));
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mess.setSubject("Verify Email");
            mess.setContent("This is your verification code: " + verificationCode, "text/plain");

            Transport.send(mess);
            test = true;

        } catch (Exception e) {
            System.out.println(e);
        }

        return test;
    }
    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

//    @Override
//    public void sendVerificationCode(User user) {
//        String code = generateVerificationCode();
//        user.setVerificationCode(code);
//        userRepository.save(user);
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(user.getEmail());
//        message.setSubject("Your Verification Code");
//        message.setText("Your verification code is: " + code);
//        mailSender.send(message);
//    }
//
//    @Override
//    public void sendPasswordResetCode(User user) {
//        String code = generateVerificationCode();
//        user.setVerificationCode(code);
//        userRepository.save(user);
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(user.getEmail());
//        message.setSubject("Your Password Reset Code");
//        message.setText("Your password reset code is: " + code);
//        mailSender.send(message);
//    }
//
//


    public String generateUserCode() {
        String uniqueID = UUID.randomUUID().toString().substring(0, 8); // Tạo mã ngẫu nhiên gồm 8 ký tự
        return "USER" + uniqueID; // Tiền tố 'USER' được thêm vào trước mã
    }



    @Transactional
    public void updateParentIdByEmail(String email, int parentId) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            User parentUser = userRepository.findById(parentId).orElse(null);
            if (parentUser != null) {
                user.setParent(parentUser);
                userRepository.save(user);
            }
        }
    }


    @Override
    @Transactional
    public void updateParentIdById(int userId, int parentId) {
        User user = userRepository.findById(userId).orElse(null);
        User parentUser = userRepository.findById(parentId).orElse(null);
        if (user != null && parentUser != null) {
            user.setParent(parentUser);
            userRepository.save(user);
        }
    }



//    @Override
//    public int getVerificationCode() {
//        return verificationCode;
//    }

//    @Override
//    public User findById(int id) {
//        return userRepository.findById(id).orElse(null);
//    }
}
