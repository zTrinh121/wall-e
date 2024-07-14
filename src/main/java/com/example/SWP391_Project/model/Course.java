package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t01_course")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonFormat
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

    @Column(name = "C01_COURSE_START_DATE", columnDefinition = "DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date startDate;

    @Column(name = "C01_COURSE_END_DATE", columnDefinition = "DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date endDate;

    @Column(name = "C01_AMOUNT_OF_STUDENTS", nullable = false)
    int amountOfStudents;

    @Column(name = "C01_COURSE_FEE", nullable = false)
    float courseFee;

    @Column(name = "C01_SUBJECT_NAME", nullable = false)
    String subject;

    @ManyToOne
    @JoinColumn(name = "C01_CENTER_ID", nullable = false)
    @JsonManagedReference
    Center center;

    @ManyToOne
    @JoinColumn(name = "C01_TEACHER_ID", nullable = false)
    @JsonManagedReference
    User teacher;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    List<Slot> slots;

//    @Override
//    public String toString() {
//        return "Course{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", code='" + code + '\'' +
//                ", description='" + description + '\'' +
//                ", startDate=" + startDate +
//                ", endDate=" + endDate +
//                ", amountOfStudents=" + amountOfStudents +
//                ", courseFee=" + courseFee +
//                ", subject='" + subject + '\'' +
//                ", center=" + center.getId() + // Chỉ in ID của center
//                ", teacher=" + teacher.getId() + // Chỉ in ID của teacher
//                '}';
//    }
}
