package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "t06_feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C06_FEEDBACK_ID")
    int id;

    @Column(name = "C06_FEEDBACK_DESC", columnDefinition = "TEXT", nullable = false)
    String description;

    @Column(name = "C06_CREATED_AT", nullable = false)
    Date createdAt;

    @Column(name = "C06_UPDATED_AT")
    Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "C06_ACTOR_ID", referencedColumnName = "C14_USER_ID")
    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    User actor;

    @ManyToOne
    @JoinColumn(name = "C06_SEND_TO_USER", referencedColumnName = "C14_USER_CODE")
    @JsonManagedReference
    User sendToUser;

    @ManyToOne
    @JoinColumn(name = "C06_COURSE_ID", nullable = false)
    @JsonManagedReference
    Course course;

}
