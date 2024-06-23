package com.example.SWP391_Project.model;

import com.example.SWP391_Project.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t03_center")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Center {

    @Id
    @Column(name = "C03_CENTER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "C03_CENTER_CODE")
    String code;

    @Column(name = "C03_CENTER_NAME")
    String name;

    @Column(name = "C03_CENTER_DESC")
    String description;

    @Column(name = "C03_CENTER_ADDRESS")
    String address;

    @Column(name = "C03_CENTER_PHONE")
    String phone;

    @Column(name = "C03_CENTER_EMAIL")
    String email;

    @Column(name = "C03_CENTER_REGULATIONS")
    String regulation;

    @Column(name = "C03_CREATED_AT")
    Date createdAt;

    @Column(name = "C03_CENTER_STATUS")
    @Enumerated(EnumType.ORDINAL)
    Status status;

    @Column(name = "C03_IMAGE_PATH")
    String imagePath; // xử lí sau

    @Column(name = "C03_CLOUDINARY_IMAGE_ID")
    String cloudinaryImageId;

    @ManyToOne
    @JoinColumn(name = "C03_MANAGER_ID")
    @JsonManagedReference
    User manager;

//    @OneToMany(mappedBy = "center", cascade = CascadeType.REMOVE)
//    List<Course> courses;

