package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

// Import lớp Student
import com.example.SWP391_Project.model.StudentSlot;

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


    @Column(name = "C02_SLOT_DATE", columnDefinition = "DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date slotDate;

    @Column(name = "C02_SLOT_START_TIME", columnDefinition = "TIME", nullable = false)
    Date slotStartTime;

    @Column(name = "C02_SLOT_END_TIME", columnDefinition = "TIME", nullable = false)
    Date slotEndTime;

    @ManyToOne
    @JoinColumn(name = "C02_COURSE_ID")
    @JsonManagedReference
    @ToString.Exclude
    Course course;

    @ManyToOne
    @JoinColumn(name = "C02_ROOM_ID", nullable = false)
    Room room;

    @OneToMany(mappedBy = "slot")
    Set<StudentSlot> studentSlots;

//    @Override
//    public String toString() {
//        return "Slot{" +
//                "id=" + id +
//                ", slotDate=" + slotDate +
//                ", slotStartTime=" + slotStartTime +
//                ", slotEndTime=" + slotEndTime +
//                ", room=" + room.getId() + // Chỉ in ID của room
//                '}';
//    }
}
