package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "t10_result")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C10_RESULT_ID")
    int id;

    @Column(name = "C10_RESULT_TYPE")
    int resultType;

    @Column(name = "C10_RESULT_VAL")
    int resultValue;

    @ManyToOne
    @JoinColumn(name = "C10_STUDENT_ID")
    @JsonManagedReference
    User student;

    @ManyToOne
    @JoinColumn(name = "C10_COURSE_ID")
    @JsonManagedReference
    Course course;
}
