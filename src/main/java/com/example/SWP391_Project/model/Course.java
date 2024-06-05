package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "t01_course")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C01_COURSE_ID")
    int id;

    @Column(name = "C01_COURSE_NAME", nullable = false)
    String name;

    @Column(name = "C01_COURSE_CODE", nullable = false, unique = true)
    String code;

    @Column(name = "C01_COURSE_DESC", nullable = false, columnDefinition = "TEXT")
    String description;

    @Column(name = "C01_COURSE_START_DATE", nullable = false)
    Date startDate;

    @Column(name = "C01_COURSE_END_DATE", nullable = false)
    Date endDate;

    @Column(name = "C01_AMOUNT_OF_STUDENTS", nullable = false)
    int amountOfStudents;

    @Column(name = "C01_COURSE_FEE", nullable = false)
    float courseFee;

    @Column(name = "C01_TOTAL_COURSE_FEE", nullable = false)
    float totalCourseFee;

    @ManyToOne
    @JoinColumn(name = "C01_CENTER_ID", nullable = false)
    @JsonManagedReference
    Center center;

    @ManyToOne
    @JoinColumn(name = "C01_SUBJECT_ID", nullable = false)
    @JsonManagedReference
    Subject subject;

    @ManyToOne
    @JoinColumn(name = "C01_TEACHER_ID", nullable = false)
    @JsonManagedReference
    User teacher;
}

