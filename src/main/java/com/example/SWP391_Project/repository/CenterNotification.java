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
//@Table(name = "t22_center_notification")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Builder(toBuilder = true)
//public class CenterNotification {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "C22_ID")
//    int id;
//
//    @Column(name = "C22_TITLE", nullable = false)
//    String title;
//
//    @Column(name = "C22_CONTENT", nullable = false)
//    String content;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "C22_CREATE_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
//    Date createdAt;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "C22_UPDATE_AT")
//    Date updatedAt;
//
//    @ManyToOne
//    @JoinColumn(name = "C22_CENTER_ID", nullable = false)
//    @JsonManagedReference
//    Center center;
//}