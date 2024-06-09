package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "t10_result")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C10_RESULT_ID")
    int id;

    @Column(name = "C10_RESULT_TYPE", nullable = false)
    int type;

    @Column(name = "C10_RESULT_VAL", nullable = false)
    int value;

    @ManyToOne
    @JoinColumn(name = "C10_STUDENT_ID", nullable = false)
    @JsonManagedReference
    User student;

    @ManyToOne
    @JoinColumn(name = "C10_COURSE_ID", nullable = false)
    @JsonManagedReference
    Course course;
}
