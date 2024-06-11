package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;

@Entity
@Table(name = "t02_slot",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"C02_SLOT_DATE", "C02_SLOT_START_TIME", "C02_SLOT_END_TIME", "C02_ROOM_ID"},
                name = "CK_T02_UNQ"
        )})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C02_SLOT_ID")
    int id;

    @Column(name = "C02_SLOT_DATE", nullable = false)
    Date slotDate;

    @Column(name = "C02_SLOT_START_TIME", nullable = false)
    Date slotStartTime;

    @Column(name = "C02_SLOT_END_TIME", nullable = false)
    Date slotEndTime;

    @ManyToOne
    @JoinColumn(name = "C02_COURSE_ID")
    @JsonManagedReference
    Course course;

    @ManyToOne
    @JoinColumn(name = "C02_ROOM_ID")
    @JsonManagedReference
    Room room;

}
