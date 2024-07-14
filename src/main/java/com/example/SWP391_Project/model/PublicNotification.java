//package com.example.SWP391_Project.model;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "t20_public_notifications")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder(toBuilder = true)
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class PublicNotification {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "C20_ID")
//    int id;
//
//    @Column(name = "C20_TITLE", nullable = false)
//    String title;
//
//    @Column(name = "C20_CONTENT", nullable = false)
//    String content;
//
//    @Column(name = "C20_CREATED_AT", nullable = false)
//    Date createdAt;
//
//    @Column(name = "C20_UPDATED_AT")
//    Date updatedAt;
//
//    @ManyToOne
//    @JoinColumn(name = "C20_SEND_TO_CENTER", referencedColumnName = "C03_CENTER_CODE")
//    @JsonManagedReference
//    Center center;
//
//}
