package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "C06_CREATED_AT", columnDefinition = "DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date createdAt;

    @Column(name = "C06_UPDATED_AT", columnDefinition = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "C06_ACTOR_ID", referencedColumnName = "C14_USER_ID")
    @JsonManagedReference
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    User actor;

    @ManyToOne
    @JoinColumn(name = "C06_SEND_TO_USER", referencedColumnName = "C14_USER_ID")
    @JsonManagedReference
    User sendToUser;

    @ManyToOne
    @JoinColumn(name = "C06_SEND_TO_COURSE", referencedColumnName = "C01_COURSE_ID")
    @JsonManagedReference
    Course sendToCourse;

    @Column(name = "C06_RATING", nullable = false)
    int rating;

}
