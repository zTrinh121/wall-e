package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "t19_view_system_notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ViewSystemNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C19_ID")
    int id;

    @ManyToOne
    @JoinColumn(name = "C19_SYSTEM_NOTIFICATION_ID", nullable = false)
    @JsonManagedReference
    SystemNotification systemNotification;

    @ManyToOne
    @JoinColumn(name = "C19_HAS_SEEN_BY", nullable = false)
    @JsonManagedReference
    User hasSeenBy;

    @Column(name = "C19_SEEN_TIME", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    Date seenTime;

}
