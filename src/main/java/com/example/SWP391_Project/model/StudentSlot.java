package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "t17_student_slot",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"C17_student_ID", "C17_CENTER_ID"},
                name = "CK_T17_UNQ")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C17_STUDENT_SLOT_ID")
    int id;

    @ManyToOne
    @JoinColumn(name = "C17_STUDENT_ID")
    @JsonManagedReference
    User student;

    @ManyToOne
    @JoinColumn(name = "C17_SLOT_ID")
    @JsonManagedReference
    Slot slot;

    @Column(name = "C17_ATTENDANCE_STATUS", nullable = false)
    Boolean attendanceStatus;

}
