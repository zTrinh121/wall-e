package com.example.SWP391_Project.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "t02_slot")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C02_SLOT_ID")
    int id;

    @Column(name = "C02_SLOT_DATE", nullable = false)
    LocalDate date;

    @Column(name = "C02_SLOT_START_TIME", nullable = false)
    LocalTime startTime;

    @Column(name = "C02_SLOT_END_TIME", nullable = false)
    LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "C02_COURSE_ID", nullable = false)
    Course course;

    @ManyToOne
    @JoinColumn(name = "C02_ROOM_ID", nullable = false)
    Room room;
}
