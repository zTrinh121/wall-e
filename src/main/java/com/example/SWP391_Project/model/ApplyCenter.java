package com.example.SWP391_Project.model;

import com.example.SWP391_Project.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "t12_apply_center",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"C12_TEACHER_ID", "C12_CENTER_ID"},
                name = "CK_T12_UNQ")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ApplyCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C12_APPLY_ID")
    int id;

    @Column(name = "C12_TITLE")
    String title;

    @Column(name = "C12_CONTENT", columnDefinition = "TEXT")
    String content;

    @Column(name = "C12_CREATED_AT", columnDefinition = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "C12_APPLY_STATUS")
    Status status;

    @ManyToOne
    @JoinColumn(name = "C12_TEACHER_ID")
    @JsonManagedReference
    User teacher;

    @ManyToOne
    @JoinColumn(name = "C12_CENTER_ID")
    @JsonManagedReference
    Center center;

}
