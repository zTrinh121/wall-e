package com.example.SWP391_Project.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "t14_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C14_USER_ID")
    int id;

    @Column(name = "C14_USER_USERNAME", nullable = false)
    String username;

    @Column(name = "C14_USER_PASSWORD", nullable = false)
    String password;

    @Column(name = "C14_ACC_STATUS", nullable = false)
    boolean status;

    @Column(name = "C14_USER_CODE", nullable = false)
    String code;

    @Column(name = "C14_USER_NAME", nullable = true)
    String name;

    @Column(name = "C14_USER_PHONE", nullable = false)
    String phone;

    @Column(name = "C14_USER_ADDRESS", nullable = true)
    String address;

    @Column(name = "C14_USER_DOB", nullable = true)
    Date dob;

    @Column(name = "C14_USER_GENDER", nullable = true)
    boolean gender;

    @Column(name = "C14_USER_EMAIL", nullable = false)
    String email;

    @Column(name = "C14_VERIFICATION_CODE", nullable = false)
    String verificationCode;

    @Column(name = "C14_PROFILE_IMAGE", nullable = false)
    String profileImage;

    @ManyToOne
    @JoinColumn(name = "C14_ROLE_ID")
    Role role;

    @ManyToOne
    @JoinColumn(name = "C14_PARENT_ID")
    User parent;

}