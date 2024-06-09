package com.example.SWP391_Project.model;

import com.example.SWP391_Project.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "t21_center_posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CenterPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C21_ID")
    int id;

    @Column(name = "C21_TITLE")
    String title;

    @Column(name = "C21_CONTENT")
    String content;

    @Column(name = "C21_POST_STATUS")
    @Enumerated(EnumType.ORDINAL)
    Status status;

    @Column(name = "C21_FILE_URL")
    String file_url; // sẽ xử lí file sau

    @Column(name = "C21_CREATED_AT")
    Date createdAt;

    @Column(name = "C21_UPDATED_AT")
    Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "C21_CENTER_ID")
    @JsonManagedReference
    Center center;

}