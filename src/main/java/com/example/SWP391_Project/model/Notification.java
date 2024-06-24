package com.example.SWP391_Project.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;

@Entity
@Table(name = "t20_individual_notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C20_ID")
    int id;

    @Column(name = "C20_TITLE", nullable = false)
    String title;

    @Column(name = "C20_CONTENT", nullable = false)
    String content;

    @Column(name = "C20_HAS_SEEN", nullable = false)
    boolean hasSeen;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C20_SEEN_TIME")
    Date seenTime;

    @Column(name = "C20_SEND_TO_USER", nullable = false)
    String sendToUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C20_ACTOR_ID", referencedColumnName = "C14_USER_ID")
    User actor;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "C20_CREATE_AT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
//    @Generated(GenerationTime.INSERT)
//    Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C20_UPDATE_AT")
    Date updatedAt;
}
