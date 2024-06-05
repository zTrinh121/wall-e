package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "t18_room",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"C18_ROOM_NAME", "C18_CENTER_ID"},
                name = "CK_T18_UNQ"
        )})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C18_ROOM_ID")
    int id;

    @Column(name = "C18_ROOM_NAME", nullable = false)
    String name;

    @ManyToOne
    @JoinColumn(name = "C18_CENTER_ID")
    @JsonManagedReference
    Center center;
}
