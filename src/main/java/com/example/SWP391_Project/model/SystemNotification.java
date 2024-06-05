package com.example.SWP391_Project.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "t11_system_notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class SystemNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C11_ID")
    int id;

    @Column(name = "C11_TITLE", nullable = false)
    String title;

    @Column(name = "C11_CONTENT", nullable = false)
    String content;

    @Column(name = "C11_CREATED_AT", nullable = false)
    Date createdAt;

    @Column(name = "C11_UPDATED_AT", nullable = false)
    Date updatedAt;

}
