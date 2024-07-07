package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "t23_view_center_notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ViewCenterNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C23_ID")
    int id;

    @ManyToOne
    @JoinColumn(name = "C23_CENTER_NOTIFICATION_ID", nullable = false)
    @JsonManagedReference
    CenterNotification centerNotification;

    @ManyToOne
    @JoinColumn(name = "C23_HAS_SEEN_BY", referencedColumnName = "C14_USER_ID", nullable = false)
    @JsonManagedReference
    User hasSeenBy;

    @Column(name = "C23_SEEN_TIME", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    Date seenTime;
}
