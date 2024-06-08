package com.example.SWP391_Project.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "t15_enrollment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C15_ENROLLMENT_ID")
    int id;

    @ManyToOne
    @JoinColumn(name = "C15_STUDENT_ID", nullable = false)
    User student;

    @ManyToOne
    @JoinColumn(name = "C15_COURSE_ID", nullable = false)
    Course course;
}
