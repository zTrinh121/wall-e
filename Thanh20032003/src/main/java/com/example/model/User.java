package com.example.model;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "t14_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C14_USER_ID")
    private int id;

    @Column(name = "C14_USER_USERNAME")
    private String username;

    @Column(name = "C14_USER_PASSWORD")
    private String password;

    @Column(name = "C14_ACC_STATUS")
    private boolean status;

    @Column(name = "C14_USER_CODE")
    private int code;

    @Column(name = "C14_USER_NAME")
    private String name;

    @Column(name = "C14_USER_PHONE")
    private String phone;

    @Column(name = "C14_USER_ADDRESS")
    private String address;

    @Column(name = "C14_USER_DOB")
    private LocalDate dob;

    @Column(name = "C14_USER_GENDER")
    private boolean gender;

    @Column(name = "C14_USER_EMAIL")
    private String email;

    @Column(name = "C14_VERIFICATION_CODE")
    private String verificationCode;

    @Column(name = "C14_PROFILE_IMAGE")
    private String profileImage;

    @ManyToOne
    @JoinColumn(name = "C14_ROLE_ID")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "C14_PARENT_ID")
    private User parent;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }
}
