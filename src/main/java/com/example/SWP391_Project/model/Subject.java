package com.example.SWP391_Project.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "t13_subject")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C13_SUBJECT_ID")
    int id;

    @Column(name = "C13_SUBJECT_CODE", nullable = false, unique = true)
    int code;

    @Column(name = "C13_SUBJECT_NAME", nullable = false, unique = true)
    String name;

    @Column(name = "C13_SUBJECT_DESC", nullable = false, columnDefinition = "TEXT")
    String description;
}
