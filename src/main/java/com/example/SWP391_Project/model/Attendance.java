package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "t09_attendance",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"C09_STUDENT_ID", "C09_SLOT_ID"},
                name = "CK_ATTENDANCE_UNQ")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C09_ATTENDANCE_ID")
    int id;

    @Column(name = "C09_ATTENDANCE_STATUS", columnDefinition = "BIT(1)", nullable = false)
    boolean attendanceStatus;

    @ManyToOne
    @JoinColumn(name = "C09_STUDENT_ID")
    @JsonManagedReference
    User student;

    @ManyToOne
    @JoinColumn(name = "C09_SLOT_ID")
    @JsonManagedReference
    Slot slot;
}
