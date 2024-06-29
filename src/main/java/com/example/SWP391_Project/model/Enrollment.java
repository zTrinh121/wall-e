package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

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
    int enrollmentId;

    @ManyToOne
    @JoinColumn(name = "C15_STUDENT_ID")
    @JsonManagedReference
    User student;

    @ManyToOne
    @JoinColumn(name = "C15_COURSE_ID")
    @JsonManagedReference
    Course course;

    @Column(name = "C15_ENROLL_DATE", columnDefinition = "DATE")
    Date enrollDate;

    // chỗ này sai
    // bill và enrollment là one-to-one
    @OneToMany(mappedBy = "enrollment")
    @JsonBackReference
    List<Bill> bills;
}