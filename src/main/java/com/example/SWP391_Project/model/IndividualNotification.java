package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "t20_individual_notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class IndividualNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C20_ID")
    int id;

    @Column(name = "C20_TITLE", nullable = false)
    String title;

    @Column(name = "C20_CONTENT", nullable = false)
    String content;

    @ManyToOne
    @JoinColumn(name = "C20_ACTOR_ID", nullable = false)
    @JsonManagedReference
    User actor; // admin or manager

    @Column(name = "C20_CREATE_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

    @Column(name = "C20_UPDATE_AT")
    Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "C20_SEND_TO_USER", referencedColumnName = "C14_USERNAME", nullable = false)
    @JsonManagedReference
    User sendToUser;

    @Column(name = "C20_HAS_SEEN", columnDefinition = "BIT(1) DEFAULT 0")
    Boolean hasSeen;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C20_SEEN_TIME")
    Date seenTime;
}
