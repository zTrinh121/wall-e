//package com.example.SWP391_Project.model;
//
//import com.example.SWP391_Project.enums.RoleDescription;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "t19_private_notifications")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder(toBuilder = true)
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class PrivateNotification {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "C19_ID")
//    int id;
//
//    @Column(name = "C19_TITLE", nullable = false)
//    String title;
//
//    @Column(name = "C19_CONTENT", nullable = false)
//    String content;
//
//    @Column(name = "C19_CREATED_AT", nullable = false)
//    Date createdAt;
//
//    @Column(name = "C19_UPDATED_AT")
//    Date updatedAt;
//
//    @Column(name = "C19_ACTOR", nullable = false)
//    @Enumerated(EnumType.ORDINAL)
//    RoleDescription actor;
//
//    @ManyToOne
//    @JoinColumn(name = "C19_SEND_TO_USER", referencedColumnName = "C14_USER_CODE")
//    @JsonManagedReference
//    User userSendTo;
//
//    @ManyToOne
//    @JoinColumn(name = "C19_SEND_TO_CENTER", referencedColumnName = "C03_CENTER_CODE")
//    @JsonManagedReference
//    Center centerSendTo;
//}
