package com.example.SWP391_Project.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "t16_feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C16_FEEDBACK_ID")
    int id;

    @Column(name = "C16_DESC", nullable = false, columnDefinition = "TEXT")
    String description;

    @ManyToOne
    @JoinColumn(name = "C16_TEACHER_ID", nullable = false)
    User teacher;

    @ManyToOne
    @JoinColumn(name = "C16_COURSE_ID", nullable = false)
    Course course;
}
