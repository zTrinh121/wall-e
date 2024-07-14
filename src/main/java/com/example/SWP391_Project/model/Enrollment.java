package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

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
    User student;

    @ManyToOne
    @JoinColumn(name = "C15_COURSE_ID")
    Course course;

    @Column(name = "C15_ENROLL_DATE", columnDefinition = "DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date enrollDate;

    @OneToOne(mappedBy = "enrollment")
    @JsonBackReference
    Bill bill;
}
